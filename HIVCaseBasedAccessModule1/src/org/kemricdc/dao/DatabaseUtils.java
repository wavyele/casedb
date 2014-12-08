/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kemricdc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class DatabaseUtils {
    private static Connection con = null;
    
    private static DatabaseUtils databaseUtils;
    
    
    private final String url ="jdbc:mysql://localhost:3306/cpad_shadow?zeroDateTimeBehavior=convertToNull";
    private final String username="root";
    private final String password="2806";
    
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
