package org.kemricdc;

import java.util.ArrayList;
import java.util.Date;

import org.kemricdc.entities.AppProperties;
import org.kemricdc.entities.MaritalStatus;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.Sex;
import org.kemricdc.hapi.EventsHl7Service;
import org.kemricdc.hapi.PatientHl7Service;
import org.kemricdc.hapi.util.OruFiller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TEST APPLICATION
 * @author Stanslaus Odhiambo
 * @version 2.0.1
 *
 *This is a test application for the {@ Hl7Module} application
 *
 *
 *LICENCE TYPE : NONE
 */
public class Hl7Module {

	/**
	 * Application Entry Point
	 * @param args - no arguments supplied at the moment as arguments to the entry
	 * point
	 */
	public static void main(String[] args) {
		System.out
				.println("**************************************************************\n"
						+ "HL7 Module\n"
						+ "**************************************************************");

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:beans.xml");

		//Sex and marital status should be provided to prevent some Exception that hasn't caught my attention to handle...;);)
		Person person=(Person) context.getBean("person");
		person.setMaritalStatus(MaritalStatus.SINGLE);
		person.setSex(Sex.MALE);
		person.setDob(new Date());
		
		AppProperties appProperties=(AppProperties) context.getBean("appProperties");
		System.out.println(appProperties.getProperty("sitename"));
		

		PatientHl7Service patientHl7Service=(PatientHl7Service) context.getBean("patientHl7Service");
		patientHl7Service.setPerson(person);
		patientHl7Service.setAppProperties(appProperties);
		patientHl7Service.doWork("A04");
		

		EventsHl7Service eventsHl7Service=(EventsHl7Service) context.getBean("eventsHl7Service");
		eventsHl7Service.setPerson(person);
		eventsHl7Service.setOruFiller(new ArrayList<OruFiller>(3));
		patientHl7Service.setAppProperties(appProperties);
		eventsHl7Service.doWork("R01");
		
		
		//((ClassPathXmlApplicationContext) context).close();
		
		
		

	}

}
