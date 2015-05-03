package org.kemricdc.hapi.util;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.segment.PID;

public class PidSegmentFiller {
	private PID pid;
	private Person person;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);

	public PidSegmentFiller(PID pid, Person person) {
		this.pid = pid;
		this.person = person;
	}

	public PID fillPidSegment() throws DataTypeException, HL7Exception {
		pid.getPatientName(0).getFamilyName().getSurname().setValue(person.getLastName());
		pid.getPatientName(0).getGivenName().setValue(person.getFirstName());
		pid.getPatientName(0).getSecondAndFurtherGivenNamesOrInitialsThereof().setValue(person.getMiddleName());

		// pid.getPatientIdentifierList(0).getID().setValue(((PersonIdentifier)
		// person.getPersonIdentifiers().iterator().next()).getIdentifier());
		// Changed to the below to handle scenario where the patient has more
		// than one identifier
		Set<PersonIdentifier> identifiers = person.getPersonIdentifiers();
		int count = 0;
		if (identifiers != null) {
			for (PersonIdentifier personIdentifier : identifiers) {
				pid.getPatientIdentifierList(count).getID().setValue(personIdentifier.getIdentifier());
				pid.getPatientIdentifierList(count).getIdentifierTypeCode().setValue(personIdentifier.getIdentifierType().getValue());
				count++;
			}
		}
		sdf.format(person.getDob());

		pid.getAdministrativeSex().setValue(person.getSex().getValue());
		pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(sdf.format(person.getDob()));
		pid.getMaritalStatus().getIdentifier().setValue(person.getMaritalStatus().getValue());
		pid.getPhoneNumberHome(0).getPhoneNumber().setValue(person.getTelephoneNumber());
		pid.getMotherSIdentifier(0).getID().setValue(person.getMotherId());

		return pid;
	}
}
