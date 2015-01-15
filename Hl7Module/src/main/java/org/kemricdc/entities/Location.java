package org.kemricdc.entities;

/**
 * Models the location of a Person
 * @author Stanslaus Odhiambo
 *
 */
public class Location {

	private String villageName;
	private String county;
	private String subCounty;
	private String district;
	private String location;
	private String subLocation;
	private String landmark;

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getSubCounty() {
		return subCounty;
	}

	public void setSubCounty(String subCounty) {
		this.subCounty = subCounty;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(String subLocation) {
		this.subLocation = subLocation;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

}
