/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kemricdc.entities;

import java.util.Date;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class Transaction {
    
    
   
    private String variable;
    private String dataType;
    private String dataValue;
    private Date dateCreated;



    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "\n\n\nTransaction :" + "\nvariable=" + variable + ", \ndataType=" + dataType + ", \ndataValue=" + dataValue + ", \ndateCreated=" + dateCreated + '}';
    }

    
    
    
    
}
