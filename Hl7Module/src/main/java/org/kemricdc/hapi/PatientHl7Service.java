package org.kemricdc.hapi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import org.kemricdc.entities.AppProperties;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;

@Component
public class PatientHl7Service implements IHL7Service {

	@Autowired
	private Person person;
	@Autowired
	private AppProperties appProperties;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public PatientHl7Service() {

	}

	public PatientHl7Service(Person person) {
		this.person = person;
	}

	@Autowired
	public PatientHl7Service(Person person, AppProperties appProperties) {
		this.person = person;
		this.appProperties = appProperties;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setAppProperties(AppProperties appProperties) {
		this.appProperties = appProperties;

	}

	public void doWork(String trigger) {

		String hl7Message;
		try {
			hl7Message = generateHL7String(trigger);
			new SendHl7Message().sendMessage(hl7Message,
					(Integer) appProperties.getProperty("port"),
					(String) appProperties.getProperty("host"));
		} catch (Exception e) {
			if (e instanceof HL7Exception || e instanceof IOException) {
				e.printStackTrace();
			} else if (e instanceof NullPointerException) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unused")
	private ADT_A01 generateHL7(String triggerEvent) throws HL7Exception,
			IOException {

		ADT_A01 adt_a01 = new ADT_A01();

		// adt_a01.initQuickstart("ADT", "A04", "T");

		// Populate the MSH Segment
		MSH mshSegment = adt_a01.getMSH();

		mshSegment.getFieldSeparator().setValue("|");
		mshSegment.getEncodingCharacters().setValue("^~\\&");
		mshSegment.getSendingApplication().getNamespaceID()
				.setValue("TestSendingSystem");
		mshSegment.getReceivingApplication().getNamespaceID()
				.setValue("PAMSimulator");
		mshSegment.getProcessingID().getProcessingID().setValue("P");
		mshSegment.getSequenceNumber().setValue("123");
		mshSegment.getMessageType().getTriggerEvent().setValue("A28");
		mshSegment.getMessageType().getMessageStructure().setValue("ADT_A05");
		mshSegment.getVersionID().getVersionID().setValue("2.5");
		mshSegment.getMessageControlID().setValue(
				sdf.format(Calendar.getInstance().getTime()));
		mshSegment.getSendingFacility().getNamespaceID().setValue("OpenMRS");
		mshSegment.getReceivingFacility().getNamespaceID().setValue("IHE");
		mshSegment.getDateTimeOfMessage().getTimeOfAnEvent()
				.setValue(sdf.format(Calendar.getInstance().getTime()));

		mshSegment
				.getSendingApplication()
				.getNamespaceID()
				.setValue(
						(String) appProperties.getProperty("application_name"));
		mshSegment.getSendingFacility().getNamespaceID()
				.setValue((String) appProperties.getProperty("facility_name"));
		mshSegment.getSequenceNumber().setValue(
				(String) appProperties.getProperty("sequence_number"));
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
		System.err.println("PERSSFDASDFFSDF" + identifiers);
		int count = 0;
		for (PersonIdentifier personIdentifier : identifiers) {
			pid.getPatientIdentifierList(count).getID()
					.setValue(personIdentifier.getIdentifier());
			pid.getPatientIdentifierList(count).getIdentifierTypeCode()
					.setValue(personIdentifier.getIdentifierType().getValue());
			count++;
		}
		sdf.format(person.getDob());

		pid.getAdministrativeSex().setValue(person.getSex().getValue());
		pid.getDateTimeOfBirth().getTimeOfAnEvent()
				.setValue(sdf.format(person.getDob()));
		pid.getMaritalStatus().getText()
				.setValue(person.getMaritalStatus().getValue());

		/*
		 * Populate more fields and Segments
		 */
		// encoding the message and look at the output
		Parser parser = new PipeParser();
		String encodedMessage = parser.encode(adt_a01);
		System.err.println("\nPrinting ER7 Encoded Message:yawa");
		System.out.println("\n" + encodedMessage);

		// // Next, let's use the XML parser to encode as XML
		// parser = new XMLParser();
		// encodedMessage = parser.encode(adt_a01);
		// System.out.println("Printing XML Encoded Message:");
		// System.out.println(encodedMessage);
		return adt_a01;
	}

	private String generateHL7String(String triggerEvent) throws HL7Exception,
			IOException {

		ADT_A01 adt_a01 = new ADT_A01();
		// adt_a01.initQuickstart("ADT", triggerEvent, "T");

		// Populate the MSH Segment
		MSH mshSegment = adt_a01.getMSH();

		mshSegment.getFieldSeparator().setValue("|");
		mshSegment.getEncodingCharacters().setValue("^~\\&");
		mshSegment.getSendingApplication().getNamespaceID()
				.setValue("TestSendingSystem");
		mshSegment.getReceivingApplication().getNamespaceID()
				.setValue("PAMSimulator");
		mshSegment.getProcessingID().getProcessingID().setValue("P");
		mshSegment.getSequenceNumber().setValue("123");
		mshSegment.getMessageType().getTriggerEvent().setValue("A28");
		mshSegment.getMessageType().getMessageStructure().setValue("ADT_A01");
		mshSegment.getVersionID().getVersionID().setValue("2.5");
		mshSegment.getMessageControlID().setValue(
				sdf.format(Calendar.getInstance().getTime()));
		mshSegment.getSendingFacility().getNamespaceID().setValue("OpenMRS");
		mshSegment.getReceivingFacility().getNamespaceID().setValue("IHE");
		mshSegment.getDateTimeOfMessage().getTimeOfAnEvent()
				.setValue(sdf.format(Calendar.getInstance().getTime()));

		mshSegment
				.getSendingApplication()
				.getNamespaceID()
				.setValue(
						(String) appProperties
								.getProperty("sending_application"));
		mshSegment
				.getSendingFacility()
				.getNamespaceID()
				.setValue(
						(String) appProperties.getProperty("sending_facility"));
		mshSegment.getSequenceNumber().setValue(
				(String) appProperties.getProperty("sequence_number"));
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

		if (identifiers != null) {
			for (PersonIdentifier personIdentifier : identifiers) {
				pid.getPatientIdentifierList(count).getID()
						.setValue(personIdentifier.getIdentifier());
				pid.getPatientIdentifierList(count)
						.getIdentifierTypeCode()
						.setValue(
								personIdentifier.getIdentifierType().getValue());
				count++;
			}
		}

		pid.getAdministrativeSex().setValue(person.getSex().getValue());
		pid.getDateTimeOfBirth().getTimeOfAnEvent()
				.setValue(sdf.format(person.getDob()));
		pid.getMaritalStatus().getAlternateIdentifier()
				.setValue(person.getMaritalStatus().getValue());
		// pid.getMaritalStatus().getText().setValue(person.getMaritalStatusType().getValue());

		/*
		 * Populate more fields and Segments
		 */
		// encoding the message and look at the output

		Parser parser = new PipeParser();
		String encodedMessage = parser.encode(adt_a01);
		System.out.println("\nPrinting Encoded Message:");
		System.out.println("\n" + encodedMessage);

		return encodedMessage;
	}
}
