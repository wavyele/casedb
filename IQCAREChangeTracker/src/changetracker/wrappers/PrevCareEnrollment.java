/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changetracker.wrappers;

import ca.uhn.hl7v2.HL7Exception;
import changetracker.util.ChangeTracker;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
public class PrevCareEnrollment implements Runnable{
    
    @Override
    public void run() {
        try {
            trackPrevCareEnrollment();
        } catch (Exception ex) {
            Logger.getLogger(PrevCareEnrollment.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    public static void trackPrevCareEnrollment() throws HL7Exception, Exception
    {
        
        try{
            
            List hiv_diagnosis_results = ChangeTracker.getChangesFromTable(ChangeTracker.getLastVersion("dtl_PatientHivPrevCareEnrollment"), "dtl_PatientHivPrevCareEnrollment", "Visit_pk");
            
            
            
            if(!hiv_diagnosis_results.isEmpty())
            {
            for(Object object:hiv_diagnosis_results)
            {
               HashMap record =  (HashMap)object;
               String change_operation = (String)record.get("SYS_CHANGE_OPERATION");
                
                if(change_operation.equalsIgnoreCase("I"))
                {
                    
                    Person person = PersonFactory.getPerson((int)record.get("ptn_pk"));
                    
                    List<OruFiller> fillers = new ArrayList<>();
                    
                    
                     if((String) record.get("FirstHIVPosTestDate")!=null && !((String) record.get("FirstHIVPosTestDate")).equals(""))
                    {
                      
                        //forming a sample OruFiller object
                        OruFiller filler=new OruFiller();
                        filler.setObservationIdentifier(null);
                        filler.setObservationIdentifierText(Event.HIV_DIAGNOSIS.getValue());
                        filler.setCodingSystem("ICD10");
                        filler.setObservationSubId(null);
                        filler.setObservationValue(null);
                        filler.setUnits(null);
                        filler.setResultStatus("P");
                        filler.setDateTimeOfObservation((Date) record.get("FirstHIVPosTestDate"));

                        fillers.add(filler);
                    }
                     else{System.out.println("No New HIV Diagnosis Reported!");}
                     
                      if((String) record.get("ARTStartDate")!=null && !((String) record.get("ARTStartDate")).equals(""))
                    {
                        
                        OruFiller filler=new OruFiller();
                        filler.setObservationIdentifier(null);
                        filler.setObservationIdentifierText(Event.ART_START.getValue());
                        filler.setCodingSystem("ICD10");
                        filler.setObservationSubId(null);
                        filler.setObservationValue(null);
                        filler.setUnits(null);
                        filler.setResultStatus("P");
                        filler.setDateTimeOfObservation((Date) record.get("ARTStartDate"));

                        fillers.add(filler);
                    }
                      else{System.out.println("No New ART Start Reported!");}
                     
                    if(!fillers.isEmpty())
                    {
                        
                        ProcessTransactions bXSegment = new ProcessTransactions(person,fillers);        
                        String bXString = bXSegment.generateORU();
                        new SendHL7String().sendStringMessage(bXString);
                    }
                   
                }
               
            }
            }
            else
            {System.out.println("No changes On HIV Diagnosis To Report!");}
        }catch(SQLException ex){
        ex.printStackTrace();
        }
       
        }

    
    
}
