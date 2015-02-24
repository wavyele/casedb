package org.kemricdc.hapi.Connection;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.ConnectionHub;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.llp.MinLowerLayerProtocol;
import ca.uhn.hl7v2.parser.PipeParser;

public class Hl7Connection {

	private static Connection connection;

	public static Connection getInstance(String host, int port)
			throws HL7Exception,LLPException {
		System.out.println("Connecting to Host: " + host);
		System.out.println("Connecting to Port: " + port);
		if (connection == null) {
			return ConnectionHub.getInstance().attach(host, port,
					new PipeParser(), MinLowerLayerProtocol.class);
		} else {
			return connection;
		}
	}
}
