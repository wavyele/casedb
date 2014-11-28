/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kemricdc.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Stanslaus Odhiambo
 */
@Entity
@Table(name = "events")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Events.findAll", query = "SELECT e FROM Events e"),
    @NamedQuery(name = "Events.findByIdevent", query = "SELECT e FROM Events e WHERE e.idevent = :idevent"),
    @NamedQuery(name = "Events.findByName", query = "SELECT e FROM Events e WHERE e.name = :name"),
    @NamedQuery(name = "Events.findByEventDatatype", query = "SELECT e FROM Events e WHERE e.eventDatatype = :eventDatatype"),
    @NamedQuery(name = "Events.findBySingletonEvent", query = "SELECT e FROM Events e WHERE e.singletonEvent = :singletonEvent"),
    @NamedQuery(name = "Events.findByMinValue", query = "SELECT e FROM Events e WHERE e.minValue = :minValue"),
    @NamedQuery(name = "Events.findByMaxValue", query = "SELECT e FROM Events e WHERE e.maxValue = :maxValue"),
    @NamedQuery(name = "Events.findByDescription", query = "SELECT e FROM Events e WHERE e.description = :description")})
public class Events implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idevent")
    private Integer idevent;
    @Column(name = "name")
    private String name;
    @Column(name = "event_datatype")
    private String eventDatatype;
    @Column(name = "singleton_event")
    private Boolean singletonEvent;
    @Column(name = "min_value")
    private String minValue;
    @Column(name = "max_value")
    private String maxValue;
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<PersonEvents> personEventsCollection;

    public Events() {
    }

    public Events(Integer idevent) {
        this.idevent = idevent;
    }

    public Integer getIdevent() {
        return idevent;
    }

    public void setIdevent(Integer idevent) {
        this.idevent = idevent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventDatatype() {
        return eventDatatype;
    }

    public void setEventDatatype(String eventDatatype) {
        this.eventDatatype = eventDatatype;
    }

    public Boolean getSingletonEvent() {
        return singletonEvent;
    }

    public void setSingletonEvent(Boolean singletonEvent) {
        this.singletonEvent = singletonEvent;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<PersonEvents> getPersonEventsCollection() {
        return personEventsCollection;
    }

    public void setPersonEventsCollection(Collection<PersonEvents> personEventsCollection) {
        this.personEventsCollection = personEventsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idevent != null ? idevent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Events)) {
            return false;
        }
        Events other = (Events) object;
        if ((this.idevent == null && other.idevent != null) || (this.idevent != null && !this.idevent.equals(other.idevent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kemricdc.Events[ idevent=" + idevent + " ]";
    }
    
}
