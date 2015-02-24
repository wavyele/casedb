package org.kemricdc.constants;

import ca.uhn.hl7v2.model.Message;

/**
 * Triggers enum
 * @author Stanslaus Odhiambo
 *
 *Enumerates the Constant Trigger Events for use in the determining the type of {@link Message} to generate for sending using this module
 */
public enum Triggers {

	A04("A04"),R01("R01"),A08("A08");

	private final String value;

	private Triggers(String value) {
		this.value = value;
	}

	/**
	 * Gets the associated value of a selected enum instance
	 * @return {@link String} value of the enum type
	 */
	public String getValue() {
		return value;

	}

}
