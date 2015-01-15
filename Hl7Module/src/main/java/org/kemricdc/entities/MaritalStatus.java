package org.kemricdc.entities;

/**
 * Enumeration of the different types of Marital Status to be used in {@link Person#setMaritalStatus(MaritalStatus)}
 * @author Stanslaus Odhiambo
 *
 */
public enum MaritalStatus {

	SINGLE("SINGLE"), MONOGAMOUS_MARRIED("MONOGAMOUS_MARRIED"), POLYGAMOUS_MARRIED(
			"POLYGAMOUS_MARRIED"), DIVORCED("DIVORCED"), SEPARATED("SEPARATED"), WIDOWED(
			"WIDOWED"), COHABITING("COHABITING"), MISSING("MISSING");

	private final String value;

	private MaritalStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;

	}

}
