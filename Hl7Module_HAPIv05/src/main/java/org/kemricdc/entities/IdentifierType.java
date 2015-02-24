package org.kemricdc.entities;

/**
 * Enumeration of the different identifier types
 * @author Stanslaus Odhiambo
 *
 */
public enum IdentifierType {

	PID("PID_NUMBER"),CCC("CCC"),PMTCT("PMTCT"), ANC("ANC"), TB("TB");

	private final String value;

	private IdentifierType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;

	}

}
