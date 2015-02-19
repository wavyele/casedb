/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changetracker.wrappers;

import ca.uhn.hl7v2.HL7Exception;
import changetracker.util.ChangeTracker;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kemricdc.constants.IdentifierType;
import org.kemricdc.constants.MaritalStatus;
import org.kemricdc.constants.Sex;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;
import org.kemricdc.hapi.adt.PatientRegistrationAndUpdate;
/**
 *
 * @author Vicky
 */
public class PatientEnrollmentTracker implements Runnable{
    
    @Override
    public void run() {
        try {
            trackPatientEnrollment();
        } catch (Exception ex) {
            Logger.getLogger(PatientEnrollmentTracker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void trackPatientEnrollment() throws HL7Exception, Exception
    {
        try{
            List results = ChangeTracker.getChangesFromTable(ChangeTracker.getLastVersion("Mst_Patient"), "Mst_Patient", "Ptn_Pk");
            
            if(!results.isEmpty())
            {
            for(Object object:results)
            {
               HashMap record =  (HashMap)object;
               String change_operation = (String)record.get("SYS_CHANGE_OPERATION");
               int primary_key = (int)record.get("Ptn_Pk");
               
                Timestamp update_date =   (Timestamp) record.get("UpdateDate");
                Timestamp ART_start_date =   (Timestamp) record.get("ARTStartDate");
                
                Person person = new Person();
                person.setFirstName((String) record.get("firstname_decrypted"));
                person.setMiddleName((String) record.get("middlename_decrypted"));
                person.setLastName((String) record.get("lastname_decrypted"));
                person.setBirthdate((Timestamp) record.get("DOB"));
                person.setDateCreated((Timestamp) record.get("CreateDate"));
                
                switch((int)record.get("Sex")){
                   case 16:
                       person.setSex("MALE");
                       break;
                   case 17:
                       person.setSex("FEMALE");
                       break;
                   default:
                       person.setSex("UNKOWN");
                       break;
                }
                    
                    
                    
                    switch((int)record.get("MaritalStatus")){
                        case 42:
                            //marital_status is single, so the set only contains the person himself
                            person.setMaritalStatusType(MaritalStatus.SINGLE);
                            break;
                        case 43:
                        case 291:
                            person.setMaritalStatusType(MaritalStatus.MONOGAMOUS_MARRIED);
                            break;
                        case 44:
                            person.setMaritalStatusType(MaritalStatus.DIVORCED);
                            break;
                        case 189:
                            person.setMaritalStatusType(MaritalStatus.WIDOWED);
                            break;
                        case 45:
                            person.setMaritalStatusType(MaritalStatus.MISSING);
                            break;
                        
                        case 290:
                            person.setMaritalStatusType(MaritalStatus.POLYGAMOUS_MARRIED);
                            break;
                        case 292:
                            person.setMaritalStatusType(MaritalStatus.COHABITING);
                            break;
                        default:
                            person.setMaritalStatusType(MaritalStatus.MISSING);
                            break;
                    }
                    
                    Set<PersonIdentifier> identifiers = new HashSet<>();
                    PersonIdentifier pi = new PersonIdentifier();
                    pi.setIdentifier((String) record.get("IQNumber"));
                    pi.setIdentifierType(IdentifierType.CCC_NUMBER);
                    identifiers.add(pi);
                    
                    if((String) record.get("ANCNumber")!=null && !((String) record.get("ANCNumber")).equals(""))
                    {
                        pi = new PersonIdentifier();
                        pi.setIdentifier((String) record.get("ANCNumber"));
                        pi.setIdentifierType(IdentifierType.ANC_NUMBER);
                        identifiers.add(pi);
                    }
                    
                    if((String) record.get("PMTCTNumber")!=null && !((String) record.get("PMTCTNumber")).equals(""))
                    {
                        pi = new PersonIdentifier();
                        pi.setIdentifier((String) record.get("PMTCTNumber"));
                        pi.setIdentifierType(IdentifierType.PMTCT_NUMBER);
                        identifiers.add(pi);
                    }
                    
                    if((String) record.get("[ID/PassportNo]")!=null && !((String) record.get("[ID/PassportNo]")).equals(""))
                    {
                        pi = new PersonIdentifier();
                        pi.setIdentifier((String) record.get("[ID/PassportNo]"));
                        pi.setIdentifierType(IdentifierType.NATIONAL_ID);
                        identifiers.add(pi);
                    }
                    
                    //Add identifiers to the set
                    person.setPersonIdentifiers(identifiers);

                    //Leave this error for the time..reminds me of what is to be done.
                    //No use of null values
                if(change_operation.equalsIgnoreCase("I"))
                {
                    PatientRegistrationAndUpdate patientRegistration = new PatientRegistrationAndUpdate(person,null,null,null);
                    patientRegistration.processRegistrationOrUpdate("A04");
                }
                else if(change_operation.equalsIgnoreCase("U"))
                {
                    
                    PatientRegistrationAndUpdate patientRegistration = new PatientRegistrationAndUpdate(person,null,null,null);
                    patientRegistration.processRegistrationOrUpdate("A08");
                }
               
            }
            }
            else
            {System.out.println("No changes To Report!");}
        }catch(SQLException ex){}
        
        }

    
        
    }