/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc.constants;

/**
 *
 * @author Stanslaus Odhiambo
 */
public enum MaritalStatus {

    SINGLE("SINGLE"),
    MONOGAMOUS_MARRIED("MONOGAMOUS_MARRIED"),
    POLYGAMOUS_MARRIED("POLYGAMOUS_MARRIED"),
    DIVORCED("DIVORCED"),
    SEPARATED("SEPARATED"),
    WIDOWED("WIDOWED"),
    COHABITING("COHABITING"),
    MISSING("MISSING"),
    UNKOWN("UNKNOWN");

    private final String st;

    private MaritalStatus(String st) {
        this.st = st;
    }

    public String getValue() {
        return st;
    }

}
