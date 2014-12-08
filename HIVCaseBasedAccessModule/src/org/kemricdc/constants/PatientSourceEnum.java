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
public enum PatientSourceEnum {

    ANC("ANC"),
    PMTCT("PMTCT"),
    TB("TB"),
    VCT("VCT"),
    TB_OPD("TB OPD"),
    IN_PATIENT("IN PATIENT"),
    CHILD_WELFARE_CLINIC("CHILD WELFARE CLINIC"),
    DTC("DTC");

    private final String ps;

    private PatientSourceEnum(String ps) {
        this.ps = ps;
    }

    public String getValue() {
        return ps;
    }

}
