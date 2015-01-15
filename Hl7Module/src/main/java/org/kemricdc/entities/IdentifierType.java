package org.kemricdc.entities;

/**
 * Enumeration of the different identifier types
 * @author Stanslaus Odhiambo
 *
 */
public enum IdentifierType {

	PMTCT("pmtct"), ANC("anc"), TB("tb");

	private final String value;

	private IdentifierType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;

	}

}
