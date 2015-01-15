package org.kemricdc.entities;

/**
 * Model of a Person address
 * @author Stanslaus Odhiambo
 *
 */
public class PersonAddress {
	private String address;
	private String postalCode;
	private String town;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

}
