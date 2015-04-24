package org.kemricdc.hapi;

import java.io.IOException;
import java.util.List;

import org.kemricdc.entities.AppProperties;
import org.kemricdc.entities.Person;
import org.kemricdc.hapi.util.MshSegmentFiller;
import org.kemricdc.hapi.util.OruFiller;
import org.kemricdc.hapi.util.PidSegmentFiller;
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
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;

/**
 * Class that models the formation of HL7 event for transmission
 * 
 * @author Stanslaus Odhiambo
 * 
 */

public class EventsHl7Service implements IHL7Service {
	@Autowired
	private Person person;
	@Autowired
	private AppProperties appProperties;
	@Autowired
	private List<OruFiller> oruFillers;

	public EventsHl7Service() {

	}

	@Autowired
	public EventsHl7Service(Person person, List<OruFiller> oruFillers, AppProperties appProperties) {
		this.person = person;
		this.oruFillers = oruFillers;
		this.appProperties = appProperties;
	}

	public void setOruFiller(List<OruFiller> oruFillers) {
		this.oruFillers = oruFillers;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setAppProperties(AppProperties appProperties) {
		this.appProperties = appProperties;

	}

	public List<OruFiller> getOruFillers() {
		return oruFillers;
	}

	public void setOruFillers(List<OruFiller> oruFillers) {
		this.oruFillers = oruFillers;
	}

	public Person getPerson() {
		return person;
	}

	public AppProperties getAppProperties() {
		return appProperties;
	}

	public void doWork(String trigger) {
		String hl7Message;
		try {
			hl7Message = generateORU(trigger);
			//new SendHl7Message().sendMessage(hl7Message, Integer.valueOf((String) appProperties.getProperty("port")),
			//		(String) appProperties.getProperty("host"), (String) appProperties.getProperty("hapi_dump"));
			new SendHl7Message().saveMessageToFileSystem(hl7Message, (String) appProperties.getProperty("hapi_dump"));
			
		} catch (Exception e) {
			if (e instanceof HL7Exception || e instanceof IOException) {
				e.printStackTrace();
			} else if (e instanceof NullPointerException) {
				e.printStackTrace();
			}
		}

	}

	public String generateORU(String triggerEvent) throws HL7Exception, IOException {

		/**
		 * First, a message object is constructed
		 */
		ORU_R01 message = new ORU_R01();

		MSH mshSegment = message.getMSH();

		MshSegmentFiller mshSegmentFiller = new MshSegmentFiller(mshSegment, appProperties, "ORU", triggerEvent);
		mshSegment = mshSegmentFiller.fillMshSegment();

		ORU_R01_PATIENT oruPatient = message.getPATIENT_RESULT().getPATIENT();
		// Populate the PID Segment
		PID pid = oruPatient.getPID();
		PidSegmentFiller pidSegmentFiller = new PidSegmentFiller(pid, person);
		pid = pidSegmentFiller.fillPidSegment();

		/*
		 * The OBR segment is contained within a group called ORDER_OBSERVATION,
		 * which is itself in a group called PATIENT_RESULT. These groups are
		 * reached using named accessors.
		 */
		ORU_R01_ORDER_OBSERVATION orderObservation = message.getPATIENT_RESULT().getORDER_OBSERVATION();

		// Populate the OBR
		OBR obr = orderObservation.getOBR();
		obr.getSetIDOBR().setValue((String) appProperties.getProperty("facility_mfl_code"));
		obr.getFillerOrderNumber().getEntityIdentifier().setValue((String) appProperties.getProperty("fillerOrderNumberIdentifier"));
		obr.getFillerOrderNumber().getNamespaceID().setValue((String) appProperties.getProperty("fillerOrderNumberNamespace"));

		obr.getUniversalServiceIdentifier().getIdentifier().setValue((String) appProperties.getProperty("facility_mfl_code"));
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
			obx.getSetIDOBX().setValue(String.valueOf(i));

			// We are working with a fixed value of ST - pretty much works for
			// us
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
			obx.getNatureOfAbnormalTest().setValue(oruFiller.getNatureOfAbnormalTest());

			// Form Result Status
			obx.getObservationResultStatus().setValue(oruFiller.getResultStatus());

			// Form the Date of Last Normal Values
			obx.getDateLastObservationNormalValue().getTimeOfAnEvent().setValue(oruFiller.getDateOfLastNormalValue());

			// set the User Defined Access Checks if necessary
			obx.getUserDefinedAccessChecks().setValue(oruFiller.getUserDefinedAccessChecks());

			// set Date/Time of the Observation
			obx.getDateTimeOfTheObservation().getTimeOfAnEvent().setValue(oruFiller.getDateTimeOfObservation());

			// set the Producer's ID
			obx.getProducerSID().getText().setValue(oruFiller.getProducerId());

			// form the Responsible Observer
			obx.getResponsibleObserver().getIDNumber().setValue(oruFiller.getResponsibleObserverId());
			obx.getResponsibleObserver().getGivenName().setValue(oruFiller.getResponsibleObserverGivenName());

			// Form the Observation Method
			obx.getObservationMethod(0).getText().setValue(oruFiller.getObservationMethod());

		}

		// Print the message (remember, the MSH segment was not fully or
		// correctly populated)
		Parser parser = new PipeParser();
		String encodedMessage = parser.encode(message);
		System.out.println(encodedMessage);

		/*
		 * Something close to this would be returned
		 * 
		 * MSH|^~\&|||||20111102082111.435-0500||ORU^R01^ORU_R01|305|T|2.5
		 * OBR|1||1234^LAB|88304 OBX|1|CE|88304|1|T57000^GALLBLADDER^SNM
		 * OBX|2|TX|88304&MDT|1|MICROSCOPIC EXAM SHOWS HISTOLOGICALLY NORMAL
		 * GALLBLADDER TISSUE
		 */
		return encodedMessage;

	}

}
