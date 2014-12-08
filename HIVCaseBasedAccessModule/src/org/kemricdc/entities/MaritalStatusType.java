package org.kemricdc.entities;
// Generated Nov 28, 2014 11:52:32 AM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * MaritalStatusType generated by hbm2java
 */
public class MaritalStatusType  implements java.io.Serializable {


     private Integer maritalStatusTypeId;
     private String maritalStatus;
     private Set persons = new HashSet(0);

    public MaritalStatusType() {
    }

    public MaritalStatusType(String maritalStatus, Set persons) {
       this.maritalStatus = maritalStatus;
       this.persons = persons;
    }
   
    public Integer getMaritalStatusTypeId() {
        return this.maritalStatusTypeId;
    }
    
    public void setMaritalStatusTypeId(Integer maritalStatusTypeId) {
        this.maritalStatusTypeId = maritalStatusTypeId;
    }
    public String getMaritalStatus() {
        return this.maritalStatus;
    }
    
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    public Set getPersons() {
        return this.persons;
    }
    
    public void setPersons(Set persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "MaritalStatusType{" + "maritalStatusTypeId=" + maritalStatusTypeId + ", maritalStatus=" + maritalStatus + '}';
    }




}


