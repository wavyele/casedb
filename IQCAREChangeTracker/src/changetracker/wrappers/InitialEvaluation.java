/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changetracker.wrappers;

import ca.uhn.hl7v2.HL7Exception;
import changetracker.util.ChangeTracker;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kemricdc.constants.Event;
import org.kemricdc.entities.Person;
import org.kemricdc.hapi.SendHL7String;
import org.kemricdc.hapi.oru.OruFiller;
import org.kemricdc.hapi.oru.ProcessTransactions;

/**
 *
 * @author Vicky
 */
public class InitialEvaluation implements Runnable{
   
    @Override
    public void run() {
        try {
            trackInitialEvaluationDetails();
        } catch (Exception ex) {
            Logger.getLogger(InitialEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void trackInitialEvaluationDetails() throws HL7Exception, Exception
    {
        
        try{
            
            List initialEvaluationDetails = ChangeTracker.getChangesFromTable(ChangeTracker.getLastVersion("dtl_PatientHivPrevCareIE"), "dtl_PatientHivPrevCareIE", "Visit_pk");
            
            if(!initialEvaluationDetails.isEmpty())
            {
            for(Object object:initialEvaluationDetails)
            {
               HashMap record =  (HashMap)object;
               String change_operation = (String)record.get("SYS_CHANGE_OPERATION");
                    
                if(change_operation.equalsIgnoreCase("I"))
                {
                    //(int)record.get("Ptn_pk");
                    Person person = PersonFactory.getPerson((int)record.get("Ptn_pk"));
                    
                    List<OruFiller> fillers = new ArrayList<>();
                    
                    if((int)record.get("longTermTBMed")!=0)
                    {   //NEW TB DIAGNOSIS
                        OruFiller filler=new OruFiller();
                        filler.setObservationIdentifier(null);
                        filler.setObservationIdentifierText(Event.TB_DIAGNOSIS.getValue());
                        filler.setCodingSystem("ICD10");
                        filler.setObservationSubId(null);
                        filler.setObservationValue((String) record.get("longTermTBMedDesc"));
                        filler.setUnits(null);
                        filler.setResultStatus("P");
                        filler.setDateTimeOfObservation((Timestamp) record.get("longTermTBStartDate"));

                        fillers.add(filler);
                    }
                    if( record.get("CurrentART")!=null)
                    {
                        //ART Start
                        OruFiller filler=new OruFiller();
                        filler.setObservationIdentifier(null);
                        filler.setObservationIdentifierText(Event.ART_START.getValue());
                        filler.setCodingSystem("ICD10");
                        filler.setObservationSubId(null);
                        filler.setObservationValue((String) record.get("CurrentART"));
                        filler.setUnits(null);
                        filler.setResultStatus("P");
                        filler.setDateTimeOfObservation((Timestamp) record.get("CurrentARTStartDate"));

                        fillers.add(filler);
                    }
                    if(record.get("PrevARVsCD4")!=null || record.get("PrevARVsCD4Percent")!=null)
                    {   //CD4 Count Prior To Starting ART
                        OruFiller filler=new OruFiller();
                        filler.setObservationIdentifier(null);
                        if(record.get("PrevARVsCD4")!=null)
                        {
                            filler.setObservationIdentifierText(Event.CD4_COUNT.getValue());
                            filler.setObservationValue(((java.math.BigDecimal)record.get("PrevARVsCD4")).toString());
                        }
                        else{
                            filler.setObservationIdentifierText(Event.CD4_PERCENT.getValue());
                            filler.setObservationValue((String)record.get("PrevARVsCD4Percent"));
                        }
                        filler.setCodingSystem("ICD10");
                        filler.setObservationSubId(null);
                        
                        filler.setUnits(null);
                        filler.setResultStatus("P");
                        filler.setDateTimeOfObservation((Timestamp) record.get("PrevARVsCD4Date"));

                        fillers.add(filler);
                    } 
                     if(record.get("PrevMostRecentCD4")!=null || record.get("PrevMostRecentCD4Percent")!=null)
                    {   //CD4 Count Most Recent CD4
                        OruFiller filler=new OruFiller();
                        filler.setObservationIdentifier(null);
                        if(record.get("PrevMostRecentCD4")!=null)
                        {
                            filler.setObservationIdentifierText(Event.CD4_COUNT.getValue());
                            filler.setObservationValue(((java.math.BigDecimal)record.get("PrevMostRecentCD4")).toString());
                        }
                        else{
                            filler.setObservationIdentifierText(Event.CD4_PERCENT.getValue());
                            filler.setObservationValue((String)record.get("PrevMostRecentCD4Percent"));
                        }
                        filler.setCodingSystem("ICD10");
                        filler.setObservationSubId(null);
                        
                        filler.setUnits(null);
                        filler.setResultStatus("P");
                        filler.setDateTimeOfObservation((Timestamp) record.get("PrevMostRecentCD4Date"));

                        fillers.add(filler);
                    }
                     
                     if(record.get("PrevMostRecentViralLoad")!=null)
                    {   //Viral Load
                        OruFiller filler=new OruFiller();
                        filler.setObservationIdentifier(null);
                        filler.setObservationIdentifierText(Event.VIRAL_LOAD.getValue());
                        filler.setObservationValue(((java.math.BigDecimal)record.get("PrevMostRecentViralLoad")).toString());
                        filler.setCodingSystem("ICD10");
                        filler.setObservationSubId(null);
                        filler.setUnits(null);
                        filler.setResultStatus("P");
                        filler.setDateTimeOfObservation((Timestamp) record.get("PrevMostRecentViralLoadDate"));

                        fillers.add(filler);
                    }
                    
                    ProcessTransactions bXSegment = new ProcessTransactions(person,fillers);        
                    String bXString = bXSegment.generateORU();
                    new SendHL7String().sendStringMessage(bXString);
                   
                }
               
            }
            }
            else
            {System.out.println("No Changes On Initial Evaluation!");}
        }catch(SQLException ex){
        ex.printStackTrace();
        }
       
        } 
}
