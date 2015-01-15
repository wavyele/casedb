package org.kemricdc.hapi;

import java.io.IOException;
import java.util.Set;

import org.kemricdc.entities.AppProperties;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.v24.message.*;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.parser.Parser;

@Component
public class PatientHl7Service implements HL7Service {

	private Person person;
	private AppProperties appProperties;
	private HapiContext context;
	private SendHl7Message sendHl7Message;

	
	public PatientHl7Service() {
		
	}
	public PatientHl7Service(Person person) {
		this.person = person;
	}

	@Autowired
	public void setSendHl7Message(SendHl7Message sendHl7Message) {
		this.sendHl7Message = sendHl7Message;
	}

	@Autowired
	public void setContext(HapiContext context) {
		this.context = context;
	}

	@Autowired
	public void setPerson(Person person) {
		this.person = person;
	}

	@Autowired
	public void setAppProperties(AppProperties appProperties) {
		this.appProperties = appProperties;

	}

	public void doWork(String trigger) {

		// switch (Triggers.valueOf(trigger)) {
		// case A04: {
		String hl7Message;
		try {
			hl7Message = generateHL7String(trigger);
			sendHl7Message.sendStringMessage(hl7Message);
		} catch (HL7Exception | IOException e) {
			e.printStackTrace();
		}

		// break;
		// }
		// case A08: {
		// break;
		// }
		// default: {
		// break;
		// }
		// }

	}

	@SuppressWarnings("unused")
	private ADT_A01 generateHL7(String triggerEvent) throws HL7Exception,
			IOException {

		ADT_A01 adt_a01 = new ADT_A01();
		adt_a01.initQuickstart("ADT", "A04", "T");

		// Populate the MSH Segment
		MSH mshSegment = adt_a01.getMSH();
		mshSegment.getSendingApplication().getNamespaceID()
				.setValue(appProperties.getProperty("application_name"));
		mshSegment.getSendingFacility().getNamespaceID()
				.setValue(appProperties.getProperty("facility_name"));
		mshSegment.getSequenceNumber().setValue(
				appProperties.getProperty("sequence_number"));
		mshSegment.getMessageType().getMessageStructure()
				.setValue("ADT_" + triggerEvent);

		// Populate the PID Segment
		PID pid = adt_a01.getPID();
		pid.getPatientName(0).getFamilyName().getSurname()
				.setValue(person.getLastName());
		pid.getPatientName(0).getGivenName().setValue(person.getFirstName());

		// pid.getPatientIdentifierList(0).getID().setValue(((PersonIdentifier)
		// person.getPersonIdentifiers().iterator().next()).getIdentifier());
		// Changed to the below to handle scenario where the patient has more
		// than one identifier
		Set<PersonIdentifier> identifiers = person.getPersonIdentifiers();
		System.err.println("PERSSFDASDFFSDF"+identifiers);
		int count = 0;
		for (PersonIdentifier personIdentifier : identifiers) {
			pid.getPatientIdentifierList(count).getID()
					.setValue(personIdentifier.getIdentifier());
			pid.getPatientIdentifierList(count).getIdentifierTypeCode()
					.setValue(personIdentifier.getIdentifierType().getValue());
			count++;
		}

		pid.getAdministrativeSex().setValue(person.getSex().getValue());
		pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(person.getDob());
		pid.getMaritalStatus().getText()
				.setValue(person.getMaritalStatus().getValue());

		/*
		 * Populate more fields and Segments
		 */
		// encoding the message and look at the output
		Parser parser = context.getPipeParser();
		String encodedMessage = parser.encode(adt_a01);
		System.err.println("\nPrinting ER7 Encoded Message:yawa");
		System.out.println("\n" + encodedMessage);

		// // Next, let's use the XML parser to encode as XML
		// parser = context.getXMLParser();
		// encodedMessage = parser.encode(adt_a01);
		// System.out.println("Printing XML Encoded Message:");
		// System.out.println(encodedMessage);
		return adt_a01;
	}

	private String generateHL7String(String triggerEvent) throws HL7Exception,
			IOException {

		ADT_A01 adt_a01 = new ADT_A01();
		adt_a01.initQuickstart("ADT", triggerEvent, "T");

		// Populate the MSH Segment
		MSH mshSegment = adt_a01.getMSH();
		mshSegment.getSendingApplication().getNamespaceID()
				.setValue(appProperties.getProperty("sending_application"));
		mshSegment.getSendingFacility().getNamespaceID()
				.setValue(appProperties.getProperty("sending_facility"));
		mshSegment.getSequenceNumber().setValue(
				appProperties.getProperty("sequence_number"));
		mshSegment.getMessageType().getMessageStructure()
				.setValue("ADT_" + triggerEvent);

		
		
		// Populate the PID Segment
		PID pid = adt_a01.getPID();
		pid.getPatientName(0).getFamilyName().getSurname()
				.setValue(person.getLastName());
		pid.getPatientName(0).getGivenName().setValue(person.getFirstName());
		pid.getPatientName(0).getSecondAndFurtherGivenNamesOrInitialsThereof()
				.setValue(person.getMiddleName());
		int count = 0;
		Set<PersonIdentifier> identifiers = person.getPersonIdentifiers();
		

		if(identifiers!=null){
			for (PersonIdentifier personIdentifier : identifiers) {
				pid.getPatientIdentifierList(count).getID()
						.setValue(personIdentifier.getIdentifier());
				pid.getPatientIdentifierList(count).getIdentifierTypeCode()
						.setValue(personIdentifier.getIdentifierType().getValue());
				count++;
			}
		}
		
		pid.getAdministrativeSex().setValue(person.getSex().getValue());
		pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(person.getDob());
		pid.getMaritalStatus().getCe1_Identifier()
				.setValue(person.getMaritalStatus().getValue());
		// pid.getMaritalStatus().getText().setValue(person.getMaritalStatusType().getValue());

		/*
		 * Populate more fields and Segments
		 */
		// encoding the message and look at the output
		// First, lets use the XML Parser to encode XML
		Parser parser = context.getXMLParser();
		String encodedMessage = parser.encode(adt_a01);
		System.out.println("\nPrinting XML Encoded Message:");
		System.out.println("\n" + encodedMessage);

		// Next, let's use the ER7 parser to encode as ER7
		parser = context.getPipeParser();
		encodedMessage = parser.encode(adt_a01);
		System.out.println("\nPrinting ER7 Encoded Message:");
		System.out.println("\n" + encodedMessage);

		return encodedMessage;
	}
}
