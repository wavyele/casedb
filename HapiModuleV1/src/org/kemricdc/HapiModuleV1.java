/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc;

import ca.uhn.hl7v2.HL7Exception;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.kemricdc.entities.Person;
import org.kemricdc.hapi.adt.PatientRegistration;
import org.kemricdc.hapi.oru.OruFiller;
import org.kemricdc.hapi.oru.ProcessTransactions;

/**
 *
 * This is the test program for the modules for mining patient registrations and 
 * patient transactions from the individual EMRs , converting the data into HL7 formats
 * before sending them through to the CDS database
 * @author Stanslaus Odhiambo
 */
public class HapiModuleV1 {

    /**
     * @param args the command line arguments
     * @throws ca.uhn.hl7v2.HL7Exception
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws HL7Exception, IOException {

        Person p = new Person();
        p.setFirstName("stanslaus");
        p.setLastName("Odhiambo");
        p.setSex("male");
        // TODO code application logic here

        //Leave this error for the time..reminds me of what is to be done.
        //No use of null values
        PatientRegistration patientRegistration = new PatientRegistration(p, null, null, null, null, null);
        patientRegistration.processRegistration();

        
        
        System.err.println("\n\nThe transaction phase\n\n");
//        Ensure person fields populated before passing to the constructor
        ProcessTransactions bXSegment = new ProcessTransactions(p);

//        Person p=new Person();
//        p.setFirstName("Demo First Name");
//        p.setLastName("DemoLastName");
//        p.setBirthdate(new Date());
//        p.setSex("male");
        List<OruFiller> fillers = new ArrayList<>();
        String bXString = bXSegment.generateORU(fillers);
        patientRegistration.sendStringMessage(bXString);
    }

}
