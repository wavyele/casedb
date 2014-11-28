/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kemricdc.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Stanslaus Odhiambo
 */
@Entity
@Table(name = "personevents")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonEvents.findAll", query = "SELECT p FROM PersonEvents p"),
    @NamedQuery(name = "PersonEvents.findByPersoneventsId", query = "SELECT p FROM PersonEvents p WHERE p.personeventsId = :personeventsId"),
    @NamedQuery(name = "PersonEvents.findByEventdatetime", query = "SELECT p FROM PersonEvents p WHERE p.eventdatetime = :eventdatetime"),
    @NamedQuery(name = "PersonEvents.findByValueText", query = "SELECT p FROM PersonEvents p WHERE p.valueText = :valueText"),
    @NamedQuery(name = "PersonEvents.findByValueBoolean", query = "SELECT p FROM PersonEvents p WHERE p.valueBoolean = :valueBoolean"),
    @NamedQuery(name = "PersonEvents.findByValueDatetime", query = "SELECT p FROM PersonEvents p WHERE p.valueDatetime = :valueDatetime"),
    @NamedQuery(name = "PersonEvents.findByValueNumeric", query = "SELECT p FROM PersonEvents p WHERE p.valueNumeric = :valueNumeric"),
    @NamedQuery(name = "PersonEvents.findByValueCoded", query = "SELECT p FROM PersonEvents p WHERE p.valueCoded = :valueCoded"),
    @NamedQuery(name = "PersonEvents.findByValuesource", query = "SELECT p FROM PersonEvents p WHERE p.valuesource = :valuesource"),
    @NamedQuery(name = "PersonEvents.findByRemarks", query = "SELECT p FROM PersonEvents p WHERE p.remarks = :remarks")})
public class PersonEvents implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "personevents_id")
    private Integer personeventsId;
    @Column(name = "eventdatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventdatetime;
    @Column(name = "value_text")
    private String valueText;
    @Column(name = "value_boolean")
    private Boolean valueBoolean;
    @Column(name = "value_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDatetime;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value_numeric")
    private Double valueNumeric;
    @Column(name = "value_coded")
    private String valueCoded;
    @Column(name = "valuesource")
    private Integer valuesource;
    @Column(name = "remarks")
    private String remarks;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "facility_mflcode", referencedColumnName = "mflcode")
    @ManyToOne(optional = false)
    private Facility facilityMflcode;
    @JoinColumn(name = "event_id", referencedColumnName = "idevent")
    @ManyToOne(optional = false)
    private Events eventId;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne(optional = false)
    private Person personId;

    public PersonEvents() {
    }

    public PersonEvents(Integer personeventsId) {
        this.personeventsId = personeventsId;
    }

    public Integer getPersoneventsId() {
        return personeventsId;
    }

    public void setPersoneventsId(Integer personeventsId) {
        this.personeventsId = personeventsId;
    }

    public Date getEventdatetime() {
        return eventdatetime;
    }

    public void setEventdatetime(Date eventdatetime) {
        this.eventdatetime = eventdatetime;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public Boolean getValueBoolean() {
        return valueBoolean;
    }

    public void setValueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
    }

    public Date getValueDatetime() {
        return valueDatetime;
    }

    public void setValueDatetime(Date valueDatetime) {
        this.valueDatetime = valueDatetime;
    }

    public Double getValueNumeric() {
        return valueNumeric;
    }

    public void setValueNumeric(Double valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    public String getValueCoded() {
        return valueCoded;
    }

    public void setValueCoded(String valueCoded) {
        this.valueCoded = valueCoded;
    }

    public Integer getValuesource() {
        return valuesource;
    }

    public void setValuesource(Integer valuesource) {
        this.valuesource = valuesource;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Facility getFacilityMflcode() {
        return facilityMflcode;
    }

    public void setFacilityMflcode(Facility facilityMflcode) {
        this.facilityMflcode = facilityMflcode;
    }

    public Events getEventId() {
        return eventId;
    }

    public void setEventId(Events eventId) {
        this.eventId = eventId;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personeventsId != null ? personeventsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonEvents)) {
            return false;
        }
        PersonEvents other = (PersonEvents) object;
        if ((this.personeventsId == null && other.personeventsId != null) || (this.personeventsId != null && !this.personeventsId.equals(other.personeventsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kemricdc.PersonEvents[ personeventsId=" + personeventsId + " ]";
    }
    
}
