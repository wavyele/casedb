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
public enum PatientSource {

    ANC("ANC"),
    PMTCT("PMTCT"),
    OPD("OPD"),
    TB_CLINIC("TB CLINIC"),
    VCT("VCT"),
    IPD_ADULT("IPD ADULT"),  
    IPD_CHILD("IPD CHILD"),
    MCH_CHILD("MCH CHILD"),
    IN_PATIENT("IN PATIENT"),
    CHILD_WELFARE_CLINIC("CHILD WELFARE CLINIC"),
    OUT_PATIENT("OUT PATIENT"),
    MISSING("MISSING");

    private final String ps;

    private PatientSource(String ps) {
        this.ps = ps;
    }

    public String getValue() {
        return ps;
    }

}
