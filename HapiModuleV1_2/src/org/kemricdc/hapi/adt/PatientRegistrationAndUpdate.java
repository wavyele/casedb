/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc.hapi.adt;

import org.kemricdc.entities.Person;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.HL7Service;
import ca.uhn.hl7v2.examples.ExampleReceiverApplication;
import ca.uhn.hl7v2.hoh.api.DecodeException;
import ca.uhn.hl7v2.hoh.api.EncodeException;
import ca.uhn.hl7v2.hoh.api.IReceivable;
import ca.uhn.hl7v2.hoh.api.ISendable;
import ca.uhn.hl7v2.hoh.api.MessageMetadataKeys;
import ca.uhn.hl7v2.hoh.hapi.api.MessageSendable;
import ca.uhn.hl7v2.hoh.hapi.client.HohClientSimple;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v24.message.*;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kemricdc.entities.PersonIdentifier;
import org.kemricdc.hapi.SendHL7String;
import org.kemricdc.utils.AppProperties;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class PatientRegistrationAndUpdate {

 
    Person person;
    HapiContext context = new DefaultHapiContext();

    public PatientRegistrationAndUpdate(Person person) {
        this.person = person;

    }

    public void processRegistrationOrUpdate(String triggerEvent) {
        try {
            String s = generateHL7String(triggerEvent);
            new SendHL7String().sendStringMessage(s);

        } catch (HL7Exception | IOException ex) {
            Logger.getLogger(PatientRegistrationAndUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String generateHL7String(String triggerEvent) throws HL7Exception, IOException {

        ADT_A01 adt_a01 = new ADT_A01();
        adt_a01.initQuickstart("ADT", triggerEvent, "P");

        // Populate the MSH Segment
        MSH mshSegment = adt_a01.getMSH();
        mshSegment.getSendingApplication().getNamespaceID().setValue(AppProperties.getProperty("sending_application"));
        mshSegment.getSendingFacility().getNamespaceID().setValue(AppProperties.getProperty("sending_facility"));
        mshSegment.getReceivingApplication().getNamespaceID().setValue(AppProperties.getProperty("receiving_application"));
        mshSegment.getReceivingFacility().getNamespaceID().setValue(AppProperties.getProperty("receiving_station"));
        
        mshSegment.getSequenceNumber().setValue(AppProperties.getProperty("sequence_number"));
        mshSegment.getMessageType().getMessageStructure().setValue("ADT_" + triggerEvent);

        // Populate the PID Segment
        PID pid = adt_a01.getPID();
        pid.getPatientName(0).getFamilyName().getSurname().setValue(person.getLastName());
        pid.getPatientName(0).getGivenName().setValue(person.getFirstName());
        pid.getPatientName(0).getSecondAndFurtherGivenNamesOrInitialsThereof().setValue(person.getMiddleName());
        int count = 0;
        Set<PersonIdentifier> identifiers = person.getPersonIdentifiers();

        for (PersonIdentifier personIdentifier : identifiers) {
            pid.getPatientIdentifierList(count).getID().setValue(personIdentifier.getIdentifier());
            pid.getPatientIdentifierList(count).getIdentifierTypeCode().setValue(personIdentifier.getIdentifierType().getValue());
            count++;
        }
        pid.getAdministrativeSex().setValue(person.getSex().getValue());
        pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(person.getBirthdate());
        pid.getMaritalStatus().getCe1_Identifier().setValue(person.getMaritalStatus().getValue());
//        pid.getMaritalStatus().getText().setValue(person.getMaritalStatus().getValue());

        /*
         * Populate more fields and Segments
         */
        //  encoding the message and look at the output
        //First, lets use the XML Parser to encode XML
        Parser parser = context.getXMLParser();
        String encodedMessage = parser.encode(adt_a01);
        System.out.println("\nPrinting XML Encoded Message:\n");
        System.out.println("\n" + encodedMessage);

        // Next, let's use the ER7 parser to encode as ER7
        parser = context.getPipeParser();
        encodedMessage = parser.encode(adt_a01);
        System.out.println("\nPrinting ER7 Encoded Message:");
        System.out.println("\n" + encodedMessage);

        return encodedMessage;
    }

    /**
     * Sends an ADT_A01 message via http to the receiving application
     *
     * @param adt The message to be sent out
     */
    public void sendMessage(ADT_A01 adt) {
        String host = AppProperties.getProperty("host");
        int port = Integer.parseInt(AppProperties.getProperty("port"));
        String uri = AppProperties.getProperty("uri");

        // Create a parser
        Parser parser = PipeParser.getInstanceWithNoValidation();

        // Create a client
        HohClientSimple client = new HohClientSimple(host, port, uri, parser);
        // The MessageSendable provides the message to send 

        try {
            ISendable sendable = new MessageSendable(adt);
            // sendAndReceive actually sends the message
            IReceivable<Message> receivable = client.sendAndReceiveMessage(sendable);

            // receivavle.getRawMessage() provides the response
            Message message = receivable.getMessage();
            System.out.println("\nResponse was:\n" + message.encode());

            // IReceivable also stores metadata about the message
            String remoteHostIp = (String) receivable.getMetadata().get(MessageMetadataKeys.REMOTE_HOST_ADDRESS);
            System.out.println("\nFrom:\n" + remoteHostIp);

            /*
             * Note that the client may be reused as many times as you like,
             * by calling sendAndReceiveMessage repeatedly
             */
        } catch (DecodeException | EncodeException | HL7Exception | IOException e) {
            // Thrown if the response can't be read
            Logger.getLogger(PatientRegistrationAndUpdate.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    
    /**
     * Method to sent the "Stringified" message of any type to the host and port
     * specified in the properties file
     *
     * @param adt_a01
     */
    public void sendOverTCPs(ADT_A01 adt_a01) {
        String host = AppProperties.getProperty("host");
        int port = Integer.parseInt(AppProperties.getProperty("port"));
        boolean useTLS = Boolean.parseBoolean(AppProperties.getProperty("useTLS"));
        HapiContext context = new DefaultHapiContext();
        HL7Service server = context.newServer(port, useTLS);

        /*
         * The server may have any number of "application" objects registered to
         * handle messages. We are going to create an application to listen to
         * ADT^A01 messages.
         * 
         * You might want to look at the source of ExampleReceiverApplication
         * (it's a nested class below) to see how it works.
         */
        ReceivingApplication handler = new ExampleReceiverApplication();
        server.registerApplication("ADT", "A01", handler);

        /*
         * We are going to register the same application to handle ADT^A02
         * messages. Of course, we coud just as easily have specified a different
         * handler.
         */
        server.registerApplication("ADT", "A02", handler);
        try {
            // Start the server listening for messages
            server.startAndWait();
        } catch (InterruptedException ex) {
            Logger.getLogger(PatientRegistrationAndUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
