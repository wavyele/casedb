package org.kemricdc.hapi;

import org.springframework.stereotype.Component;

@Component
public interface HL7Service {
	public void doWork(String trigger);
}
