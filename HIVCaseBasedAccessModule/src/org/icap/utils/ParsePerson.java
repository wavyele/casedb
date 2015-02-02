/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.icap.utils;

import accessquery.AccessPerson;
import accessquery.AccessQuery;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.tool.hbm2x.StringUtils;
import org.icap.mappers.TransactionMapper;
import org.kemricdc.constants.IdentifierType;
import org.kemricdc.constants.MaritalStatus;
import org.kemricdc.constants.PatientSource;
import org.kemricdc.constants.Sex;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class ParsePerson {

    public static Person parsePerson(Transaction t) {
        String idString;
        PatientSource ps;
        MaritalStatus ms = null;
        Person processPerson = new Person();
        AccessPerson ap;

        idString = t.getDataValue();
        IdentifierType identifierType = IdentifierType.CCC_NUMBER;
        PersonIdentifier identifier = new PersonIdentifier(identifierType, idString);
        processPerson.getPersonIdentifiers().add(identifier);

        //For uniformity purposes....
        identifierType = IdentifierType.PID_NUMBER;
        identifier = new PersonIdentifier(identifierType, idString);
        processPerson.getPersonIdentifiers().add(identifier);

        if (idString != null) {
            try {
                ap = new AccessQuery().getAccessPerson(idString);
                processPerson.setFirstName(ap.getFirstName());
                String lastNameToBeParsed = ap.getLastName();
                String[] names = ParseDateAndName.parseLastName(lastNameToBeParsed);
                if (names.length > 1) {
                    processPerson.setMiddleName(names[0]);
                    processPerson.setLastName(names[1]);
                } else {
                    processPerson.setLastName(names[0]);
                }

                //sort out the dob
                String dv2 = ap.getDob();

                if (StringUtils.isNotEmpty(dv2)) {
                    processPerson.setBirthdate(ParseDateAndName.parseDate(dv2));
                }

                //sort out the sex bit
                String sexV2 = ap.getSex();
                if (StringUtils.isNotEmpty(sexV2)) {
                    switch (sexV2) {
                        case "1": {
                            processPerson.setSex(Sex.MALE);
                            break;
                        }
                        case "2": {
                            processPerson.setSex(Sex.FEMALE);
                            break;
                        }
                        default: {
                            processPerson.setSex(Sex.UNKNOWN);
                            break;
                        }
                    }
                }

                //sort out the marital status thingy
                String statusV2 = ap.getMaritalStatus();
                if (StringUtils.isNotEmpty(statusV2)) {
                    switch (statusV2) {
                        case "1": {
                            ms = MaritalStatus.valueOf("POLYGAMOUS_MARRIED");
                            break;
                        }
                        case "2": {
                            ms = MaritalStatus.valueOf("MONOGAMOUS_MARRIED");
                            break;
                        }
                        case "3": {
                            ms = MaritalStatus.valueOf("DIVORCED");
                            break;
                        }
                        case "4": {
                            ms = MaritalStatus.valueOf("WIDOWED");
                            break;
                        }
                        case "5": {
                            ms = MaritalStatus.valueOf("COHABITING");
                            break;
                        }
                        case "6": {
                            ms = MaritalStatus.valueOf("SINGLE");
                            break;
                        }
                        case "7": {
                            ms = MaritalStatus.valueOf("SEPARATED");
                            break;
                        }
                        case "8": {
                            ms = MaritalStatus.valueOf("MISSING");
                            break;
                        }
                    }
                    processPerson.setMaritalStatus(ms);
                }

                //sort out the patient sorce thingy
                String source = ap.getPatientSource();
                if (StringUtils.isNotEmpty(source)) {
                    switch (source) {
                        case "1": {
                            ps = PatientSource.valueOf("PMTCT");
                            break;
                        }
                        case "2": {
                            ps = PatientSource.valueOf("VCT");
                            break;
                        }
                        case "3": {
                            ps = PatientSource.valueOf("OPD");
                            break;
                        }
                        case "4": {
                            ps = PatientSource.valueOf("IN PATIENT");
                            break;
                        }
                        case "5": {
                            ps = PatientSource.valueOf("CHILD WELFARE CLINIC");
                            break;
                        }
                        case "6": {
                            ps = PatientSource.valueOf("OPD");
                            break;
                        }
                        case "7": {
                            ps = PatientSource.valueOf("CHILD WELFARE CLINIC");
                            break;
                        }
                        case "8": {
                            ps = PatientSource.valueOf("MISSING");
                            break;
                        }
                        case "10": {
                            ps = PatientSource.valueOf("IPD ADULT");
                            break;
                        }
                        case "11": {
                            ps = PatientSource.valueOf("IPD CHILD");
                            break;
                        }
                        default: {
                            ps = PatientSource.valueOf("MISSING");
                            break;
                        }
                    }
                    processPerson.setPatientSource(ps);
                }

            } catch (Exception ex) {
                Logger.getLogger(TransactionMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         return processPerson;
    }
    
    public static Person parsePerson(String personId) {
        PatientSource ps;
        MaritalStatus ms = null;
        Person processPerson = new Person();
        AccessPerson ap;

        
        IdentifierType identifierType = IdentifierType.CCC_NUMBER;
        PersonIdentifier identifier = new PersonIdentifier(identifierType, personId);
        processPerson.getPersonIdentifiers().add(identifier);

        //For uniformity purposes....
        identifierType = IdentifierType.PID_NUMBER;
        identifier = new PersonIdentifier(identifierType, personId);
        processPerson.getPersonIdentifiers().add(identifier);

        if (personId != null) {
            try {
                ap = new AccessQuery().getAccessPerson(personId);
                processPerson.setFirstName(ap.getFirstName());
                String lastNameToBeParsed = ap.getLastName();
                String[] names = ParseDateAndName.parseLastName(lastNameToBeParsed);
                if (names.length > 1) {
                    processPerson.setMiddleName(names[0]);
                    processPerson.setLastName(names[1]);
                } else {
                    processPerson.setLastName(names[0]);
                }

                //sort out the dob
                String dv2 = ap.getDob();

                if (StringUtils.isNotEmpty(dv2)) {
                    processPerson.setBirthdate(ParseDateAndName.parseDate(dv2));
                }

                //sort out the sex bit
                String sexV2 = ap.getSex();
                if (StringUtils.isNotEmpty(sexV2)) {
                    switch (sexV2) {
                        case "1": {
                            processPerson.setSex(Sex.MALE);
                            break;
                        }
                        case "2": {
                            processPerson.setSex(Sex.FEMALE);
                            break;
                        }
                        default: {
                            processPerson.setSex(Sex.UNKNOWN);
                            break;
                        }
                    }
                }

                //sort out the marital status thingy
                String statusV2 = ap.getMaritalStatus();
                if (StringUtils.isNotEmpty(statusV2)) {
                    switch (statusV2) {
                        case "1": {
                            ms = MaritalStatus.valueOf("POLYGAMOUS_MARRIED");
                            break;
                        }
                        case "2": {
                            ms = MaritalStatus.valueOf("MONOGAMOUS_MARRIED");
                            break;
                        }
                        case "3": {
                            ms = MaritalStatus.valueOf("DIVORCED");
                            break;
                        }
                        case "4": {
                            ms = MaritalStatus.valueOf("WIDOWED");
                            break;
                        }
                        case "5": {
                            ms = MaritalStatus.valueOf("COHABITING");
                            break;
                        }
                        case "6": {
                            ms = MaritalStatus.valueOf("SINGLE");
                            break;
                        }
                        case "7": {
                            ms = MaritalStatus.valueOf("SEPARATED");
                            break;
                        }
                        case "8": {
                            ms = MaritalStatus.valueOf("MISSING");
                            break;
                        }
                    }
                    processPerson.setMaritalStatus(ms);
                }

                //sort out the patient sorce thingy
                String source = ap.getPatientSource();
                if (StringUtils.isNotEmpty(source)) {
                    switch (source) {
                        case "1": {
                            ps = PatientSource.valueOf("PMTCT");
                            break;
                        }
                        case "2": {
                            ps = PatientSource.valueOf("VCT");
                            break;
                        }
                        case "3": {
                            ps = PatientSource.valueOf("OPD");
                            break;
                        }
                        case "4": {
                            ps = PatientSource.valueOf("IN PATIENT");
                            break;
                        }
                        case "5": {
                            ps = PatientSource.valueOf("CHILD WELFARE CLINIC");
                            break;
                        }
                        case "6": {
                            ps = PatientSource.valueOf("OPD");
                            break;
                        }
                        case "7": {
                            ps = PatientSource.valueOf("CHILD WELFARE CLINIC");
                            break;
                        }
                        case "8": {
                            ps = PatientSource.valueOf("MISSING");
                            break;
                        }
                        case "10": {
                            ps = PatientSource.valueOf("IPD ADULT");
                            break;
                        }
                        case "11": {
                            ps = PatientSource.valueOf("IPD CHILD");
                            break;
                        }
                        default: {
                            ps = PatientSource.valueOf("MISSING");
                            break;
                        }
                    }
                    processPerson.setPatientSource(ps);
                }

            } catch (Exception ex) {
                Logger.getLogger(TransactionMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         return processPerson;
    }
}
