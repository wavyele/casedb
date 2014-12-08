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
@Table(name = "location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l"),
    @NamedQuery(name = "Location.findByLocationId", query = "SELECT l FROM Location l WHERE l.locationId = :locationId"),
    @NamedQuery(name = "Location.findByVillageName", query = "SELECT l FROM Location l WHERE l.villageName = :villageName"),
    @NamedQuery(name = "Location.findByCounty", query = "SELECT l FROM Location l WHERE l.county = :county"),
    @NamedQuery(name = "Location.findBySubcounty", query = "SELECT l FROM Location l WHERE l.subcounty = :subcounty"),
    @NamedQuery(name = "Location.findByDistrict", query = "SELECT l FROM Location l WHERE l.district = :district"),
    @NamedQuery(name = "Location.findByLocation", query = "SELECT l FROM Location l WHERE l.location = :location"),
    @NamedQuery(name = "Location.findBySubLocation", query = "SELECT l FROM Location l WHERE l.subLocation = :subLocation"),
    @NamedQuery(name = "Location.findByLandmark", query = "SELECT l FROM Location l WHERE l.landmark = :landmark")})
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "location_id")
    private Integer locationId;
    @Column(name = "village_name")
    private String villageName;
    @Column(name = "county")
    private String county;
    @Column(name = "subcounty")
    private String subcounty;
    @Column(name = "district")
    private String district;
    @Column(name = "location")
    private String location;
    @Column(name = "sub_location")
    private String subLocation;
    @Column(name = "landmark")
    private String landmark;
    @OneToMany(mappedBy = "locationId")
    private Collection<Person> personCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId")
    private Collection<Facility> facilityCollection;

    public Location() {
    }

    public Location(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSubcounty() {
        return subcounty;
    }

    public void setSubcounty(String subcounty) {
        this.subcounty = subcounty;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubLocation() {
        return subLocation;
    }

    public void setSubLocation(String subLocation) {
        this.subLocation = subLocation;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    @XmlTransient
    public Collection<Person> getPersonCollection() {
        return personCollection;
    }

    public void setPersonCollection(Collection<Person> personCollection) {
        this.personCollection = personCollection;
    }

    @XmlTransient
    public Collection<Facility> getFacilityCollection() {
        return facilityCollection;
    }

    public void setFacilityCollection(Collection<Facility> facilityCollection) {
        this.facilityCollection = facilityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kemricdc.Location[ locationId=" + locationId + " ]";
    }
    
}
