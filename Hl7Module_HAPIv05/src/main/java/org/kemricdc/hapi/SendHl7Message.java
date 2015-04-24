package org.kemricdc.hapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kemricdc.hapi.Connection.Hl7Connection;
import org.kemricdc.util.FilesUtil;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.EncodingNotSupportedException;
import ca.uhn.hl7v2.parser.GenericParser;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;

public class SendHl7Message {

	public String sendMessage(String hl7Message, int port, String host, String hl7MessagesDumpPath) throws EncodingNotSupportedException,
			RuntimeException {

		hl7Message = hl7Message.replace("\r", "|\r");
		System.out.println(hl7Message);
		/*
		 * Now, create a connection to that server, and send a message
		 */
		// Create a message to send
		Parser p = new GenericParser();

		// A connection object represents a socket attached to an HL7 server
		Connection connection;
		try {
			connection = Hl7Connection.getInstance(host, port);
			Initiator initiator = connection.getInitiator();
			Message message = p.parse(hl7Message);
			Message response = initiator.sendAndReceive(message);
			PipeParser parser = new PipeParser(); // The message parser
			String responseString = parser.encode(response);
			System.out.println(responseString);
			return responseString;
		} catch (HL7Exception e) {
			System.out.println("Error-HL7Exception: Unable to Parse HL7 message:");
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e.getMessage());
			System.err.println("Error sending the message due to a connection exception. \nThe "
					+ "HL7 message will be pushed to the HAPI dump folder for later processing!!");
			saveMessageToFileSystem(hl7Message,hl7MessagesDumpPath);
		} catch (LLPException e) {
			System.out.println("Error-LLPException: Unable to create connection:");
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e.getMessage());
			System.err.println("Error sending the message due to a connection exception. \nThe "
					+ "HL7 message will be pushed to the HAPI dump folder for later processing!!");
			saveMessageToFileSystem(hl7Message,hl7MessagesDumpPath);
		} catch (IOException e) {
			System.out.println("Error-IOException: Unable to Send HL7 message:" + e.getMessage().toString());
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e.getMessage());
			System.err.println("Error sending the message due to a connection exception. \nThe "
					+ "HL7 message will be pushed to the HAPI dump folder for later processing!!");
			saveMessageToFileSystem(hl7Message,hl7MessagesDumpPath);
		}

		// Close the connection
		// connection.close();

		return null;

	}
	
	public void saveMessageToFileSystem(String hl7Message,String hl7MessagesDumpPath){
		File f = new File(hl7MessagesDumpPath, new Random().nextInt() + ".hl7");
		
		try {
			FilesUtil.writeEmptyFile(f);
			System.out.println(f.getCanonicalPath());
		} catch (IOException ex1) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex1);
		}
		try {
			FilesUtil.setContents(f, hl7Message);
		} catch (FileNotFoundException e1) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, "File not found: " + e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e1) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, "Unable to write file: " + e1.getMessage());
			e1.printStackTrace();
		}

	}
}