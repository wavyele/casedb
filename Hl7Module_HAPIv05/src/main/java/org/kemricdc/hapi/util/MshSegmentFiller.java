package org.kemricdc.hapi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.kemricdc.entities.AppProperties;

import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.segment.MSH;

public class MshSegmentFiller {

	private MSH mshSegment;
	private AppProperties appProperties;
	private String messageType;
	private String triggerEvent;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getTriggerEvent() {
		return triggerEvent;
	}

	public void setTriggerEvent(String triggerEvent) {
		this.triggerEvent = triggerEvent;
	}

	public MSH getMshSegment() {
		return mshSegment;
	}

	public void setMshSegment(MSH msh) {
		this.mshSegment = msh;
	}

	public AppProperties getAppProperties() {
		return appProperties;
	}

	public void setAppProperties(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public MshSegmentFiller() {

	}

	public MshSegmentFiller(MSH mshSegment, AppProperties appProperties, String messageType, String triggerEvent) {
		this.mshSegment = mshSegment;
		this.appProperties = appProperties;
		this.messageType = messageType;
		this.triggerEvent = triggerEvent;
	}

	public MSH fillMshSegment() throws DataTypeException {
		mshSegment.getFieldSeparator().setValue("|");
		mshSegment.getEncodingCharacters().setValue("^~\\&");
		mshSegment.getSendingApplication().getNamespaceID().setValue((String) appProperties.getProperty("application_name"));
		mshSegment.getReceivingApplication().getNamespaceID().setValue((String) appProperties.getProperty("cdsapplication_name"));
		mshSegment.getProcessingID().getProcessingID().setValue((String) appProperties.getProperty("processing_id"));
		mshSegment.getMessageType().getMessageType().setValue(messageType);
		mshSegment.getMessageType().getTriggerEvent().setValue(triggerEvent);
		mshSegment.getMessageType().getMessageStructure().setValue(messageType + "_" + triggerEvent);
		mshSegment.getVersionID().getVersionID().setValue("2.4");
		mshSegment.getMessageControlID().setValue(sdf.format(Calendar.getInstance().getTime()));
		// mshSegment.getReceivingFacility().getNamespaceID().setValue("IHE");
		mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue(sdf.format(Calendar.getInstance().getTime()));
		mshSegment.getSendingApplication().getNamespaceID().setValue((String) appProperties.getProperty("application_name"));
		mshSegment.getSendingFacility().getNamespaceID().setValue((String) appProperties.getProperty("facility_name"));
		mshSegment.getSendingFacility().getUniversalID().setValue((String) appProperties.getProperty("facility_mfl_code"));
			
		mshSegment.getSequenceNumber().setValue((String) appProperties.getProperty("sequence_number"));

		return mshSegment;
	}
}
