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
@Table(name = "marital_status_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MaritalStatusType.findAll", query = "SELECT m FROM MaritalStatusType m"),
    @NamedQuery(name = "MaritalStatusType.findByMaritalStatusTypeId", query = "SELECT m FROM MaritalStatusType m WHERE m.maritalStatusTypeId = :maritalStatusTypeId"),
    @NamedQuery(name = "MaritalStatusType.findByMaritalStatus", query = "SELECT m FROM MaritalStatusType m WHERE m.maritalStatus = :maritalStatus")})
public class MaritalStatusType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "marital_status_type_id")
    private Integer maritalStatusTypeId;
    @Column(name = "marital_status")
    private String maritalStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maritalStatus")
    private Collection<Person> personCollection;

    public MaritalStatusType() {
    }

    public MaritalStatusType(Integer maritalStatusTypeId) {
        this.maritalStatusTypeId = maritalStatusTypeId;
    }

    public Integer getMaritalStatusTypeId() {
        return maritalStatusTypeId;
    }

    public void setMaritalStatusTypeId(Integer maritalStatusTypeId) {
        this.maritalStatusTypeId = maritalStatusTypeId;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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
        hash += (maritalStatusTypeId != null ? maritalStatusTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaritalStatusType)) {
            return false;
        }
        MaritalStatusType other = (MaritalStatusType) object;
        if ((this.maritalStatusTypeId == null && other.maritalStatusTypeId != null) || (this.maritalStatusTypeId != null && !this.maritalStatusTypeId.equals(other.maritalStatusTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kemricdc.MaritalStatusType[ maritalStatusTypeId=" + maritalStatusTypeId + " ]";
    }
    
}
