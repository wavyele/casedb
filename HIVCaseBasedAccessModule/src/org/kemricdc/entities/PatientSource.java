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
@Table(name = "patient_source")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PatientSource.findAll", query = "SELECT p FROM PatientSource p"),
    @NamedQuery(name = "PatientSource.findByPatientSourceId", query = "SELECT p FROM PatientSource p WHERE p.patientSourceId = :patientSourceId"),
    @NamedQuery(name = "PatientSource.findByPatientSourceName", query = "SELECT p FROM PatientSource p WHERE p.patientSourceName = :patientSourceName")})
public class PatientSource implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "patient_source_id")
    private Integer patientSourceId;
    @Column(name = "patient_source_name")
    private String patientSourceName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientSourceId")
    private Collection<Person> personCollection;

    public PatientSource() {
    }

    public PatientSource(Integer patientSourceId) {
        this.patientSourceId = patientSourceId;
    }

    public Integer getPatientSourceId() {
        return patientSourceId;
    }

    public void setPatientSourceId(Integer patientSourceId) {
        this.patientSourceId = patientSourceId;
    }

    public String getPatientSourceName() {
        return patientSourceName;
    }

    public void setPatientSourceName(String patientSourceName) {
        this.patientSourceName = patientSourceName;
    }

    @XmlTransient
    public Collection<Person> getPersonCollection() {
        return personCollection;
    }

    public void setPersonCollection(Collection<Person> personCollection) {
        this.personCollection = personCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patientSourceId != null ? patientSourceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientSource)) {
            return false;
        }
        PatientSource other = (PatientSource) object;
        if ((this.patientSourceId == null && other.patientSourceId != null) || (this.patientSourceId != null && !this.patientSourceId.equals(other.patientSourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kemricdc.PatientSource[ patientSourceId=" + patientSourceId + " ]";
    }
    
}
