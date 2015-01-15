package org.kemricdc.hapi;

import org.kemricdc.entities.AppProperties;

import ca.uhn.hl7v2.examples.ExampleReceiverApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.hoh.api.DecodeException;
import ca.uhn.hl7v2.hoh.api.EncodeException;
import ca.uhn.hl7v2.hoh.api.IReceivable;
import ca.uhn.hl7v2.hoh.api.ISendable;
import ca.uhn.hl7v2.hoh.api.MessageMetadataKeys;
import ca.uhn.hl7v2.hoh.hapi.api.MessageSendable;
import ca.uhn.hl7v2.hoh.hapi.client.HohClientSimple;
import ca.uhn.hl7v2.llp.LLPException;

import java.io.IOException;
import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SendHl7Message {
	private HapiContext hapiContext;
	private AppProperties appProperties;

	@Autowired
	public void setHapiContext(HapiContext hapiContext) {
		this.hapiContext = hapiContext;
	}

	@Autowired
	public void setAppProperties(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public void sendStringMessage(String hl7Message) {

		String host = appProperties.getProperty("host");
		System.out.println("The HOST number is: " + host);
		int port = Integer.parseInt(appProperties.getProperty("port"));
		System.out.println("The HOST number is: " + port);
		boolean useTLS = Boolean.parseBoolean(appProperties
				.getProperty("useTLS"));
		Parser p = hapiContext.getPipeParser();
		Connection connection = null;
		Initiator initiator;
		Message response;
		try {
			Message adt = p.parse(hl7Message);
			// A connection object represents a socket attached to an HL7 server
			connection = hapiContext.newClient(host, port, useTLS);
			// The initiator is used to transmit unsolicited messages
			initiator = connection.getInitiator();
			response = initiator.sendAndReceive(adt);

			String responseString = p.encode(response);
			System.out.println("\n\nReceived response:\n" + responseString);
		} catch (HL7Exception | LLPException ex) {
			Logger.getLogger(SendHl7Message.class.getName()).log(Level.SEVERE,
					null, ex.getMessage());
		}catch (ConnectException ex) {
			Logger.getLogger(SendHl7Message.class.getName()).log(Level.SEVERE,
					null, ex.getMessage());
		}
		catch (IOException ex) {
			Logger.getLogger(SendHl7Message.class.getName()).log(Level.SEVERE,
					null, ex.getMessage());
		}
		// finally {
		// if (connection != null) {
		// connection.close();
		// }
		// }
	}

	public void sendADTMessage(ADT_A01 adt) {

		String host = appProperties.getProperty("host");
		int port = Integer.parseInt(appProperties.getProperty("port"));
		String uri = appProperties.getProperty("uri");

		// Create a parser
		Parser parser = PipeParser.getInstanceWithNoValidation();

		// Create a client
		HohClientSimple client = new HohClientSimple(host, port, uri, parser);
		// The MessageSendable provides the message to send

		try {
			ISendable<Message> sendable = new MessageSendable(adt);
			// sendAndReceive actually sends the message
			IReceivable<Message> receivable = client
					.sendAndReceiveMessage(sendable);

			// receivable.getRawMessage() provides the response
			Message message = receivable.getMessage();
			System.out.println("\nResponse was:\n" + message.encode());

			// IReceivable also stores metadata about the message
			String remoteHostIp = (String) receivable.getMetadata().get(
					MessageMetadataKeys.REMOTE_HOST_ADDRESS);
			System.out.println("\nFrom:\n" + remoteHostIp);

			/*
			 * Note that the client may be reused as many times as you like, by
			 * calling sendAndReceiveMessage repeatedly
			 */
		} catch (DecodeException | EncodeException | HL7Exception | IOException e) {
			// Thrown if the response can't be read
			Logger.getLogger(SendHl7Message.class.getName()).log(Level.SEVERE,
					null, e);
		}

	}

	private void sendOverTCP(ADT_A01 adt_a01) {
		String host = appProperties.getProperty("host");
		int port = Integer.parseInt(appProperties.getProperty("port"));
		boolean useTLS = Boolean.parseBoolean(appProperties.getProperty("useTLS"));
		
		ca.uhn.hl7v2.app.HL7Service server = hapiContext.newServer(port, useTLS);

		/*
		 * The server may have any number of "application" objects registered to
		 * handle messages. We are going to create an application to listen to
		 * ADT^A01 messages.
		 * 
		 * You might want to look at the source of ExampleReceiverApplication
		 * (it's a nested class below) to see how it works.
		 */
		ReceivingApplication handler = (ReceivingApplication) new ExampleReceiverApplication();
		server.registerApplication("ADT", "A01", handler);

		/*
		 * We are going to register the same application to handle ADT^A02
		 * messages. Of course, we coud just as easily have specified a
		 * different handler.
		 */
		server.registerApplication("ADT", "A02", handler);
		try {
			// Start the server listening for messages
			server.startAndWait();
		} catch (InterruptedException ex) {
			Logger.getLogger(SendHl7Message.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}


}
