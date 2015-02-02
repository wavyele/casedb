/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.icap.utils;

import java.util.ArrayList;
import java.util.List;
import org.icap.constants.Operation;


/**
 *
 * @author Stanslaus Odhiambo
 */
public class TransactionSet {
    
    private String tableName;
    private List<Transaction> transactions=new ArrayList<>();
    private Operation operation;
    private double patientIdentifier;
    
    

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public double getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(double patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "TransactionSet{" + "tableName=" + tableName + ", operation=" + operation + ", patientIdentifier=" + patientIdentifier + ", transactions=" + transactions + '}';
    }
    
    
    
    
    
    
    
    
}
