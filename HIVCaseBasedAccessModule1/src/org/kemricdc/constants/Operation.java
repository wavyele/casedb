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
public enum Operation {

    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String op;

    private Operation(String op) {
        this.op = op;
    }

    public String getValue() {
        return op;
    }

}
