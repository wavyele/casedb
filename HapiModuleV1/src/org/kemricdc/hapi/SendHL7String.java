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
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kemricdc.hapi.adt.PatientRegistration;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class SendHL7String {
    private static final Properties properties = new Properties();
    private static final HapiContext context = new DefaultHapiContext();
    
    public static void sendStringMessage(String s) {
        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));
        boolean useTLS = Boolean.parseBoolean(properties.getProperty("useTLS"));
        Parser p = context.getPipeParser();
        Connection connection = null;
        Initiator initiator;
        Message response;
        try {
            Message adt = p.parse(s);
            // A connection object represents a socket attached to an HL7 server
            connection = context.newClient(host, port, useTLS);
            // The initiator is used to transmit unsolicited messages
            initiator = connection.getInitiator();
            response = initiator.sendAndReceive(adt);

            String responseString = p.encode(response);
            System.out.println("\n\nReceived response:\n" + responseString);
        } catch (HL7Exception | LLPException | IOException ex) {
            Logger.getLogger(PatientRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
