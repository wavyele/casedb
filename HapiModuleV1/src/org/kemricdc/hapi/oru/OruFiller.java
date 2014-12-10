/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc.hapi.oru;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class OruFiller {

    public enum ValueType {

        CE,
        TX,
        ST,
        NM
        
    }

    private ValueType valueType;
    private String id;
    private String value;
    private String codingSystem;
    private String obxId;
    private String subId;
    private String identifier, ceValue; //these two are for CEs
    private String extraSubComponent, txValue; //TX data type

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCodingSystem() {
        return codingSystem;
    }

    public void setCodingSystem(String codingSystem) {
        this.codingSystem = codingSystem;
    }

    public String getObxId() {
        return obxId;
    }

    public void setObxId(String obxId) {
        this.obxId = obxId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCeValue() {
        return ceValue;
    }

    public void setCeValue(String ceValue) {
        this.ceValue = ceValue;
    }

    public String getExtraSubComponent() {
        return extraSubComponent;
    }

    public void setExtraSubComponent(String extraSubComponent) {
        this.extraSubComponent = extraSubComponent;
    }

    public String getTxValue() {
        return txValue;
    }

    public void setTxValue(String txValue) {
        this.txValue = txValue;
    }
    
    

}
