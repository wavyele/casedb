/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.icap.mappers.inserts;

import org.icap.utils.ParseDateAndName;
import accessquery.AccessPerson;
import accessquery.AccessQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.tool.hbm2x.StringUtils;
import org.icap.mappers.TransactionMapper;
import org.icap.utils.ParsePerson;
import org.icap.utils.Transaction;
import org.icap.utils.TransactionSet;
import org.kemricdc.constants.IdentifierType;
import org.kemricdc.constants.MaritalStatus;
import org.kemricdc.constants.PatientSource;
import org.kemricdc.constants.Sex;
import org.kemricdc.entities.Address;
import org.kemricdc.entities.Location;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;
import org.kemricdc.hapi.adt.PatientRegistrationAndUpdate;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class ProcessAddressInsert {

    private TransactionSet set;
    private MaritalStatus ms;
    private PatientSource ps;

    public ProcessAddressInsert(TransactionSet set) {
        this.set = set;

    }

    public void processTblAddressInsert() {

        Person addressPerson = new Person();

        Address address = new Address();
        Location l1 = new Location();

        List<Transaction> transactions = set.getTransactions();
        String variable;
        String dataValue;
        AccessPerson ap;
        for (Transaction transaction : transactions) {
            variable = transaction.getVariable();
            System.out.println("Processing....... " + variable);

            switch (variable) {
                case "patient_id": {
                    addressPerson=ParsePerson.parsePerson(transaction);
                    break;
                }
                case "postal_address": {
                    dataValue = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(dataValue)) {
                        address.setPostalAddress(dataValue);
                    }

                    break;
                }
                case "telephone": {
                    dataValue = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(dataValue)) {
                        address.setTelephone(dataValue);
                    }

                    break;
                }
                case "district": {
                    dataValue = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(dataValue)) {
                        l1.setDistrict(dataValue);
                    }
                    break;
                }
                case "location": {
                    dataValue = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(dataValue)) {
                        l1.setLocation(dataValue);
                    }
                    break;
                }
                case "sub_location": {
                    dataValue = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(dataValue)) {
                        l1.setSubLocation(dataValue);
                    }
                    break;
                }
                case "nearest_school": {
                    break;
                }
                case "nearest_health_center": {
                    dataValue = transaction.getDataValue();
                    if (StringUtils.isNotEmpty(dataValue)) {
                        l1.setLandmark(dataValue);
                    }
                    break;
                }

            }

        }
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);
        addressPerson.setAddresses(addresses);
        addressPerson.setLocation(l1);

        System.out.println(addressPerson);
        PatientRegistrationAndUpdate prau = new PatientRegistrationAndUpdate(addressPerson);
        prau.processRegistrationOrUpdate("A08");

    }

}
