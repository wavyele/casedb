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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "facility")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facility.findAll", query = "SELECT f FROM Facility f"),
    @NamedQuery(name = "Facility.findByMflcode", query = "SELECT f FROM Facility f WHERE f.mflcode = :mflcode"),
    @NamedQuery(name = "Facility.findByFacilityname", query = "SELECT f FROM Facility f WHERE f.facilityname = :facilityname")})
public class Facility implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "mflcode")
    private Integer mflcode;
    @Column(name = "facilityname")
    private String facilityname;
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @ManyToOne(optional = false)
    private Location locationId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facilityMflcode")
    private Collection<PersonEvents> personEventsCollection;

    public Facility() {
    }

    public Facility(Integer mflcode) {
        this.mflcode = mflcode;
    }

    public Integer getMflcode() {
        return mflcode;
    }

    public void setMflcode(Integer mflcode) {
        this.mflcode = mflcode;
    }

    public String getFacilityname() {
        return facilityname;
    }

    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
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
        hash += (mflcode != null ? mflcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facility)) {
            return false;
        }
        Facility other = (Facility) object;
        if ((this.mflcode == null && other.mflcode != null) || (this.mflcode != null && !this.mflcode.equals(other.mflcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kemricdc.Facility[ mflcode=" + mflcode + " ]";
    }
    
}
