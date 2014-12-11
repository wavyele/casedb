/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc.hapi;

import java.util.Set;
import org.kemricdc.constants.MaritalStatusTypeName;
import org.kemricdc.entities.Address;
import org.kemricdc.entities.Location;
import org.kemricdc.entities.PatientSource;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class PersonFactory {

    private Person person = null;
    private Set<Address> addresses = null;
    private Set<PersonIdentifier> personIdentifiers = null;
    private Location location = null;
    private MaritalStatusTypeName maritalStatusType = null;
    private PatientSource patientSource = null;

    public PersonFactory(Person person, Set<Address> addresses,
            Location location, PatientSource patientSource) {
        setPerson(person);
        setAddresses(addresses);
        setPersonIdentifiers(personIdentifiers);
        setLocation(location);
        setMaritalStatusType(maritalStatusType);
        setPatientSource(patientSource);

    }

    public Person getPerson() {
        return person;
    }

    private void setPerson(Person person) {
        this.person = person;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    private void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<PersonIdentifier> getPersonIdentifiers() {
        return personIdentifiers;
    }

    private void setPersonIdentifiers(Set<PersonIdentifier> personIdentifiers) {
        this.personIdentifiers = personIdentifiers;
    }

    public Location getLocation() {
        return location;
    }

    private void setLocation(Location location) {
        this.location = location;
    }

    public MaritalStatusTypeName getMaritalStatusType() {
        return maritalStatusType;
    }

    private void setMaritalStatusType(MaritalStatusTypeName maritalStatusType) {
        this.maritalStatusType = maritalStatusType;
    }

    public PatientSource getPatientSource() {
        return patientSource;
    }

    private void setPatientSource(PatientSource patientSource) {
        this.patientSource = patientSource;
    }

    public Person buildPerson() {
        person.setPersonIdentifiers(personIdentifiers);
        person.setAddresses(addresses);
        person.setLocation(location);
//        person.setMaritalStatusType(maritalStatusType);
        person.setPatientSource(patientSource);

        return person;
    }

}
