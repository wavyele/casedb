/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.icap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kemricdc.utils.AppProperties;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class DatabaseUtils {
    private static Connection con = null;
    
    private static DatabaseUtils databaseUtils;
    
    
    private static final String url = AppProperties.getProperty("url");
    private static final String username = AppProperties.getProperty("username");
    private static final String password = AppProperties.getProperty("password");
    
    private DatabaseUtils(){
        
    }
    
    public static DatabaseUtils getInstance(){
        if(databaseUtils!=null){
            return databaseUtils;
        }else{
            databaseUtils=new DatabaseUtils();
            return databaseUtils;
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public Connection getConnection(){
        
        if (con==null) {
            try {
                con = DriverManager.getConnection(url, username, password);
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.INFO, null, "Connection Created Successfully.............");
                
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return con;
        } else {
            return con;
        }
        
    }
    
    
    public void closeConnection(Connection con){
        
        if(con!=null){
            try {
                con.close();
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, "Database Connection Successfully closed....");
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
