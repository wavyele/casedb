package org.kemricdc.entities;
// Generated Nov 28, 2014 11:52:32 AM by Hibernate Tools 3.6.0



/**
 * EventValues generated by hbm2java
 */
public class EventValues  implements java.io.Serializable {


     private Integer eventValuesId;
     private Events events;
     private String value;
     private String eventId;

    public EventValues() {
    }

	
    public EventValues(Events events) {
        this.events = events;
    }
    public EventValues(Events events, String value, String eventId) {
       this.events = events;
       this.value = value;
       this.eventId = eventId;
    }
   
    public Integer getEventValuesId() {
        return this.eventValuesId;
    }
    
    public void setEventValuesId(Integer eventValuesId) {
        this.eventValuesId = eventValuesId;
    }
    public Events getEvents() {
        return this.events;
    }
    
    public void setEvents(Events events) {
        this.events = events;
    }
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    public String getEventId() {
        return this.eventId;
    }
    
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }




}


