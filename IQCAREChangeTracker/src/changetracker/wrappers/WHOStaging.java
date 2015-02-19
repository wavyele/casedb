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
public class WHOStaging implements Runnable {
    
    @Override
    public void run() {
        try {
            trackWHOStageChange();
        } catch (Exception ex) {
            Logger.getLogger(WHOStaging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void trackWHOStageChange() throws HL7Exception, Exception
    {
        
        try{
            
            List who_stage_results = ChangeTracker.getChangesFromTable(ChangeTracker.getLastVersion("dtl_PatientStage"), "dtl_PatientStage", "WABStageID");
            
            if(!who_stage_results.isEmpty())
            {
            for(Object object:who_stage_results)
            {
               HashMap record =  (HashMap)object;
               String change_operation = (String)record.get("SYS_CHANGE_OPERATION");
               
               String who_stage;
               
               switch((int)record.get("WHOStage")){
                   case 87:
                       who_stage = "1";
                       break;
                   case 88:
                       who_stage = "2";
                       break;
                   case 89:
                       who_stage = "3";
                       break;
                   case 90:
                       who_stage = "4";
                       break;
                   default:
                       who_stage = "0";
                       break;
                }
               
                
                Timestamp update_date =   (Timestamp) record.get("UpdateDate");
                Timestamp ART_start_date =   (Timestamp) record.get("ARTStartDate");
                
                if(change_operation.equalsIgnoreCase("I"))
                {
                    //(int)record.get("Ptn_pk");
                    Person person = PersonFactory.getPerson((int)record.get("Ptn_pk"));
                    
                    System.err.println("\n\nThe transaction phase\n\n");
                    
                    List<OruFiller> fillers = new ArrayList<>();
                    
                    OruFiller filler=new OruFiller();
                    filler.setObservationIdentifier(null);
                    filler.setObservationIdentifierText(Event.WHO_STAGE.getValue());
                    filler.setCodingSystem("ICD10");
                    filler.setObservationSubId(null);
                    filler.setObservationValue(who_stage);
                    filler.setUnits(null);
                    filler.setResultStatus("P");
                    filler.setDateTimeOfObservation(ChangeTracker.getVisitDate((int)record.get("Visit_Pk")));

                    fillers.add(filler);
                    
                    ProcessTransactions bXSegment = new ProcessTransactions(person,fillers);        
                    String bXString = bXSegment.generateORU();
                    new SendHL7String().sendStringMessage(bXString);
                   
                }
               
            }
            }
            else
            {System.out.println("No Changes On WHO Staging!");}
        }catch(SQLException ex){
        ex.printStackTrace();
        }
       
        } 

    
}
