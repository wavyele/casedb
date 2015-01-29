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
public enum Sex {

    MALE("MALE"),
    FEMALE("FEMALE"),
    MISSING("MISSING"),
    UNKOWN("UNKNOWN");

    private final String st;

    private Sex(String st) {
        this.st = st;
    }

    public String getValue() {
        return st;
    }

}
