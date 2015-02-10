package org.kemricdc.hapi;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.kemricdc.entities.AppProperties;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;
import org.kemricdc.hapi.util.OruFiller;
import org.springframework.beans.factory.annotation.Autowired;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v24.datatype.NM;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.OBR;
import ca.uhn.hl7v2.model.v24.segment.OBX;
import ca.uhn.hl7v2.model.v24.segment.PID;

/**
 * Class that models the formation of HL7 event for transmission
 * @author Stanslaus Odhiambo
 *
 */

public class EventsHl7Service implements IHL7Service {
	private Person person;
	private AppProperties appProperties;
	private List<OruFiller> oruFillers;


	public EventsHl7Service() {
		
	}
	
	public EventsHl7Service(Person person, List<OruFiller> oruFillers) {
		this.person = person;
		this.oruFillers = oruFillers;
	}

	public void setOruFiller(List<OruFiller> oruFillers) {
		this.oruFillers = oruFillers;
	}

	@Autowired
	public void setSendHl7Message(SendHl7Message sendHl7Message) {
	}


	
	public void setPerson(Person person) {
		this.person = person;
	}

	@Autowired
	public void setAppProperties(AppProperties appProperties) {
		this.appProperties = appProperties;

	}

	public void doWork(String trigger) {
		String hl7Message;
		try {
			hl7Message = generateORU(trigger);
			new SendHl7Message().sendMessage(hl7Message, (Integer)appProperties.getProperty("port"), (String)appProperties.getProperty("host"));
		} catch (Exception e) {
			if(e instanceof HL7Exception || e instanceof IOException){
				e.printStackTrace();				
			}else if(e instanceof NullPointerException){
				e.printStackTrace();
			}
		}

	}

	/**
	 * The following message snippet is drawn (and modified for simplicity) from
	 * section 7.4.2.4 of the HL7 2.5 specification.
	 *
	 * In an ORU message, the OBR segment is used as a report header and
	 * contains important information about the order being fulfilled (i.e.
	 * order number, request date/time, observation date/time, ordering
	 * provider, etc.). It is part of a group that can be used more than once
	 * for each observation result that is reported in the message.
	 *
	 * <code>
	 * OBR|1||1234^LAB|88304
	 * OBX|1|CE|88304|1|T57000^GALLBLADDER^SNM
	 * OBX|2|TX|88304|1|THIS IS A NORMAL GALLBLADDER
	 * OBX|3|TX|88304&MDT|1|MICROSCOPIC EXAM SHOWS HISTOLOGICALLY NORMAL GALLBLADDER TISSUE
	 * </code>
	 *
	 * The following code attempts to generate this message structure.
	 *
	 * The HL7 spec defines, the following structure for an ORU^R01 message,
	 * represented in HAPI by the segment group:
	 *
	 * <code>
	 *                     ORDER_OBSERVATION start
	 *       {
	 *       [ ORC ]
	 *       OBR
	 *       [ { NTE } ]
	 *                     TIMING_QTY start
	 *          [{
	 *          TQ1
	 *          [ { TQ2 } ]
	 *          }]
	 *                     TIMING_QTY end
	 *       [ CTD ]
	 *                     OBSERVATION start
	 *          [{
	 *          OBX
	 *          [ { NTE } ]
	 *          }]
	 *                     OBSERVATION end
	 *       [ { FT1 } ]
	 *       [ { CTI } ]
	 *                     SPECIMEN start
	 *          [{
	 *          SPM
	 *          [ { OBX } ]
	 *          }]
	 *                     SPECIMEN end
	 *       }
	 *                     ORDER_OBSERVATION end
	 * </code>
	 * @param trigger 
	 *
	 * @return the encoded ORU^R01 message
	 * @throws HL7Exception
	 *             If any processing problem occurs
	 * @throws IOException
	 */
	public String generateORU(String trigger) throws HL7Exception, IOException {
		

		/**
		 *  First, a message object is constructed
		 */
		ORU_R01 message = new ORU_R01();

		/*
		 * 
		 * The initQuickstart method populates all of the mandatory fields in
		 * the MSH segment of the message, including the message type, the
		 * timestamp, and the control ID.
		 */
		/*message.initQuickstart(appProperties.getProperty("event_message_type"),
				trigger,
				appProperties.getProperty("event_processing_id"));*/

		MSH msh = message.getMSH();
		msh.getSendingApplication().getNamespaceID()
				.setValue((String) appProperties.getProperty("application_name"));
		msh.getSendingFacility().getNamespaceID()
				.setValue((String) appProperties.getProperty("facility_name"));
		msh.getMessageType().getMessageStructure()
		.setValue("ORU_" + trigger);

		// populate the receiving application details
		msh.getReceivingApplication().getNamespaceID()
				.setValue((String) appProperties.getProperty("cds_name"));
		msh.getReceivingFacility().getNamespaceID()
				.setValue((String) appProperties.getProperty("cdsapplication_name"));
		msh.getSequenceNumber().setValue((String) appProperties.getProperty("sequence_number"));

		ORU_R01_PATIENT oruPatient = message.getPATIENT_RESULT().getPATIENT();
		PID pid = oruPatient.getPID();
		pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(person.getDob().toString());
		pid.getPatientName(0).getFamilyName().getSurname()
				.setValue(person.getLastName());
		pid.getPatientName(0).getGivenName().setValue(person.getFirstName());
		pid.getAdministrativeSex().setValue(person.getSex().getValue());
		pid.getMaritalStatus().getAlternateIdentifier()
				.setValue(person.getMaritalStatus().getValue());

		// Changed to the below to handle scenario where the patient has more
		// than one identifier
		Set<PersonIdentifier> identifiers = person.getPersonIdentifiers();
		System.err.println(identifiers);
		int count = 0;
		if (identifiers!=null) {
			for (PersonIdentifier personIdentifier : identifiers) {
				pid.getPatientIdentifierList(count).getID()
						.setValue(personIdentifier.getIdentifier());
				pid.getPatientIdentifierList(count)
						.getIdentifierTypeCode()
						.setValue(
								personIdentifier.getIdentifierType().getValue());
				count++;
			}
			// pid.getPatientIdentifierList(0).getID().setValue("123456");
		}
		/*
		 * The OBR segment is contained within a group called ORDER_OBSERVATION,
		 * which is itself in a group called PATIENT_RESULT. These groups are
		 * reached using named accessors.
		 */
		ORU_R01_ORDER_OBSERVATION orderObservation = message
				.getPATIENT_RESULT().getORDER_OBSERVATION();

		// Populate the OBR
		OBR obr = orderObservation.getOBR();
		obr.getSetIDOBR().setValue((String) appProperties.getProperty("obr_id"));
		obr.getFillerOrderNumber().getEntityIdentifier()
				.setValue((String) appProperties.getProperty("fillerOrderNumberIdentifier"));
		obr.getFillerOrderNumber().getNamespaceID()
				.setValue((String) appProperties.getProperty("fillerOrderNumberNamespace"));

		obr.getUniversalServiceIdentifier().getIdentifier()
				.setValue((String) appProperties.getProperty("facility_mfl_code"));
		OBX obx = null;
		Varies value;

		for (int i = 0; i < oruFillers.size(); i++) {
			OruFiller oruFiller = oruFillers.get(i);
			/*
			 * The OBX segment is in a repeating group called OBSERVATION. You
			 * can use a named accessor which takes an index to access a
			 * specific repetition. You can ask for an index which is equal to
			 * the current number of repetitions,and a new repetition will be
			 * created.
			 */
			// ORU_R01_OBSERVATION observation =
			// orderObservation.getOBSERVATION(0);

			// Populate the OBXs
			obx = orderObservation.getOBSERVATION(i).getOBX();
			obx.getSetIDOBX().setValue((String) appProperties.getProperty(Integer.toString(i)));

			// We are working with a fixed value of ST - pretty much works for us
			obx.getValueType().setValue("ST");

			// Form the Observation Identifier
			obx.getObservationIdentifier().getIdentifier().setValue(oruFiller.getObservationIdentifier());
			obx.getObservationIdentifier().getText().setValue(oruFiller.getObservationIdentifierText());
			obx.getObservationIdentifier().getNameOfCodingSystem().setValue(oruFiller.getCodingSystem());

			// Form the Observation Sub-ID if necessary
			obx.getObservationSubId().setValue(oruFiller.getObservationSubId());

			// Form the Observation Value
			NM nm = new NM(message);
			nm.setValue(oruFiller.getObservationValue());
			value = obx.getObservationValue(0);
			value.setData(nm);

			// Form the Units
			obx.getUnits().getText().setValue(oruFiller.getUnits());

			// Form References Range
			obx.getReferencesRange().setValue(oruFiller.getReferencesRange());

			// Form the Abnormal Flags
			obx.getAbnormalFlags().setValue(oruFiller.getAbnormalFlags());

			// Form the Probability
			obx.getProbability(0).setValue(oruFiller.getProbability());

			// Form Nature of Abnormal Test
			obx.getNatureOfAbnormalTest().setValue(
					oruFiller.getNatureOfAbnormalTest());

			// Form Result Status
			obx.getObservationResultStatus().setValue(
					oruFiller.getResultStatus());

			// Form the Date of Last Normal Values
			obx.getDateLastObservationNormalValue().getTimeOfAnEvent()
					.setValue(oruFiller.getDateOfLastNormalValue());

			// set the User Defined Access Checks if necessary
			obx.getUserDefinedAccessChecks().setValue(
					oruFiller.getUserDefinedAccessChecks());

			// set Date/Time of the Observation
			obx.getDateTimeOfTheObservation().getTimeOfAnEvent()
					.setValue(oruFiller.getDateTimeOfObservation());

			// set the Producer's ID
			obx.getProducerSID().getText().setValue(oruFiller.getProducerId());

			// form the Responsible Observer
			obx.getResponsibleObserver().getIDNumber()
					.setValue(oruFiller.getResponsibleObserverId());
			obx.getResponsibleObserver().getGivenName()
					.setValue(oruFiller.getResponsibleObserverGivenName());

			// Form the Observation Method
			obx.getObservationMethod(0).getText()
					.setValue(oruFiller.getObservationMethod());

			//
			//
			// ST observationIdentifier =
			// obx.getObservationIdentifier().getIdentifier();
			// observationIdentifier.setValue(properties.getProperty("facility_mfl_code"));
			// //
			// obx.getObservationIdentifier().getIdentifier().setValue(properties.getProperty("facility_mfl_code"));
			// obx.getObservationSubId().setValue(properties.getProperty("application_code"));
			// switch (oruFiller.getValueType()) {
			// case CE: {
			// // The OBX has a value type of CE. So first, we populate OBX-2
			// with "CE"...
			// obx.getValueType().setValue("CE");
			// // ... then we create a CE instance to put in OBX-5.
			// CE ce = new CE(message);
			// ce.getNameOfCodingSystem().setValue(properties.getProperty("coding_system"));
			// value = obx.getObservationValue(0);
			// populateCeObx(obx, ce, (i + 1) + "", oruFiller.getIdentifier(),
			// oruFiller.getSubId(), oruFiller.getCeValue());
			// value.setData(ce);
			// break;
			// }
			// case TX: {
			// // The second OBX in the sample message has an extra subcomponent
			// at
			// // OBX-3-1. This component is actually an ST, but the HL7
			// specification allows
			// // extra subcomponents to be tacked on to the end of a component.
			// This is
			// // uncommon, but HAPI nontheless allows it.
			// // Already set the value up above
			// // observationIdentifier.setValue("88304");
			// ST extraSubcomponent = new ST(message);
			// extraSubcomponent.setValue("MDT");
			// observationIdentifier.getExtraComponents().getComponent(0).setData(extraSubcomponent);
			//
			// // The first OBX has a value type of TX. So first, we populate
			// OBX-2 with "TX"...
			// obx.getValueType().setValue("TX");
			//
			// // ... then we create a TX instance to put in OBX-5.
			// TX tx = new TX(message);
			// tx.setValue(oruFiller.getTxValue());
			//
			// value = obx.getObservationValue(0);
			// value.setData(tx);
			//
			// break;
			// }
			// }

		}

		// Print the message (remember, the MSH segment was not fully or
		// correctly populated)
		String finalString = message.getMessage().toString();//.encode();
		System.out.println(finalString);

		/*
		 * Something close to this would be returned
		 * 
		 * MSH|^~\&|||||20111102082111.435-0500||ORU^R01^ORU_R01|305|T|2.5
		 * OBR|1||1234^LAB|88304 OBX|1|CE|88304|1|T57000^GALLBLADDER^SNM
		 * OBX|2|TX|88304&MDT|1|MICROSCOPIC EXAM SHOWS HISTOLOGICALLY NORMAL
		 * GALLBLADDER TISSUE
		 */
		return finalString;

	}

}
