/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changetracker.util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vicky
 */
public class ChangeTracker extends TimerTask {
    
    private static final String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:jtds:sqlserver://localhost:1433/iqcare";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "victoria@89";
    
    
    @Override
    public void run() {
        // trackPatientEnrollment();
        
    }
    
    
   
    public static int getLastVersion(String table)
    {
        Properties prop = new Properties();
	InputStream input = null;
        int last_version = -1;
        
        try {
 
		input = new FileInputStream("config.properties");
 
		// load the properties file
		prop.load(input);
 
		// get the property value and print it out
		
                last_version = Integer.parseInt(prop.getProperty(table+".current_version"));
                
	} catch (IOException ex) {
		ex.printStackTrace();
	}
        finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        return last_version;
    }
    public static void setCurrentVersion(String table, int current_version){
        Properties prop = new Properties();
	FileOutputStream output = null;
        FileInputStream input = null;
 
	try {
                //first load old one:
                input = new FileInputStream("config.properties");
                prop.load(input);
        
		// set the properties value
		prop.setProperty(table+".current_version", String.valueOf(current_version));
                
                //save modified properies file
                output = new FileOutputStream("config.properties");
                prop.store(output, null);
 
	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
 
	}
    }
    private static Connection getDBConnection() {
 
        Connection dbConnection = null;

        try {

                Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

                System.out.println(e.getMessage());

        }

        try {

                dbConnection = DriverManager.getConnection(
                     DB_CONNECTION, DB_USER,DB_PASSWORD);
                return dbConnection;

        } catch (SQLException e) {

                System.out.println(e.getMessage());

        }

        return dbConnection;
 
    }
    
    public static List resultSetToArrayList(ResultSet rs, String table) throws SQLException{
        
        
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList list = new ArrayList(50);
        while (rs.next()){
            if(rs.isFirst())
            {
               
                    int change_version = rs.getInt("SYS_CHANGE_VERSION");
                    System.out.println("change_version: "+change_version);
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
                dbConnection = getDBConnection();
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
        sql_query+="SELECT CTTable."+primary_key+", CTTable.SYS_CHANGE_OPERATION,Emp.*,\n";
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
                dbConnection = getDBConnection();
                
                preparedStatement = dbConnection.prepareStatement(sql_query,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                preparedStatement.setInt(1, last_version);

                // execute select SQL stetement
                rs = preparedStatement.executeQuery();
                
                
                //move cursor to last record in resultset
                
                results = resultSetToArrayList(rs,table);
                
                
                
                

                
                  
                
                
                //rs.last()
                
                
               

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

    
    
   
    
}
