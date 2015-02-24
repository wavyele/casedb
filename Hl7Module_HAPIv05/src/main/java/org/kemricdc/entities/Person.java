package org.kemricdc.entities;


import java.util.Date;
import java.util.Set;

import org.kemricdc.hapi.PatientHl7Service;
/**
 * Model of a person object used in {@link PatientHl7Service#setPerson(Person)}
 * @author Stanslaus Odhiambo
 *
 */

public class Person {
	private String  firstName;
	private String middleName;
	private String lastName;
	private MaritalStatus maritalStatus;
	private Sex sex;
	private Set<PersonIdentifier> personIdentifiers;
	private Date dob;
	private Date deathDate;
	private Set<PersonAddress> personAddresses;
	private Location location;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public Set<PersonIdentifier> getPersonIdentifiers() {
		return personIdentifiers;
	}
	public void setPersonIdentifiers(Set<PersonIdentifier> personIdentifiers) {
		this.personIdentifiers = personIdentifiers;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Date getDeathDate() {
		return deathDate;
	}
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}
	public Set<PersonAddress> getPersonAddresses() {
		return personAddresses;
	}
	public void setPersonAddresses(Set<PersonAddress> personAddresses) {
		this.personAddresses = personAddresses;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	
	
	
	
	



}
