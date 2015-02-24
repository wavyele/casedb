/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changetracker.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vicky
 */
public class ChangeTracker {
    
    
    
    
    public static int getLastVersion(String table)
    {
        return Integer.parseInt(PropertiesManager.readConfigFile(table+".current_version","config.properties"));
        
    }
    public static synchronized void setCurrentVersion(String table, int current_version){
      
        PropertiesManager.modifyConfigFile(table+".current_version", String.valueOf(current_version),"config.properties");
        
    }

    
    public static List resultSetToArrayList(ResultSet rs) throws SQLException{
        
        
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList list = new ArrayList(50);
        while (rs.next()){
           
           HashMap row = new HashMap(columns);
           for(int i=1; i<=columns; ++i){           
            row.put(md.getColumnName(i),rs.getObject(i));
            
            
           }
            list.add(row);
        }
        
       return list;
    }
    
    public static List resultSetToArrayList(ResultSet rs, String table) throws SQLException{
        
        
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList list = new ArrayList(50);
        while (rs.next()){
            if(rs.isFirst())
            {
               
                    int change_version = rs.getInt("SYS_CHANGE_VERSION");
                    setCurrentVersion(table,change_version );
               
            }
           HashMap row = new HashMap(columns);
           for(int i=1; i<=columns; ++i){           
            row.put(md.getColumnName(i),rs.getObject(i));
            
            
           }
            list.add(row);
        }
       
       return list;
    }
    
    
    public static int getCurrentVersion() throws SQLException{
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        int current_version = -1;
        String sql_query ="SELECT CHANGE_TRACKING_CURRENT_VERSION () AS current_version";
        

        try {
                dbConnection = DBConnector.getDBConnection();
                preparedStatement = dbConnection.prepareStatement(sql_query);

                // execute select SQL stetement
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {

                    current_version = rs.getInt("current_version");

                }

        } catch (SQLException e) {

                System.out.println(e.getMessage());

        } finally {

                if (preparedStatement != null) {
                        preparedStatement.close();
                }

                if (dbConnection != null) {
                        dbConnection.close();
                }

        }
        return current_version;
 
    }
    /**
    *
    * @author Vicky
     * @param last_version is the last version from which you want to get changes
     * @param table is the table being tracked
     * @param primary_key is the primary key name of the table being tracked
     * @return a list with all the columns of the tracked table, and an additional set of columns from the changetable.
     * @throws java.sql.SQLException 
    */
    public static List getChangesFromTable(int last_version, String table, String primary_key) throws SQLException {
 
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs;
        List results = null;
        
        String sql_query ="DECLARE @PreviousVersion bigint\n";
        //open the symmetric key
        sql_query+="Declare @SymKey varchar(400)\n";
        sql_query += "Set @SymKey = 'Open symmetric key Key_CTC decryption by password=''ttwbvXWpqb5WOLfLrBgisw=='''\n";
        sql_query +="exec(@SymKey)\n";
        sql_query+="SET @PreviousVersion = ?\n";
        sql_query+="SELECT CTTable."+primary_key+", CTTable.SYS_CHANGE_OPERATION, Emp.*,\n";
        if(table.equals("Mst_Patient"))
        {
        sql_query+="convert(varchar(50), decryptbykey(Emp.firstname)) AS firstname_decrypted\n";
        sql_query+=",convert(varchar(50), decryptbykey(Emp.MiddleName)) AS middlename_decrypted,";
        sql_query+="convert(varchar(50), decryptbykey(Emp.LastName)) AS lastname_decrypted,";
        }
        sql_query+="CTTable.SYS_CHANGE_VERSION, CTTable.SYS_CHANGE_COLUMNS, CTTable.SYS_CHANGE_CONTEXT \n";
        sql_query+="FROM CHANGETABLE (CHANGES "+table+", @PreviousVersion) AS CTTable\n";
        sql_query+="LEFT OUTER JOIN "+table+" AS Emp\n";
        sql_query+="ON emp."+primary_key+" = CTTable."+primary_key+"\n";
        sql_query+="WHERE CTTable.SYS_CHANGE_VERSION > @PreviousVersion\n";
        sql_query+="ORDER BY CTTable.SYS_CHANGE_VERSION DESC";
        sql_query +="\nClose symmetric key Key_CTC";
        
        
        
        try {
                dbConnection = DBConnector.getDBConnection();
                
                preparedStatement = dbConnection.prepareStatement(sql_query,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                preparedStatement.setInt(1, last_version);

                // execute select SQL stetement
                rs = preparedStatement.executeQuery();
                
                
                //move cursor to last record in resultset
                
                results = resultSetToArrayList(rs,table);
             
        } catch (SQLException e) {

                System.out.println(e.getMessage());

        } finally {

                if (preparedStatement != null) {
                        preparedStatement.close();
                }

                if (dbConnection != null) {
                        dbConnection.close();
                }

        }
        return results;
 
    }
    
    public static List getPatient(int primary_key) throws SQLException {
 
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs;
        List results = null;
        
        String sql_query ="Declare @SymKey varchar(400)\n";
        sql_query +="Set @SymKey = 'Open symmetric key Key_CTC decryption by password=''ttwbvXWpqb5WOLfLrBgisw=='''\n";
        sql_query +="exec(@SymKey)\n";
        sql_query +="SELECT convert(varchar(50), decryptbykey(Emp.firstname)) AS firstname_decrypted,\n";
        sql_query +="convert(varchar(50), decryptbykey(Emp.middlename)) AS middlename_decrypted ,\n";
        sql_query +="convert(varchar(50), decryptbykey(Emp.lastname)) AS lastname_decrypted ,*\n";
        sql_query +="FROM [iqcare].[dbo].mst_Patient AS Emp WHERE [Ptn_Pk] = ?\n";
        sql_query +="Close symmetric key Key_CTC";
        
        try {
                dbConnection = DBConnector.getDBConnection();
                
                preparedStatement = dbConnection.prepareStatement(sql_query,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                preparedStatement.setInt(1, primary_key);

                // execute select SQL stetement
                rs = preparedStatement.executeQuery();
                
                results = resultSetToArrayList(rs);
                
                
           } catch (SQLException e) {

                System.out.println(e.getMessage());

        } finally {

                if (preparedStatement != null) {
                        preparedStatement.close();
                }

                if (dbConnection != null) {
                        dbConnection.close();
                }

        }
        return results;
 
    }
    
    public static void setPK(String table, String proposed_field) throws SQLException{
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        
        String sql_query ="IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE  CONSTRAINT_TYPE = 'PRIMARY KEY'\n";
        sql_query +="AND TABLE_NAME = '"+table+"'  \n";
        sql_query +="AND TABLE_SCHEMA ='dbo' )\n";
        sql_query +="BEGIN\n";
        sql_query +="ALTER TABLE "+table+" ADD PRIMARY KEY ("+proposed_field+")\n";
        sql_query +="END";
        
        try {
                dbConnection = DBConnector.getDBConnection();
                
                preparedStatement = dbConnection.prepareStatement(sql_query);

                // execute SQL stetement
                preparedStatement.execute();
                
                
           } catch (SQLException e) {

                System.out.println(e.getMessage());

        } finally {

                if (preparedStatement != null) {
                        preparedStatement.close();
                }

                if (dbConnection != null) {
                        dbConnection.close();
                }

        }
    
    }
    public static void enableDBChangeTracking() throws SQLException {
 
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String db_name = PropertiesManager.readConfigFile("db_name","connection.properties");
        
        
        
        String sql_query ="IF NOT EXISTS (SELECT 1 FROM sys.change_tracking_databases \n";
        sql_query +="WHERE database_id = DB_ID('"+db_name+"'))\n";
        sql_query +="BEGIN\n";
        sql_query +="ALTER DATABASE "+db_name+"\n";
        sql_query +="SET CHANGE_TRACKING = ON\n";
        sql_query +="(CHANGE_RETENTION = 5 DAYS, AUTO_CLEANUP = ON);\n";
        sql_query +="END\n";
        
        try {
                dbConnection = DBConnector.getDBConnection();
                
                preparedStatement = dbConnection.prepareStatement(sql_query);

                // execute SQL stetement
                preparedStatement.execute();
                
                
           } catch (SQLException e) {

                System.out.println(e.getMessage());

        } finally {

                if (preparedStatement != null) {
                        preparedStatement.close();
                }

                if (dbConnection != null) {
                        dbConnection.close();
                }

        }
        //return results;
 
    }
    public static Timestamp getVisitDate(int visit_id){
        
        PreparedStatement preparedStatement = null;
        ResultSet rs;
        Timestamp visit_date = null;
        
        String sql_query ="SELECT VisitDate FROM iqcare.dbo.ord_Visit where Visit_Id = ?";
        
        try(Connection dbConnection = DBConnector.getDBConnection();) {
                
                
                preparedStatement = dbConnection.prepareStatement(sql_query);
                preparedStatement.setInt(1, visit_id);

                // execute select SQL stetement
                rs = preparedStatement.executeQuery();
                
                while(rs.next())
                {
                    visit_date = rs.getTimestamp("VisitDate");
                }
                
                
           } catch (SQLException e) {

                System.out.println(e.getMessage());

        }
        return visit_date;
    }
    public static void enableTableChangeTracking(String table) throws SQLException {
 
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        
        String sql_query ="IF NOT EXISTS (SELECT 1 FROM sys.change_tracking_tables \n";
        sql_query +="WHERE object_id = OBJECT_ID('"+table+"'))\n" ;
        sql_query +="BEGIN\n";
        sql_query +="ALTER TABLE "+table+"\n";
        sql_query +="ENABLE CHANGE_TRACKING\n";
        sql_query +="WITH (TRACK_COLUMNS_UPDATED = ON)\n";
        sql_query +="END";
        
        try {
                dbConnection = DBConnector.getDBConnection();
                
                preparedStatement = dbConnection.prepareStatement(sql_query);

                // execute select SQL stetement
                preparedStatement.execute();
                
                
           } catch (SQLException e) {

                System.out.println(e.getMessage());

        } finally {

                if (preparedStatement != null) {
                        preparedStatement.close();
                }

                if (dbConnection != null) {
                        dbConnection.close();
                }

        }
 
    }

    
    
   
    
}
