/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kemricdc.constants.Operation;
import org.kemricdc.dao.DatabaseUtils;
import org.kemricdc.dao.TransactionIdManager;
import org.kemricdc.utils.Transaction;
import org.kemricdc.utils.TransactionSet;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class MiningThread {

    private final Connection con;
    private int lastTransactionId;
    Operation operation;

    public MiningThread() {
        con = DatabaseUtils.getInstance().getConnection();
        lastTransactionId = TransactionIdManager.getInstance().getLastTransactionId();
        System.out.println("Last Transaction ID....." + lastTransactionId);
    }

    public List<TransactionSet>  processTransactions() {
        List<TransactionSet> transactionSets=new ArrayList<>();
        TransactionSet ts;
        PreparedStatement ps;
        ResultSet resultSet;
        int processId;
        System.out.println(lastTransactionId);
        String queryString = "SELECT\n"
                + "    `id`\n"
                + "FROM\n"
                + "    `cpad_shadow`.`transaction`\n"
                + "WHERE (`id` > ?) limit 100;";
        if (con != null) {
            try {
                ps=con.prepareStatement(queryString);
                ps.setInt(1, lastTransactionId);
                resultSet=ps.executeQuery();
                while (resultSet.next()) {                    
                    processId=resultSet.getInt("id");
                    ts=mineTransaction(processId);
                    
                    transactionSets.add(ts);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MiningThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        return transactionSets;

    }

    public TransactionSet mineTransaction(int id) {

        TransactionSet transactionSet = new TransactionSet();
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction;

        String queryString = "SELECT\n"
                + "    `table`.`name` AS `tableName`\n"
                + "    , `transaction`.`type`\n"
                + "    , `transaction`.`id`\n"
                + "    , `column`.`name` AS `columnName`\n"
                + "    , `column`.`data_type`\n"
                + "    , `transaction_data`.`data`\n"
                + "    , `transaction`.`created_datetime`\n"
                + "FROM\n"
                + "    `transaction`\n"
                + "    INNER JOIN `table` \n"
                + "        ON (`transaction`.`table_id` = `table`.`id`)\n"
                + "    INNER JOIN `column` \n"
                + "        ON (`column`.`table_id` = `table`.`id`)\n"
                + "    INNER JOIN `transaction_data` \n"
                + "        ON (`transaction_data`.`transaction_id` = `transaction`.`id`) AND (`transaction_data`.`column_id` = `column`.`id`)\n"
                + "WHERE (`transaction`.`id` = ?);";
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            statement = con.prepareStatement(queryString);
            statement.setInt(1, id);
            transactionSet = new TransactionSet();
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transaction = new Transaction();
                String var = resultSet.getString("columnName");
                transaction.setVariable(var);
                transaction.setDataType(resultSet.getString("data_type"));
                String data = resultSet.getString("data");
                if (var.equals("patient_id")) {
                    transactionSet.setPatientIdentifier(Double.parseDouble(data));
                }
                transaction.setDataValue(data);
                transaction.setDateCreated(resultSet.getDate("created_datetime"));
                transactions.add(transaction);
                transactionSet.setTableName(resultSet.getString("tableName"));
                transactionSet.setOperation(Operation.valueOf(resultSet.getString("type")));
                lastTransactionId = id;

            }
            transactionSet.setTransactions(transactions);

        } catch (SQLException ex) {
            Logger.getLogger(MiningThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("The new last transaction id is: " + lastTransactionId);
            TransactionIdManager.getInstance().setLastTransactionId(lastTransactionId);

        }

        return transactionSet;
    }

}
