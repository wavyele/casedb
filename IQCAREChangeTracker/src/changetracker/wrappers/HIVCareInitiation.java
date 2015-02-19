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
public class HIVCareInitiation implements Runnable {
    
    @Override
    public void run() {
        try {
            hivCareInitiation();
            Thread.sleep(5*1000);
        } catch (Exception ex) {
            Logger.getLogger(WHOStaging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   public static void hivCareInitiation() throws HL7Exception, Exception
    {
        
        try{
            
            List care_initiation_results = ChangeTracker.getChangesFromTable(ChangeTracker.getLastVersion("Lnk_PatientProgramStart"), "Lnk_PatientProgramStart", "Ptn_pk");
            
            
            if(!care_initiation_results.isEmpty())
            {
            for(Object object:care_initiation_results)
            {
               HashMap record =  (HashMap)object;
               String change_operation = (String)record.get("SYS_CHANGE_OPERATION");
               
                
                if(change_operation.equalsIgnoreCase("I"))
                {
                    //(int)record.get("Ptn_pk");
                    
                    Person person = PersonFactory.getPerson((int)record.get("Ptn_pk"));
                    
                    
                    List<OruFiller> fillers = new ArrayList<>();
                    
                    OruFiller filler=new OruFiller();
                    filler.setObservationIdentifier(null);
                    if((int)record.get("ModuleId")==2)
                    {
                        System.out.println("Tracking Patient's HIV Care Initiation");
                        filler.setObservationIdentifierText(Event.HIV_CARE_INITIATION.getValue());
                    }
                    else if((int)record.get("ModuleId")==1)
                    {
                        System.out.println("Tracking Patient's PMTCT Initiation");
                        filler.setObservationIdentifierText(Event.PMTCT_INITIATION.getValue());
                    }
                    filler.setCodingSystem("ICD10");
                    filler.setObservationSubId(null);
                    filler.setObservationValue(null);
                    filler.setUnits(null);
                    filler.setResultStatus("P");
                    filler.setDateTimeOfObservation((Timestamp) record.get("CreateDate"));

                    fillers.add(filler);
                    
                    ProcessTransactions bXSegment = new ProcessTransactions(person,fillers);        
                    String bXString = bXSegment.generateORU();
                    new SendHL7String().sendStringMessage(bXString);
                    
                    
                   
                }
                
            }
            }
            else
            {System.out.println("No changes To Report!");}
        }catch(SQLException ex){
        ex.printStackTrace();
        }
       
        }  
}
