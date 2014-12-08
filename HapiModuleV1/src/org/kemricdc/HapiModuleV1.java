/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kemricdc;

import ca.uhn.hl7v2.HL7Exception;
import java.io.IOException;
import org.kemricdc.hapi.adt.PatientRegistration;
import org.kemricdc.hapi.oru.PopulateOBXSegment;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class HapiModuleV1 {

    /**
     * @param args the command line arguments
     * @throws ca.uhn.hl7v2.HL7Exception
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws HL7Exception, IOException {
        // TODO code application logic here
        PatientRegistration patientRegistration=new PatientRegistration();
        patientRegistration.processRegistration();
        
        PopulateOBXSegment bXSegment=new PopulateOBXSegment();
        String bXString=bXSegment.generateORU();
        patientRegistration.sendStringMessage(bXString);
    }
    
}

