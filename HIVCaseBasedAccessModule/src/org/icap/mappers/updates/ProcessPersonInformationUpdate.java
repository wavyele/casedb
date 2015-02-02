/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.icap.mappers.updates;

import accessquery.AccessPerson;
import ca.uhn.hl7v2.HL7Exception;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.tool.hbm2x.StringUtils;
import org.icap.mappers.TransactionMapper;
import org.icap.utils.ParseDateAndName;
import org.icap.utils.ParsePerson;
import org.icap.utils.Transaction;
import org.icap.utils.TransactionSet;
import org.kemricdc.constants.Event;
import org.kemricdc.constants.MaritalStatus;
import org.kemricdc.constants.PatientSource;
import org.kemricdc.entities.Person;
import org.kemricdc.hapi.SendHL7String;
import org.kemricdc.hapi.adt.PatientRegistrationAndUpdate;
import org.kemricdc.hapi.oru.OruFiller;
import org.kemricdc.hapi.oru.ProcessTransactions;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class ProcessPersonInformationUpdate {

    private final TransactionSet set;
    private final String triggerEvent = "A08";
    private AccessPerson ap = new AccessPerson();
    private MaritalStatus ms;
    private PatientSource ps;

    public ProcessPersonInformationUpdate(TransactionSet set) {
        this.set = set;
    }

    public void processTblPersonInformationUpdate() {

        Date d = new Date();
        List<OruFiller> fillers = new ArrayList<>();
        Person person = new Person();
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
                    person = ParsePerson.parsePerson(transaction);
                    break;

                }

                case "transfer_date": {
                    //process transfer in for the patient
                    transferDate = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(transferDate)) {
                        d = ParseDateAndName.parseDate(transferDate);
                    }
                    break;
                }
                case "transfer_from": {
                    //process transfer from details
                    transferFromFacility = transaction.getDataValue();

                    //forming a sample OruFiller object
                    OruFiller filler = new OruFiller();
                    filler.setObservationIdentifier("5");
                    filler.setObservationIdentifierText(Event.TRANSFER_IN.getValue());
                    filler.setCodingSystem("NA");
                    filler.setObservationSubId("1");
                    filler.setObservationValue(transferFromFacility);
                    filler.setUnits("DATE");
                    filler.setResultStatus("P");
                    filler.setDateTimeOfObservation(d);
                    fillers.add(filler);
                    break;
                }
                case "artstart_date": {
                    String artStart = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(artStart)) {
                        d = ParseDateAndName.parseDate(artStart);
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("3");
                        filler.setObservationIdentifierText(Event.ART_START.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue("NA");
                        filler.setUnits("DATE");
                        filler.setResultStatus("P");
                        filler.setDateTimeOfObservation(d);
                        fillers.add(filler);
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
                    if (StringUtils.isNotEmpty(hivDate)) {
                        d = ParseDateAndName.parseDate(hivDate);
                    }

                    break;
                } //date of diagnosis
                case "hiv_pos_place": {

                    OruFiller filler = new OruFiller();
                    filler.setObservationIdentifier("1");
                    filler.setObservationIdentifierText(Event.HIV_DIAGNOSIS.getValue());
                    filler.setCodingSystem("NA");
                    filler.setObservationSubId("1");
                    filler.setObservationValue(transaction.getDataValue());
//                    filler.setUnits("CM");
                    filler.setResultStatus("P");
                    filler.setDateTimeOfObservation(d);
                    fillers.add(filler);
                    break;
                } //facility where diagnosis occured
                case "hiv_care_date": {
                    //process diagnosis date
                    String careDate = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(careDate)) {
                        d = ParseDateAndName.parseDate(careDate);
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("2");
                        filler.setObservationIdentifierText(Event.HIV_CARE_INITIATION.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue("NA");
                        filler.setUnits("DATE");
                        filler.setResultStatus("P");
                        filler.setDateTimeOfObservation(d);
                        fillers.add(filler);
                    }

                    break;
                } //date started on care
                case "who_stage": {
                    String whoStage = transaction.getDataValue();
                    OruFiller filler = new OruFiller();
                    filler.setObservationIdentifier("11");
                    filler.setObservationIdentifierText(Event.WHO_STAGE.getValue());
                    filler.setCodingSystem("NA");
                    filler.setObservationSubId("1");
                    filler.setObservationValue(whoStage);
//                    filler.setUnits("CM");
                    filler.setResultStatus("P");
//                    filler.setDateTimeOfObservation(d);
                    filler.setDateTimeOfObservation(d);
                    fillers.add(filler);
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
//                    //process elligibility to art date
//                    String artElligibilityDate = transaction.getDataValue();
////                    Events e = CRUDOperations.getInstance().getEvents(22);
//
//                    if (StringUtils.isNotEmpty(artElligibilityDate)) {
//                        d = parseDate(artElligibilityDate);
////                        processEvent(f, e, person, d);
//                    }

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
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("6");
                        filler.setObservationIdentifierText(Event.CD4_COUNT.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue(cd4Count);
                        filler.setUnits("copies/\u03BC L");
                        filler.setResultStatus("P");
//                    filler.setDateOfLastNormalValue(new Date());
                        filler.setDateTimeOfObservation(d);
                        fillers.add(filler);

                    }
                    break;
                }
                case "CD4_percent": {
                    String cd4Percent = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(cd4Percent)) {
                        Double d1 = Double.parseDouble(cd4Percent);
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("23");
                        filler.setObservationIdentifierText(Event.CD4_PERCENT.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue(cd4Percent);
                        filler.setUnits("Percent");
                        filler.setResultStatus("P");
//                    filler.setDateOfLastNormalValue(new Date());
                        filler.setDateTimeOfObservation(d);

                        fillers.add(filler);
                    }
                    break;
                }
                case "date_1stline_regimen": {
                    dateFirstLineRegimen = transaction.getDataValue();

                    if (StringUtils.isNotEmpty(dateFirstLineRegimen)) {
                        d = ParseDateAndName.parseDate(dateFirstLineRegimen);
                    }
                    break;
                }
                case "regimen_started": {
                    regimenStarted = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(regimenStarted)) {
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("17");
                        filler.setObservationIdentifierText(Event.FIRST_LINE_REGIMEN.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue(regimenStarted);
                        filler.setResultStatus("P");
                        filler.setDateTimeOfObservation(d);
                        fillers.add(filler);
                    }

                    break;
                }
                case "date_2ndline_regimen": {
                    dateSecondLineRegimen = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(dateSecondLineRegimen)) {
                        d = ParseDateAndName.parseDate(dateSecondLineRegimen);
                    }
                    break;
                }
                case "regimen_2ndstarted": {
                    regimenSecondStarted = transaction.getDataValue();

                    break;
                }
                case " reason_2ndline": {
                    String switchReason = transaction.getDataValue();
                    OruFiller filler = new OruFiller();
                    filler.setObservationIdentifier("18");
                    filler.setObservationIdentifierText(Event.SECOND_LINE_REGIMEN.getValue());
                    filler.setCodingSystem("NA");
                    filler.setObservationSubId("1");
                    filler.setObservationValue(regimenSecondStarted);
                    filler.setResultStatus("P");
                    filler.setDateTimeOfObservation(d);
                    filler.setExtraNote(switchReason);
                    fillers.add(filler);
                    break;
                }
            }
        }

        System.err.println("Person Details to be updated:  \n\n" + person);
        PatientRegistrationAndUpdate prau = new PatientRegistrationAndUpdate(person);
        prau.processRegistrationOrUpdate(triggerEvent);

        if (fillers.size() > 0) {
            ProcessTransactions pt = new ProcessTransactions(person, fillers);
            String s = null;
            try {
                s = pt.generateORU();
            } catch (HL7Exception | IOException ex) {
                Logger.getLogger(TransactionMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
            new SendHL7String().sendStringMessage(s);

        }

    }

}
