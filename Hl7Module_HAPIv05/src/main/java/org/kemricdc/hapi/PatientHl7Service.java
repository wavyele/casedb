package org.kemricdc.hapi;

import java.io.IOException;

import org.kemricdc.entities.AppProperties;
import org.kemricdc.entities.Person;
import org.kemricdc.hapi.util.MshSegmentFiller;
import org.kemricdc.hapi.util.PidSegmentFiller;
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
			new SendHl7Message().sendMessage(hl7Message, Integer.valueOf((String) appProperties.getProperty("port")),
					(String) appProperties.getProperty("host"), (String) appProperties.getProperty("hapi_dump"));
		} catch (Exception e) {
			if (e instanceof HL7Exception || e instanceof IOException) {
				e.printStackTrace();
			} else if (e instanceof NullPointerException) {
				e.printStackTrace();
			}
		}
	}

	private String generateHL7String(String triggerEvent) throws HL7Exception, IOException {

		ADT_A01 adt_a01 = new ADT_A01();
		// adt_a01.initQuickstart("ADT", triggerEvent, "T");

		// Populate the MSH Segment
		MSH mshSegment = adt_a01.getMSH();
		MshSegmentFiller mshSegmentFiller = new MshSegmentFiller(mshSegment, appProperties, "ADT", triggerEvent);
		mshSegment = mshSegmentFiller.fillMshSegment();

		// Populate the PID Segment
		PID pid = adt_a01.getPID();
		PidSegmentFiller pidSegmentFiller = new PidSegmentFiller(pid, person);
		pid = pidSegmentFiller.fillPidSegment();

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
