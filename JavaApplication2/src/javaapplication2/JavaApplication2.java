/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vicky
 */
public class JavaApplication2 extends TimerTask {
    
    private static final String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:jtds:sqlserver://localhost:1433/demo";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "victoria@89";
    
    
    @Override
    public void run() {
         try {
                getChangesFromTable(getLastVersion("employees"), "employees", "id");
                //System.out.println(getCurrentVersion());
                
            } 
        catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }
    
    
    public static void main(String[] args) {
        
         Timer timer = new Timer();
         timer.schedule(new JavaApplication2(), 0, 4000);
        
    }
    private static int getLastVersion(String table)
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
    private static void setCurrentVersion(String table, String current_version){
        Properties prop = new Properties();
	FileOutputStream output = null;
        FileInputStream input = null;
 
	try {
                //first load old one:
                input = new FileInputStream("config.properties");
                prop.load(input);
        
		// set the properties value
		prop.setProperty(table+".current_version", current_version);
                
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
    
    private static int getCurrentVersion() throws SQLException{
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
    
    private static ResultSet getChangesFromTable(int last_version, String table, String primary_key) throws SQLException {
 
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        String sql_query ="DECLARE @PreviousVersion bigint\n";
        sql_query+="SET @PreviousVersion = ?\n";
        sql_query+="SELECT CTTable.id, CTTable.SYS_CHANGE_OPERATION,Emp.*,\n";
        sql_query+="CTTable.SYS_CHANGE_VERSION, CTTable.SYS_CHANGE_COLUMNS, CTTable.SYS_CHANGE_CONTEXT \n";
        sql_query+="FROM CHANGETABLE (CHANGES "+table+", @PreviousVersion) AS CTTable\n";
        sql_query+="LEFT OUTER JOIN "+table+" AS Emp\n";
        sql_query+="ON emp."+primary_key+" = CTTable.id\n";
        sql_query+="WHERE CTTable.SYS_CHANGE_VERSION > @PreviousVersion\n";
        sql_query+="ORDER BY CTTable.SYS_CHANGE_VERSION ASC";
        

        try {
                dbConnection = getDBConnection();
                preparedStatement = dbConnection.prepareStatement(sql_query);
                preparedStatement.setInt(1, last_version);

                // execute select SQL stetement
                rs = preparedStatement.executeQuery();
                boolean hasNext = rs.next();
                while (hasNext) {

                    int id = rs.getInt("id");
                    String change_operation = rs.getString("SYS_CHANGE_OPERATION");
                    String name = rs.getString("name");
                    String designation = rs.getString("designation");
                    int is_staff = rs.getInt("is_staff");
                    int change_version = rs.getInt("SYS_CHANGE_VERSION");
                    
                    hasNext = rs.next();
                    if (hasNext) {
                       // processing for rows that aren't last
                        System.out.println("id: "+id + "\t" + "change_operation: "+change_operation +
                            "\t" +"name: " +name + "\t" + "designation: "+designation +
                            "\t" + "is_staff: "+is_staff +"\t"+"change_version: "+ change_version);
                    }
                    else {
                       // processing for last row
                        System.out.println("id: "+id + "\t" + "change_operation: "+change_operation +
                            "\t" +"name: " +name + "\t" + "designation: "+designation +
                            "\t" + "is_staff: "+is_staff +"\t"+"change_version: "+ change_version);
                        System.out.println("current version = "+change_version);
                        setCurrentVersion(table, String.valueOf(change_version));
                    }
                    

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
        return rs;
 
    }

    
    
   
    
}
