/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.icap.mappers.updates;

import accessquery.AccessQuery;
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
import org.kemricdc.entities.Person;
import org.kemricdc.hapi.SendHL7String;
import org.kemricdc.hapi.oru.OruFiller;
import org.kemricdc.hapi.oru.ProcessTransactions;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class ProcessVisitInformationUpdate {

    private final TransactionSet set;
    private Date cd4Instance;

    public ProcessVisitInformationUpdate(TransactionSet set) {
        this.set = set;
    }

    void processTblVisitInformationUpdate() {
        
        AccessQuery aq = new AccessQuery();
        Person visitPerson = new Person();
        List<OruFiller> fillers = new ArrayList<>();
        List<Transaction> transactions = set.getTransactions();
        String variable;
        String dataValue;
        Date visitDateConverted = null;
        for (Transaction transaction : transactions) {
            variable = transaction.getVariable();
            System.out.println("Processing....... " + variable);
            switch (variable) {
                case "visit_id": {
                    dataValue = transaction.getDataValue();
                    String personId = aq.getPersonIdByVisitId(dataValue);
                    visitPerson = ParsePerson.parsePerson(personId);
                    String s=aq.getVisitDate(dataValue);
                    visitDateConverted=ParseDateAndName.parseDate(s);
                    break;
                }
                case "visit_date": {
//                    //set the event date
//                    visitDate = transaction.getDataValue();
//                    if (StringUtils.isNotEmpty(visitDate)) {
//                        visitDateConverted = ParseDateAndName.parseDate(visitDate);
//                    }
                    break;
                }
                case "pmtct": {
                    //enter pmtct start details
                    String pmtct = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(pmtct)) {
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("19");
                        filler.setObservationIdentifierText(Event.PMTCT_INITIATION.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue("NA");
//                        filler.setUnits("Percent");
                        filler.setResultStatus("P");
//                    filler.setDateOfLastNormalValue(new Date());
                        filler.setDateTimeOfObservation(visitDateConverted);

                        fillers.add(filler);
                    }
                    break;
                }
                case "tb_status": {
                    //process status stuff
                    String tb = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(tb)) {
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("12");
                        filler.setObservationIdentifierText(Event.TB_DIAGNOSIS.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue("NA");
//                        filler.setUnits("Percent");
                        filler.setResultStatus("P");
//                    filler.setDateOfLastNormalValue(new Date());
                        filler.setDateTimeOfObservation(visitDateConverted);

                        fillers.add(filler);
                    }
                    break;

                }
                case "cotrim": {
                    //proces ctx stuff
                    String cotrim = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(cotrim)) {
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("24");
                        filler.setObservationIdentifierText(Event.COTRIMOXAZOLE.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue("NA");
//                        filler.setUnits("Percent");
                        filler.setResultStatus("P");
//                    filler.setDateOfLastNormalValue(new Date());
                        filler.setDateTimeOfObservation(visitDateConverted);

                        fillers.add(filler);
                    }
                    break;
                }
                case "art_regimen": {
                    //process art regimen stuff
                    //this was placed in the first visit...
                    String art = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(art)) {
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("3");
                        filler.setObservationIdentifierText(Event.ART_START.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue("NA");
//                        filler.setUnits("Percent");
                        filler.setResultStatus("P");
//                    filler.setDateOfLastNormalValue(new Date());
                        filler.setDateTimeOfObservation(visitDateConverted);

                        fillers.add(filler);
                    }

                    break;
                }
                case "cd4date": {
                    String cd4Date = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(cd4Date)) {
                        cd4Instance = ParseDateAndName.parseDate(cd4Date);
                    }
                    break;
                }
                case "cd4_results_percent": {
                    String cd4Per = transaction.getDataValue();

                    if (StringUtils.isNotEmpty(cd4Per)) {
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("23");
                        filler.setObservationIdentifierText(Event.CD4_PERCENT.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue(cd4Per);
                        filler.setUnits("Percent");
                        filler.setResultStatus("P");
//                    filler.setDateOfLastNormalValue(new Date());
                        filler.setDateTimeOfObservation(cd4Instance);

                        fillers.add(filler);
                    }
                }
                case "cd4result": {
                    String cd4Re = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(cd4Re)) {
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("6");
                        filler.setObservationIdentifierText(Event.CD4_COUNT.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue(cd4Re);
                        filler.setUnits("cells/\u03BC L");
                        filler.setResultStatus("P");
//                    filler.setDateOfLastNormalValue(new Date());
                        filler.setDateTimeOfObservation(cd4Instance);

                        fillers.add(filler);
                    }
                    break;
                }
                case "WHOstage": {
                    //process WHOstage
                    String whoString = transaction.getDataValue();

                    if (StringUtils.isNotEmpty(whoString)) {
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("11");
                        filler.setObservationIdentifierText(Event.WHO_STAGE.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue(whoString);
//                        filler.setUnits("cells//µL");
                        filler.setResultStatus("P");
//                    filler.setDateOfLastNormalValue(new Date());
//                        filler.setDateTimeOfObservation(visitDateConverted);
                        
                        //here is where the problem is
                        filler.setDateTimeOfObservation(visitDateConverted);

                        fillers.add(filler);

                    }
                    break;
                }
                case "TBStDate": {
                    String tbString = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(tbString)) {
                        Date d = ParseDateAndName.parseDate(tbString);
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("13");
                        filler.setObservationIdentifierText(Event.TB_TREATMENT.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue("NA");
//                        filler.setUnits("cells//µL");
                        filler.setResultStatus("P");
//                    filler.setDateOfLastNormalValue(new Date());
                        filler.setDateTimeOfObservation(d);

                        fillers.add(filler);
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
                        OruFiller filler = new OruFiller();
                        filler.setObservationIdentifier("7");
                        filler.setObservationIdentifierText(Event.VIRAL_LOAD.getValue());
                        filler.setCodingSystem("NA");
                        filler.setObservationSubId("1");
                        filler.setObservationValue(viralLoadString);
                        filler.setUnits("copies/\u03BC L");
                        filler.setResultStatus("P");
//                    filler.setDateOfLastNormalValue(new Date());
                        filler.setDateTimeOfObservation(visitDateConverted);

                        fillers.add(filler);
                    }
                    break;
                }

            }
        }

        System.out.println(visitPerson);
        for (OruFiller filler:fillers) {
            
            if(filler.getDateTimeOfObservation()==null){
                filler.setDateTimeOfObservation(visitDateConverted);
            }
            
        }

        ProcessTransactions pt = new ProcessTransactions(visitPerson, fillers);
        String s = null;
        try {
            s = pt.generateORU();
        } catch (HL7Exception | IOException ex) {
            Logger.getLogger(TransactionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        new SendHL7String().sendStringMessage(s);

    }

}
