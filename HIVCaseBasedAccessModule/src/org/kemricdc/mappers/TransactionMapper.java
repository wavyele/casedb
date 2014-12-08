/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc.mappers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.tool.hbm2x.StringUtils;
import org.kemricdc.constants.MaritalStatus;
import org.kemricdc.constants.Operation;
import org.kemricdc.constants.PatientSourceEnum;
import org.kemricdc.constants.TablesOfInterest;
import org.kemricdc.dao.CRUDOperations;
import org.kemricdc.entities.Address;
import org.kemricdc.entities.Events;
import org.kemricdc.entities.Facility;
import org.kemricdc.entities.IdentifierType;
import org.kemricdc.entities.Location;
import org.kemricdc.entities.MaritalStatusType;
import org.kemricdc.entities.PatientSource;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;
import org.kemricdc.entities.Personevents;
import org.kemricdc.entities.User;
import org.kemricdc.utils.Transaction;
import org.kemricdc.utils.TransactionSet;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class TransactionMapper {

    private Operation operation;
    private TablesOfInterest toi;
    private MaritalStatus ms;
    private PatientSourceEnum ps;
    private final Facility f = CRUDOperations.getInstance().getFacility(13852);
    private final Location l = CRUDOperations.getInstance().getloLocation(111);
    private Date cd4Instance = new Date();

    public void mapTransaction(TransactionSet set) {
        operation = set.getOperation();

        switch (operation) {
            case INSERT: {
                processInsert(set);
                break;
            }
            case UPDATE: {
                processUpdate(set);
                break;
            }
            case DELETE: {
                processDelete(set);
                break;
            }
        }
    }

    private void processInsert(TransactionSet set) {
        //this is a new record being inserted into the DB

        toi = TablesOfInterest.valueOf(set.getTableName().toUpperCase());//to review 
        switch (toi) {
            case TBLPATIENT_INFORMATION: {
                processTblPersonInformationInsert(set);
                break;

            }
            case TBLADDRESS: {
                processTblAddressInsert(set);
                //change this immediately after
                break;

            }

            case TBLVISIT_INFORMATION: {
                processTblVisitInformationInsert(set);
                break;
            }
        }

    }

    private void processUpdate(TransactionSet set) {
        toi = TablesOfInterest.valueOf(set.getTableName().toUpperCase());
        switch (toi) {
            case TBLPATIENT_INFORMATION: {
                processTblPersonInformationUpdate(set);
                break;
            }
            case TBLADDRESS: {
                processTblAddressUpdate(set);
                break;
            }
            case TBLVISIT_INFORMATION: {
                processTblVisitInformationUpdate(set);
                break;
            }
        }
    }

    private void processDelete(TransactionSet set) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void processTblPersonInformationInsert(TransactionSet set) {
        Date d = new Date();
        Integer createdId = null;
        MaritalStatusType statusType = CRUDOperations.getInstance().getMaritalStatusType(1);
        PatientSource patientSource = CRUDOperations.getInstance().getPatientSource(1);
        Person person = new Person(statusType, patientSource);
        String variable;
        String transferDate;
        String transferFromFacility;

        String dateFirstLineRegimen;
        String regimenStarted;

        String dateSecondLineRegimen;
        String regimenSecondStarted = null;

        List<Transaction> transactions = set.getTransactions();
        for (Transaction transaction : transactions) {
            variable = transaction.getVariable();
            System.out.println("Processing....... " + variable);
            switch (variable) {
                case "patient_id": {
                    createdId = CRUDOperations.getInstance().addPerson(person);
                    IdentifierType identifierType = CRUDOperations.getInstance().getIdentifierType(1);
                    PersonIdentifier identifier = new PersonIdentifier(identifierType, person, transaction.getDataValue());
                    person.getPersonIdentifiers().add(identifier);
                    CRUDOperations.getInstance().addPersonIdentifier(identifier);
                    break;
                }
                case "first_name": {
                    person.setFirstName(transaction.getDataValue());
                    break;
                }
                case "last_name": {
                    person.setLastName(transaction.getDataValue());
                    break;
                }
                case "dob": {
                    String dateValue = transaction.getDataValue();

                    if (StringUtils.isNotEmpty(dateValue)) {
                        //please don't try to understand this...it is the worst thing a programmer can ever do...n i just did it..shame on me
                        person.setBirthdate(parseDate(dateValue));
                    }

                    break;
                }

                case "sex": {

                    String sex = transaction.getDataValue();
                    switch (sex) {
                        case "1":
                            person.setSex("male");
                            break;
                        case "2":
                            person.setSex("female");
                            break;
                    }

                    break;
                }
                case "marital_status": {
                    String status = transaction.getDataValue();

                    if (status.length() > 5) {
                        ms = MaritalStatus.valueOf(status.toUpperCase());
                        switch (ms) {
                            case SINGLE: {
                                person.setMaritalStatusType(CRUDOperations.getInstance().getMaritalStatusType(1));
                                break;
                            }
                            case MONOGAMOUS_MARRIED: {
                                person.setMaritalStatusType(CRUDOperations.getInstance().getMaritalStatusType(2));
                                break;
                            }
                            case POLYGAMOUS_MARRIED: {
                                person.setMaritalStatusType(CRUDOperations.getInstance().getMaritalStatusType(3));
                                break;
                            }
                            case DIVORCED: {
                                person.setMaritalStatusType(CRUDOperations.getInstance().getMaritalStatusType(4));
                                break;
                            }
                            case SEPARATED: {
                                person.setMaritalStatusType(CRUDOperations.getInstance().getMaritalStatusType(5));
                                break;
                            }
                            case WIDOWED: {
                                person.setMaritalStatusType(CRUDOperations.getInstance().getMaritalStatusType(6));
                                break;
                            }
                            case COHABITING: {
                                person.setMaritalStatusType(CRUDOperations.getInstance().getMaritalStatusType(7));
                                break;
                            }
                            case MISSING: {
                                person.setMaritalStatusType(CRUDOperations.getInstance().getMaritalStatusType(8));
                                break;
                            }

                        }
                    }
                    break;
                }
                case "patient_source": {

                    String source = transaction.getDataValue();
                    if (source.length() > 5) {
                        ps = PatientSourceEnum.valueOf(source.toUpperCase());
                        switch (ps) {
                            case ANC: {
                                person.setPatientSource(CRUDOperations.getInstance().getPatientSource(1));
                                break;
                            }
                            case PMTCT: {
                                person.setPatientSource(CRUDOperations.getInstance().getPatientSource(2));
                                break;
                            }
                            case TB: {
                                person.setPatientSource(CRUDOperations.getInstance().getPatientSource(3));
                                break;
                            }
                            case VCT: {
                                person.setPatientSource(CRUDOperations.getInstance().getPatientSource(4));
                                break;
                            }
                            case TB_OPD: {
                                person.setPatientSource(CRUDOperations.getInstance().getPatientSource(5));
                                break;
                            }
                            case IN_PATIENT: {
                                person.setPatientSource(CRUDOperations.getInstance().getPatientSource(6));
                                break;
                            }
                            case CHILD_WELFARE_CLINIC: {
                                person.setPatientSource(CRUDOperations.getInstance().getPatientSource(7));
                                break;
                            }
                            case DTC: {
                                person.setPatientSource(CRUDOperations.getInstance().getPatientSource(8));
                                break;
                            }

                        }
                    }

                }
                case "source_other": {
//                            some code to assign the other source to the message
                    break;
                }
                case "transfer_date": {
                    //process transfer in for the patient
                    transferDate = transaction.getDataValue();
                    Events e = CRUDOperations.getInstance().getEvents(5);

                    if (StringUtils.isNotEmpty(transferDate)) {
                        d = parseDate(transferDate);
                        processEvent(f, e, person, d);
                    }

                    break;
                }
                case "transfer_from": {
                    //process transfer from details
                    transferFromFacility = transaction.getDataValue();
                    break;
                }
                case "artstart_date": {
                    String artStart = transaction.getDataValue();
                    Events e = CRUDOperations.getInstance().getEvents(3);

                    if (StringUtils.isNotEmpty(artStart)) {
                        d = parseDate(artStart);
                        processEvent(f, e, person, d);
                    }

                    break;
                }
                case "previous_arv": {
                    //yet to figure out the mapping on this one
                    break;
                }
                case "hiv_pos_date": {
                    //process diagnosis date
                    String hivDate = transaction.getDataValue();
                    Events e = CRUDOperations.getInstance().getEvents(1);

                    if (StringUtils.isNotEmpty(hivDate)) {
                        d = parseDate(hivDate);
                        processEvent(f, e, person, d);
                    }

                    break;
                } //date of diagnosis
                case "hiv_pos_place": {
                    //to be linked with the previous later...facility is hard coded for the time being
                    break;
                } //facility where diagnosis occured
                case "hiv_care_date": {
                    //process diagnosis date
                    String careDate = transaction.getDataValue();
                    Events e = CRUDOperations.getInstance().getEvents(2);

                    if (StringUtils.isNotEmpty(careDate)) {
                        d = parseDate(careDate);
                        processEvent(f, e, person, d);
                    }

                    break;
                } //date started on care
                case "who_stage": {
                    String whoStage = transaction.getDataValue();
                    Events e = CRUDOperations.getInstance().getEvents(2);
                    processEvent(f, e, person, d);
                    break;
                }
                case "pep_date": {
                    //not of interest for the time
                    break;
                }
                case "pep_reason": {
                    //not of interest at the moment
                    break;
                }
                case "art_eligibility_date": {
                    //process elligibility to art date
                    String artElligibilityDate = transaction.getDataValue();
                    Events e = CRUDOperations.getInstance().getEvents(22);

                    if (StringUtils.isNotEmpty(artElligibilityDate)) {
                        d = parseDate(artElligibilityDate);
                        processEvent(f, e, person, d);
                    }

                    break;
                }
                case "eligibility_criteria_who": {
                }//use these open cases to process the elligibility creteria
                case "eligibility_criteria_cd4": {
                }
                case "eligibility_criteria_cd4p": {
                }
                case "eligibility_criteria_tlc": {
                    break;
                }
                case "CD4_Count": {
                    String cd4Count = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(cd4Count)) {
                        Double d1 = Double.parseDouble(cd4Count);
                        Events e = CRUDOperations.getInstance().getEvents(6);
                        processEvent(f, e, person, d, d1);
                    }
                    break;
                }
                case "CD4_percent": {
                    String cd4Percent = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(cd4Percent)) {
                        Double d1 = Double.parseDouble(cd4Percent);
                        Events e = CRUDOperations.getInstance().getEvents(23);
                        processEvent(f, e, person, d, d1);
                    }
                    break;
                }
                case "date_1stline_regimen": {
                    dateFirstLineRegimen = transaction.getDataValue();

                    if (StringUtils.isNotEmpty(dateFirstLineRegimen)) {
                        d = parseDate(dateFirstLineRegimen);
                    }
//                                processEvent(f, e, person, d);

                    break;
                }
                case "regimen_started": {
                    regimenStarted = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(regimenStarted)) {
                        Double d1 = Double.parseDouble(regimenStarted);
                        Events e = CRUDOperations.getInstance().getEvents(17);
                        processEvent(f, e, person, d, d1);
                    }

                    break;
                }
                case "date_2ndline_regimen": {
                    dateSecondLineRegimen = transaction.getDataValue();

                    if (StringUtils.isNotEmpty(dateSecondLineRegimen)) {
                        d = parseDate(dateSecondLineRegimen);
//                                processEvent(f, e, person, d);
                    }

                    break;
                }
                case "regimen_2ndstarted": {
                    regimenSecondStarted = transaction.getDataValue();

                    break;
                }
                case " reason_2ndline": {
                    String switchReason = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(regimenSecondStarted)) {
                        Double d1 = Double.parseDouble(regimenSecondStarted);
                        Events e = CRUDOperations.getInstance().getEvents(18);
                        processEvent(f, e, person, d, d1, switchReason);
                    }

                    break;
                }
            }
        }
        CRUDOperations.getInstance().updatePerson(person);
        System.out.println(person);

    }

    private void processTblAddressInsert(TransactionSet set) {
        Person addressPerson = null;
        Address address = new Address();

        //This is hardcoded for the time , erxpected to be mined from the db when we start working on multiple
        //facilities
        Location location = CRUDOperations.getInstance().getloLocation(111);
        List<Transaction> transactions = set.getTransactions();
        String variable;
        String dataValue;
        for (Transaction transaction : transactions) {
            variable = transaction.getVariable();
            System.out.println("Processing....... " + variable);

            switch (variable) {
                case "patient_id": {
                    dataValue = transaction.getDataValue();
                    addressPerson = CRUDOperations.getInstance().getPersonIdByIdentifier(dataValue);

                    address.setPerson(addressPerson);
                    break;
                }
                case "postal_address": {
                    dataValue = transaction.getDataValue();
                    address.setPostalAddress(dataValue);
                    break;
                }
                case "telephone": {
                    dataValue = transaction.getDataValue();
                    address.setTelephone(dataValue);
                    break;
                }
                case "district": {
                    break;
                }
                case "location": {
                    break;
                }
                case "sub_location": {
                    break;
                }
                case "nearest_school": {
                    break;
                }
                case "nearest_health_center": {
                    break;
                }

            }

        }
        addressPerson.setLocation(location);

        CRUDOperations.getInstance().addAddress(address);
    }

    private void processTblVisitInformationInsert(TransactionSet set) {

        Events e = null;

        List<Transaction> transactions = set.getTransactions();
        Personevents personevents = new Personevents();
        String variable;
        String dataValue;
        Person visitPerson = null;
        String visitDate;
        Date visitDateConverted = null;
        for (Transaction transaction : transactions) {
            variable = transaction.getVariable();
            System.out.println("Processing....... " + variable);
            switch (variable) {
                case "patient_id": {
                    dataValue = transaction.getDataValue();
                    visitPerson = CRUDOperations.getInstance().getPersonIdByIdentifier(dataValue);
                    personevents.setPerson(visitPerson);
                    break;
                }
                case "visit_date": {
                    //set the event date
                    visitDate = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(visitDate)) {
                        visitDateConverted = parseDate(visitDate);
                    }

                    break;

                }
                case "pmtct": {
                    //enter pmtct start details
                    String pmtct = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(pmtct)) {
                        e = CRUDOperations.getInstance().getEvents(19);
                        processEvent(f, e, visitPerson, visitDateConverted);
                    }
                    break;
                }
                case "tb_status": {
                    //process status stuff
                    String tb = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(tb)) {
                        e = CRUDOperations.getInstance().getEvents(12);
                        processEvent(f, e, visitPerson, visitDateConverted);
                    }
                    break;

                }
                case "cotrim": {
                    //proces ctx stuff
                    String cotrim = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(cotrim)) {
                        e = CRUDOperations.getInstance().getEvents(24);
                        processEvent(f, e, visitPerson, visitDateConverted);
                    }
                    break;
                }
                case "art_regimen": {
                    //process art regimen stuff
                    //this was placed in the first visit...

                    break;
                }
                case "cd4date": {
                    String cd4Date = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(cd4Date)) {
                        cd4Instance = parseDate(cd4Date);
                    }
                    break;
                }
                case "cd4_results_percent": {
                    String cd4Per = transaction.getDataValue();

                    if (StringUtils.isNotEmpty(cd4Per)) {
                        Double cd4Double = Double.parseDouble(cd4Per);
                        e = CRUDOperations.getInstance().getEvents(23);
                        processEvent(f, e, visitPerson, cd4Instance, cd4Double);
                    }
                }
                case "cd4result": {
                    String cd4Re = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(cd4Re)) {
                        Double cd4Double = Double.parseDouble(cd4Re);
                        e = CRUDOperations.getInstance().getEvents(6);
                        processEvent(f, e, visitPerson, cd4Instance, cd4Double);
                    }
                    break;
                }
                case "WHOstage": {
                    //process WHOstage
                    String whoString = transaction.getDataValue();
                    
                    if (StringUtils.isNotEmpty(whoString)) {
                        int stage = Integer.parseInt(whoString);
                        
                        switch (stage) {
                            case 1: {
                                e = CRUDOperations.getInstance().getEvents(8);
                                break;
                            }
                            case 2: {
                                e = CRUDOperations.getInstance().getEvents(8);
                                break;
                            }
                            case 3: {
                                e = CRUDOperations.getInstance().getEvents(8);
                                break;
                            }
                            case 4: {
                                e = CRUDOperations.getInstance().getEvents(8);
                                break;
                            }
                        }

                        processEvent(f, e, visitPerson, cd4Instance);

                    }
                    break;
                }
                case "TBStDate": {
                    String tbString = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(tbString)) {
                        e = CRUDOperations.getInstance().getEvents(12);
                        processEvent(f, e, visitPerson, cd4Instance);
                    }

                    break;
                }
                case "TBEdDate": {
                    //processs TBEdDate
                    break;

                }
                case "ViralLoad": {
                    //process ViralLoad
                    String viralLoadString = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(viralLoadString)) {
                        e = CRUDOperations.getInstance().getEvents(7);
                        processEvent(f, e, visitPerson, cd4Instance);
                    }
                    break;
                }

            }
        }

    }

    private void processEvent(Facility facility, Events events, Person person, Date eventDate) {
//        Personevents p=new Personevents(facility, event, person)
        Personevents p = new Personevents(facility, events, person);
        p.setEventdatetime(eventDate);

        CRUDOperations.getInstance().addPersonEvent(p);
    }

    private void processEvent(Facility facility, Events events, Person person, Date eventDate, Double value) {
//        Personevents p=new Personevents(facility, event, person)
        Personevents p = new Personevents(facility, events, person);
        p.setEventdatetime(eventDate);
        p.setValueNumeric(value);

        CRUDOperations.getInstance().addPersonEvent(p);
    }

    private void processEvent(Facility facility, Events events, Person person, Date eventDate, Double value, String remark) {
//        Personevents p=new Personevents(facility, event, person)
        Personevents p = new Personevents(facility, events, person);
        p.setEventdatetime(eventDate);
        p.setValueNumeric(value);
        p.setRemarks(remark);

        CRUDOperations.getInstance().addPersonEvent(p);
    }

    //I'm slow on implementing the below with the assumption that we shall assume most of the data would be correct
    //and the focuss is more on new occurrences during visits as opposed to edits
    private void processTblPersonInformationUpdate(TransactionSet set) {
        //code to track updates on tblinformation
    }

    private void processTblAddressUpdate(TransactionSet set) {
        //code to track changes on the address table
    }

    private void processTblVisitInformationUpdate(TransactionSet set) {
        //code to track changes on the visit 
    }

    public static Date parseDate(String dateString) {
        
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date startDate = df.parse(dateString);
            System.out.println(startDate);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }

        return null;

    }

}
