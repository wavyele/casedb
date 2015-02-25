package org.kemricdc.entities;

/**
 * Model of a Person's gender to ne used in {@link Person#setSex(Sex)}
 * @author Stanslaus Odhiambo
 *
 */
public enum Sex {

	MALE("MALE"), FEMALE("FEMALE"), UNKNOWN("UNKBOWN"),MISSING("MISSING");

	private final String value;

	private Sex(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;

	}

}
