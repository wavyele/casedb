/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc.hapi;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.Parser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kemricdc.hapi.adt.PatientRegistrationAndUpdate;
import org.kemricdc.utils.AppProperties;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class SendHL7String {

    private final HapiContext context = new DefaultHapiContext();

    public SendHL7String() {
    }

    public void sendStringMessage(String s) {

        String host = AppProperties.getProperty("host");
        System.out.println("The host is: " + host);
        int port = Integer.parseInt(AppProperties.getProperty("port"));
        System.out.println("The port is: " + port);
        boolean useTLS = Boolean.parseBoolean(AppProperties.getProperty("useTLS"));
        Parser p = context.getPipeParser();
        Connection connection = null;
        Initiator initiator;
        Message response;
        Message msg;
        try {
            msg = p.parse(s);
            // A connection object represents a socket attached to an HL7 server
            connection = context.newClient(host, port, useTLS);
            // The initiator is used to transmit unsolicited messages
            initiator = connection.getInitiator();
            response = initiator.sendAndReceive(msg);

            String responseString = p.encode(response);
            System.out.println("\n\nReceived response:\n" + responseString);
        } catch (HL7Exception | LLPException | IOException ex) {
            Logger.getLogger(PatientRegistrationAndUpdate.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            System.err.println("Error sending the message due to a connection exception");
            File f = new File(AppProperties.getProperty("hapi_dump"), new Random().nextInt()+"");
            try {
                System.out.println(f.getCanonicalPath());
            } catch (IOException ex1) {
                Logger.getLogger(SendHL7String.class.getName()).log(Level.SEVERE, null, ex1);
            }

            try (FileOutputStream fos = new FileOutputStream(f)) {
                byte[] b = s.getBytes();
                fos.write(b);
                fos.flush();
                fos.close();

            } catch (Exception e) {
                Logger.getLogger(PatientRegistrationAndUpdate.class.getName()).log(Level.SEVERE, null, e.getMessage());

            }

        } finally {
            if (connection != null) {
                System.out.println("About to close the connection...&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                connection.close();
            }
        }
    }
}
