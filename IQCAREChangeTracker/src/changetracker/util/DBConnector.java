/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changetracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Vicky
 */
public class DBConnector {
    
    private static final String DB_DRIVER = PropertiesManager.readConfigFile("db_driver","connection.properties");
    private static final String DB_CONNECTION = PropertiesManager.readConfigFile("connection_string","connection.properties");
    private static final String DB_USER = PropertiesManager.readConfigFile("db_user","connection.properties");
    private static final String DB_PASSWORD = PropertiesManager.readConfigFile("db_password","connection.properties");
    
  public static Connection getDBConnection() {
 
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
}
