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
public class DeathAndTO implements Runnable {

    @Override
    public void run() {
        try {
            deathAndTransferOut();
        } catch (Exception ex) {
            Logger.getLogger(WHOStaging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deathAndTransferOut() throws HL7Exception, Exception {
        //OruFiller filler = null;
        try {

            List results = ChangeTracker.getChangesFromTable(ChangeTracker.getLastVersion("dtl_PatientCareEnded"), "dtl_PatientCareEnded", "CareEndedID");
            
            if (!results.isEmpty()) {
                for (Object object : results) {
                    HashMap record = (HashMap) object;
                    String change_operation = (String) record.get("SYS_CHANGE_OPERATION");

                    if (change_operation.equalsIgnoreCase("I")) {
                       
                        Person person = PersonFactory.getPerson((int) record.get("Ptn_Pk"));

                        List<OruFiller> fillers = new ArrayList<>();
                    
                        OruFiller filler=new OruFiller();
                        
                        filler.setObservationIdentifier(null);
                        if ((int) record.get("PatientExitReason") == 93) {
                            //this is death
                            filler.setObservationIdentifierText(Event.DECEASED.getValue());
                            filler.setObservationValue(((Timestamp) record.get("DeathDate")).toString());
                            //filler.set
                        } else if ((int) record.get("PatientExitReason") == 118) {
                            //this is transfer out
                            filler.setObservationIdentifierText(Event.TRANSFER_OUT.getValue());
                            filler.setObservationValue(((Timestamp) record.get("CreateDate")).toString());
                        } else if ((int) record.get("PatientExitReason") == 91) {
                            //this is transfer out
                            filler.setObservationIdentifierText(Event.LOST_TO_FOLLOWUP.getValue());
                            filler.setObservationValue(((Timestamp) record.get("CreateDate")).toString());
                        }else if ((int) record.get("PatientExitReason") == 92) {
                            //this is transfer out
                            filler.setObservationIdentifierText(Event.HIV_NEGATIVE.getValue());
                            filler.setObservationValue(((Timestamp) record.get("CreateDate")).toString());
                        }
                        filler.setCodingSystem("ICD10");
                        filler.setDateTimeOfObservation((Timestamp) record.get("CreateDate"));
                        filler.setObservationSubId(null);
                        //put death reason as the value

                        filler.setUnits(null);
                        filler.setResultStatus("P");
                        
                        fillers.add(filler);
                    
                        ProcessTransactions bXSegment = new ProcessTransactions(person,fillers);        
                        String bXString = bXSegment.generateORU();
                        new SendHL7String().sendStringMessage(bXString);
                        
                        
                    }

                }
            } else {
                System.out.println("No New Deaths Reported!");
                System.out.println("No New Transfer Outs Reported!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
