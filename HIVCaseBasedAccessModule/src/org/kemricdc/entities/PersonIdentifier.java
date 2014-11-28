/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kemricdc.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Stanslaus Odhiambo
 */
@Entity
@Table(name = "person_identifier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonIdentifier.findAll", query = "SELECT p FROM PersonIdentifier p"),
    @NamedQuery(name = "PersonIdentifier.findByPersonIdentifierId", query = "SELECT p FROM PersonIdentifier p WHERE p.personIdentifierId = :personIdentifierId"),
    @NamedQuery(name = "PersonIdentifier.findByIdentifier", query = "SELECT p FROM PersonIdentifier p WHERE p.identifier = :identifier")})
public class PersonIdentifier implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "person_identifier_id")
    private Integer personIdentifierId;
    @Column(name = "identifier")
    private String identifier;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne(optional = false)
    private Person personId;
    @JoinColumn(name = "identifier_type_id", referencedColumnName = "identifier_type_id")
    @ManyToOne(optional = false)
    private IdentifierType identifierTypeId;

    public PersonIdentifier() {
    }

    public PersonIdentifier(Integer personIdentifierId) {
        this.personIdentifierId = personIdentifierId;
    }

    public Integer getPersonIdentifierId() {
        return personIdentifierId;
    }

    public void setPersonIdentifierId(Integer personIdentifierId) {
        this.personIdentifierId = personIdentifierId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public IdentifierType getIdentifierTypeId() {
        return identifierTypeId;
    }

    public void setIdentifierTypeId(IdentifierType identifierTypeId) {
        this.identifierTypeId = identifierTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personIdentifierId != null ? personIdentifierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonIdentifier)) {
            return false;
        }
        PersonIdentifier other = (PersonIdentifier) object;
        if ((this.personIdentifierId == null && other.personIdentifierId != null) || (this.personIdentifierId != null && !this.personIdentifierId.equals(other.personIdentifierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kemricdc.PersonIdentifier[ personIdentifierId=" + personIdentifierId + " ]";
    }
    
}
