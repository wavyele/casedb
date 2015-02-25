package org.kemricdc.entities;

/**
 * Enumeration of the different identifier types
 * @author Stanslaus Odhiambo
 *
 */
public enum IdentifierType {

	PID("PID_NUMBER"),CCC("CCC_NUMBER"),PMTCT("PMTCT_NUMBER"), ANC("ANC_NUMBER"), TB("TB_NUMBER"),HEI("HEI_NUMBER"),NATIONAL("NATIONAL_ID");

	private final String value;

	private IdentifierType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;

	}

}
