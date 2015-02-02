/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.icap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class TransactionIdManager {
    
    private static TransactionIdManager manager;
    
    
    private TransactionIdManager(){
        
    }
    
    
    public static TransactionIdManager getInstance(){
        if(manager!=null){
            return manager;
        }else{
            manager=new TransactionIdManager();
            return manager;
        }
    }

    public int getLastTransactionId() {
        int lastTransactionId = 0;
        
        Connection con=DatabaseUtils.getInstance().getConnection();
        
        String queryString="SELECT value FROM last_transaction_id WHERE id=1";
        Statement statement = null;
        
        try {
            statement=con.createStatement();
            ResultSet resultSet=statement.executeQuery(queryString);
            resultSet.next();
            lastTransactionId=resultSet.getInt(1);
            
        } catch (SQLException ex) {
            Logger.getLogger(TransactionIdManager.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                // DatabaseUtils.getInstance().closeConnection(con);
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(TransactionIdManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lastTransactionId;
    }

    public void setLastTransactionId(int lastTransactionId) {
        
        Connection connection=DatabaseUtils.getInstance().getConnection();
        String queryString="UPDATE `cpad_shadow`.`last_transaction_id` SET `value`= ? WHERE `id`=1";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement=connection.prepareStatement(queryString);
            preparedStatement.setInt(1, lastTransactionId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionIdManager.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                //            DatabaseUtils.getInstance().closeConnection(connection);
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(TransactionIdManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
    
}
