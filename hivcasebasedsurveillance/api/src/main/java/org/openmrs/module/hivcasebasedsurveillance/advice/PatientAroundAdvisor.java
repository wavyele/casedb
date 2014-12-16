package org.openmrs.module.hivcasebasedsurveillance.advice;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.openmrs.Patient;
import org.openmrs.module.hivcasebasedsurveillance.CdsPatient;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

public class PatientAroundAdvisor extends StaticMethodMatcherPointcutAdvisor implements
		Advisor {

	private static final long serialVersionUID = -6843825997193392439L;
	
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		if (method.getName().equals("savePatient")) {
			return true;
		}
		return false;
	}

	@Override
	public Advice getAdvice() {
		return new PatientAroundAdvice();
	}

	private class PatientAroundAdvice implements MethodInterceptor {
		public Object invoke(MethodInvocation invocation) throws Throwable {

			Object args[] = invocation.getArguments();
			Patient patient = (Patient)args[0];
			
			CdsPatient cdsPatient = new CdsPatient();
			cdsPatient.setFirstName(patient.getGivenName());
			cdsPatient.setMiddleName(patient.getMiddleName());
			cdsPatient.setLastName(patient.getFamilyName());
			cdsPatient.setBirthDate(new DateTime(patient.getBirthdate()));

			if (patient.getPatientId() == null) {
				System.out.println("New Patient");
				//This is a new patient
			} else {
				System.out.println("Existing Patient");
				//This is an existing Patient
			}
			
			JAXBContext context = JAXBContext.newInstance(CdsPatient.class); 
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			CdsPatient object = cdsPatient;
			m.marshal(object, new FileOutputStream("D:\\Patient.xml"));
			
			Object o = invocation.proceed();

			return o;
		}
	}
}
