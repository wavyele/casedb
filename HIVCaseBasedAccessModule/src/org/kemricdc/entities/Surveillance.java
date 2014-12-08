package org.kemricdc.entities;
// Generated Nov 28, 2014 11:52:32 AM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Surveillance generated by hbm2java
 */
public class Surveillance  implements java.io.Serializable {


     private Integer surveillanceId;
     private String name;
     private String description;
     private Boolean active;
     private Date startDate;
     private Date endDate;
     private Set eventses = new HashSet(0);

    public Surveillance() {
    }

    public Surveillance(String name, String description, Boolean active, Date startDate, Date endDate, Set eventses) {
       this.name = name;
       this.description = description;
       this.active = active;
       this.startDate = startDate;
       this.endDate = endDate;
       this.eventses = eventses;
    }
   
    public Integer getSurveillanceId() {
        return this.surveillanceId;
    }
    
    public void setSurveillanceId(Integer surveillanceId) {
        this.surveillanceId = surveillanceId;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getActive() {
        return this.active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Set getEventses() {
        return this.eventses;
    }
    
    public void setEventses(Set eventses) {
        this.eventses = eventses;
    }




}

