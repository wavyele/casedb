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
@Table(name = "identifier_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IdentifierType.findAll", query = "SELECT i FROM IdentifierType i"),
    @NamedQuery(name = "IdentifierType.findByIdentifierTypeId", query = "SELECT i FROM IdentifierType i WHERE i.identifierTypeId = :identifierTypeId"),
    @NamedQuery(name = "IdentifierType.findByIdentifierTypeName", query = "SELECT i FROM IdentifierType i WHERE i.identifierTypeName = :identifierTypeName")})
public class IdentifierType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "identifier_type_id")
    private Integer identifierTypeId;
    @Column(name = "identifier_type_name")
    private String identifierTypeName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "identifierTypeId")
    private Collection<PersonIdentifier> personIdentifierCollection;

    public IdentifierType() {
    }

    public IdentifierType(Integer identifierTypeId) {
        this.identifierTypeId = identifierTypeId;
    }

    public Integer getIdentifierTypeId() {
        return identifierTypeId;
    }

    public void setIdentifierTypeId(Integer identifierTypeId) {
        this.identifierTypeId = identifierTypeId;
    }

    public String getIdentifierTypeName() {
        return identifierTypeName;
    }

    public void setIdentifierTypeName(String identifierTypeName) {
        this.identifierTypeName = identifierTypeName;
    }

    @XmlTransient
    public Collection<PersonIdentifier> getPersonIdentifierCollection() {
        return personIdentifierCollection;
    }

    public void setPersonIdentifierCollection(Collection<PersonIdentifier> personIdentifierCollection) {
        this.personIdentifierCollection = personIdentifierCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identifierTypeId != null ? identifierTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IdentifierType)) {
            return false;
        }
        IdentifierType other = (IdentifierType) object;
        if ((this.identifierTypeId == null && other.identifierTypeId != null) || (this.identifierTypeId != null && !this.identifierTypeId.equals(other.identifierTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kemricdc.IdentifierType[ identifierTypeId=" + identifierTypeId + " ]";
    }
    
}
