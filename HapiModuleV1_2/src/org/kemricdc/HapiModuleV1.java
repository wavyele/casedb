/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc;

import ca.uhn.hl7v2.HL7Exception;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kemricdc.constants.IdentifierType;
import org.kemricdc.constants.MaritalStatus;
import org.kemricdc.constants.PatientSource;
import org.kemricdc.constants.Sex;
import org.kemricdc.entities.Address;
import org.kemricdc.entities.Location;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;
import org.kemricdc.hapi.SendHL7String;
import org.kemricdc.hapi.adt.PatientRegistrationAndUpdate;
import org.kemricdc.hapi.oru.OruFiller;
import org.kemricdc.hapi.oru.ProcessTransactions;

/**
 *
 * This is the test program for the modules for mining patient registrations and
 * patient transactions from the individual EMRs , converting the data into HL7
 * formats before sending them through to the CDS database
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

        //Set up the person
        System.err.println("\n\nThe Registration/Updates phase\n\n");

        Person p = new Person();
        p.setFirstName("stanslaus");
        p.setLastName("Odhiambo");
        p.setMiddleName("Michael");
        p.setSex(Sex.MALE);
        p.setMaritalStatus(MaritalStatus.SEPARATED);

        //Set 3 identifiers
        Set<PersonIdentifier> identifiers = new HashSet<>();
        PersonIdentifier pi = new PersonIdentifier();
        pi.setIdentifier("123456");
        pi.setIdentifierType(IdentifierType.CCC_NUMBER);
        identifiers.add(pi);

        pi = new PersonIdentifier();
        pi.setIdentifier("44444");
        pi.setIdentifierType(IdentifierType.HEI_NUMBER);
        identifiers.add(pi);

        pi = new PersonIdentifier();
        pi.setIdentifier("888888");
        pi.setIdentifierType(IdentifierType.NATIONAL_ID);
        identifiers.add(pi);

        //Add identifiers to the set
        p.setPersonIdentifiers(identifiers);

        //set the birth date
        p.setBirthdate(new Date());
        p.setLocation(new Location());
        p.setPatientSource(PatientSource.IPD_ADULT);
        p.setAddresses(new HashSet<Address>());

        //Leave this error for the time..reminds me of what is to be done.
        //No use of null values
        System.out.println(p);

        PatientRegistrationAndUpdate patientRegistration = new PatientRegistrationAndUpdate(p);
        patientRegistration.processRegistrationOrUpdate("A04");

        System.err.println("\n\nThe transaction phase\n\n");
//        Ensure person fields populated before passing to the constructor
        List<OruFiller> fillers = new ArrayList<>();

        //forming a sample OruFiller object
        OruFiller filler = new OruFiller();
        filler.setObservationIdentifier(null);
        filler.setObservationIdentifierText("HEIGHT");
        filler.setCodingSystem("AS4/SNOMED");
        filler.setObservationSubId("2");
        filler.setObservationValue("168");
        filler.setUnits("CM");
        filler.setResultStatus("P");
        filler.setDateOfLastNormalValue(new Date());
        filler.setDateTimeOfObservation(new Date());
        fillers.add(filler);
        
        //forming a sample OruFiller object
        filler = new OruFiller();
        filler.setObservationIdentifier(null);
        filler.setObservationIdentifierText("HEIGHT");
        filler.setCodingSystem("AS4/SNOMED");
        filler.setObservationSubId("2");
        filler.setObservationValue("168");
        filler.setUnits("CM");
        filler.setResultStatus("P");
        filler.setDateOfLastNormalValue(new Date());
        filler.setDateTimeOfObservation(new Date());
        fillers.add(filler);

        ProcessTransactions bXSegment = new ProcessTransactions(p, fillers);
        String bXString = bXSegment.generateORU();
        new SendHL7String().sendStringMessage(bXString);

    }

}
