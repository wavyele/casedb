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
public enum Event {

    HIV_DIAGNOSIS("sdfsd"),
    HIV_CARE_INITIATION("sdfsd"),
    TRANSFER_IN("sdfsd"),
    CD4_COUNT("sdfsd"),
    VIRAL_LOAD("sdfsd"),
    CHANGE_IN_REGIMEN("sdfsd"),
    WHO_STAGE_1("sdfsd"),
    WHO_STAGE_2("sdfsd"),
    WHO_STAGE_3("sdfsd"),
    WHO_STAGE_4("sdfsd"),
    TB_DIAGNOSIS("sdfsd"),
    TB_TREATMENT("sdfsd"),
    LOST_TO_FOLLOWUP("sdfsd"),
    DECEASED("sdfsd"),
    FIRST_LINE_REGIMEN("sdfsd"),
    SECOND_LINE_REGIMEN("sdfsd"),
    PMTCT_INITIATION("sdfsd"),
    BIRTH("birth"),
    TRANSFER_OUT("sdfsd");

    private final String event;

    private Event(String event) {
        this.event = event;
    }

    public String getValue() {
        return event;

    }

}
