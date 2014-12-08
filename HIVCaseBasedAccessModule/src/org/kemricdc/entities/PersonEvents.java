package org.kemricdc.entities;
// Generated Nov 28, 2014 11:52:32 AM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Personevents generated by hbm2java
 */
public class Personevents  implements java.io.Serializable {


     private Integer personeventsId;
     private Facility facility;
     private User user;
     private Events events;
     private Person person;
     private Date eventdatetime;
     private String valueText;
     private Boolean valueBoolean;
     private Date valueDatetime;
     private Double valueNumeric;
     private String valueCoded;
     private Integer valuesource;
     private String remarks;
     private Set birthMetadatas = new HashSet(0);

    public Personevents() {
    }

	
    public Personevents(Facility facility, Events events, Person person) {
        this.facility = facility;
        this.events = events;
        this.person = person;
    }
    public Personevents(Facility facility, User user, Events events, Person person, Date eventdatetime, String valueText, Boolean valueBoolean, Date valueDatetime, Double valueNumeric, String valueCoded, Integer valuesource, String remarks, Set birthMetadatas) {
       this.facility = facility;
       this.user = user;
       this.events = events;
       this.person = person;
       this.eventdatetime = eventdatetime;
       this.valueText = valueText;
       this.valueBoolean = valueBoolean;
       this.valueDatetime = valueDatetime;
       this.valueNumeric = valueNumeric;
       this.valueCoded = valueCoded;
       this.valuesource = valuesource;
       this.remarks = remarks;
       this.birthMetadatas = birthMetadatas;
    }
   
    public Integer getPersoneventsId() {
        return this.personeventsId;
    }
    
    public void setPersoneventsId(Integer personeventsId) {
        this.personeventsId = personeventsId;
    }
    public Facility getFacility() {
        return this.facility;
    }
    
    public void setFacility(Facility facility) {
        this.facility = facility;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public Events getEvents() {
        return this.events;
    }
    
    public void setEvents(Events events) {
        this.events = events;
    }
    public Person getPerson() {
        return this.person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }
    public Date getEventdatetime() {
        return this.eventdatetime;
    }
    
    public void setEventdatetime(Date eventdatetime) {
        this.eventdatetime = eventdatetime;
    }
    public String getValueText() {
        return this.valueText;
    }
    
    public void setValueText(String valueText) {
        this.valueText = valueText;
    }
    public Boolean getValueBoolean() {
        return this.valueBoolean;
    }
    
    public void setValueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
    }
    public Date getValueDatetime() {
        return this.valueDatetime;
    }
    
    public void setValueDatetime(Date valueDatetime) {
        this.valueDatetime = valueDatetime;
    }
    public Double getValueNumeric() {
        return this.valueNumeric;
    }
    
    public void setValueNumeric(Double valueNumeric) {
        this.valueNumeric = valueNumeric;
    }
    public String getValueCoded() {
        return this.valueCoded;
    }
    
    public void setValueCoded(String valueCoded) {
        this.valueCoded = valueCoded;
    }
    public Integer getValuesource() {
        return this.valuesource;
    }
    
    public void setValuesource(Integer valuesource) {
        this.valuesource = valuesource;
    }
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Set getBirthMetadatas() {
        return this.birthMetadatas;
    }
    
    public void setBirthMetadatas(Set birthMetadatas) {
        this.birthMetadatas = birthMetadatas;
    }




}


