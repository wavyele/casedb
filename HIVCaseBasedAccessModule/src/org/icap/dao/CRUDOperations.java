/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.icap.dao;

import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.kemricdc.constants.IdentifierType;
import org.kemricdc.constants.MaritalStatus;
import org.kemricdc.constants.PatientSource;
import org.kemricdc.entities.Address;
import org.kemricdc.entities.Events;
import org.kemricdc.entities.Facility;
import org.kemricdc.entities.Location;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;
import org.kemricdc.entities.Personevents;
//import org.icap.entities.Address;
//import org.icap.entities.Events;
//import org.icap.entities.Facility;
//import org.icap.entities.IdentifierType;
//import org.icap.entities.Location;
//import org.icap.entities.MaritalStatusType;
//import org.icap.entities.PatientSource;
//import org.icap.entities.Person;
//import org.icap.entities.PersonIdentifier;
//import org.icap.entities.Personevents;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class CRUDOperations {

    private static SessionFactory factory;
    private static CRUDOperations crudo;

    private CRUDOperations() {
        System.out.println("Creating the factory object\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        factory = new Configuration().configure().buildSessionFactory();

    }

    public static CRUDOperations getInstance() {
        if (crudo != null) {
            return crudo;
        } else {
            crudo = new CRUDOperations();
            return crudo;
        }

    }

    public Integer addPerson(Person p) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer personId = null;
        try {
            tx = session.beginTransaction();
            personId = (Integer) session.save(p);
//            System.out.printf("Person Details for %s Added Succesfully.", p.getFirstName());
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return personId;

    }

    public Integer addPersonIdentifier(PersonIdentifier identifier) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer identifierId = null;
        System.out.println("About To Add Person Identifier.");
        try {

            tx = session.beginTransaction();
            identifierId = (Integer) session.save(identifier);
            tx.commit();
            System.out.println("Person Identifier Added Succesfully.");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return identifierId;

    }

    public Person getPerson(int personId) {
        Session session = factory.openSession();
        Transaction tx = null;
        Person person = null;
        try {
            tx = session.beginTransaction();
            person = (Person) session.get(Person.class, personId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return person;
    }

    //to be modified to update all the fields at one go
    public Person updatePerson(Person updatedPerson) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(updatedPerson);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return updatedPerson;
    }

    public IdentifierType getIdentifierType(int identifierTypeId) {
        Session session = factory.openSession();
        Transaction tx = null;
        IdentifierType identifierType = null;
        try {
            tx = session.beginTransaction();
            identifierType = (IdentifierType) session.get(IdentifierType.class, identifierTypeId);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return identifierType;
    }

    public MaritalStatus getMaritalStatusType(int maritalStatusTypeId) {
        Session session = factory.openSession();
        Transaction tx = null;
        MaritalStatus maritalStatusType = null;
        try {
            System.out.println("About to mine marital status type.....\n");
            tx = session.beginTransaction();
            maritalStatusType = (MaritalStatus) session.get(MaritalStatus.class, maritalStatusTypeId);
            System.out.println("\nAbout to commmit.......\n");

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return maritalStatusType;
    }

    public PatientSource getPatientSource(int patientSourceId) {
        Session session = factory.openSession();
        Transaction tx = null;
        PatientSource patientSource = null;
        try {
            tx = session.beginTransaction();
            patientSource = (PatientSource) session.get(PatientSource.class, patientSourceId);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return patientSource;
    }

//   Address Operations
    public Integer addAddress(Address address) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer addresssId = null;
        try {
            tx = session.beginTransaction();
            addresssId = (Integer) session.save(address);
            System.out.printf("Address Details Added Succesfully.");
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return addresssId;

    }
//
//    public Person getPersonIdByIdentifier(String identifier) {
//        Session session = factory.openSession();
//        Person person = null;
//        Transaction tx = null;
//        PersonIdentifier personIdentifier;
//        String hql = "FROM PersonIdentifier PI WHERE PI.identifier = :identifier";
//        try {
//            tx = session.beginTransaction();
//            Query query = session.createQuery(hql);
//            query.setParameter("identifier", identifier);
//            personIdentifier = (PersonIdentifier) query.uniqueResult();
//
////            System.out.printf("\n\n\nPersonIdentifier  Details for %s Retrieved Succesfully.\n\n", personIdentifier);
//            person = personIdentifier.getPerson();
////            System.out.println(person);
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//
//        return person;
//    }
//    
    

    public Integer addPersonEvent(Personevents personevents) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer eventId = null;
        System.out.println("About to insert an event.");
        try {

            tx = session.beginTransaction();
            eventId = (Integer) session.save(personevents);
            tx.commit();
            System.out.println("Person Event Added Succesfully.");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return eventId;

    }

    public Facility getFacility(int facilityId) {
        Session session = factory.openSession();
        Transaction tx = null;
        Facility facility = null;
        try {
            tx = session.beginTransaction();
            facility = (Facility) session.get(Facility.class, facilityId);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return facility;
    }

    public Location getloLocation(int locationId) {
        Session session = factory.openSession();
        Transaction tx = null;
        Location location = null;
        try {
            tx = session.beginTransaction();
            location = (Location) session.get(Location.class, locationId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return location;
    }

    public Events getEvents(int eventId) {
        Session session = factory.openSession();
        Transaction tx = null;
        Events events = null;
        try {
            tx = session.beginTransaction();
            events = (Events) session.get(Events.class, eventId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return events;
    }

}
