/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package changetracker.wrappers;

import changetracker.util.ChangeTracker;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kemricdc.constants.IdentifierType;
import org.kemricdc.constants.MaritalStatus;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;

/**
 *
 * @author Vicky
 */
public class PersonFactory {

    public synchronized static Person getPerson(int id) throws SQLException {
        
        Person person = new Person();
        
        List rs = ChangeTracker.getPatient(id);

        if (!rs.isEmpty()) {
            for (Object o : rs) {
                HashMap rc = (HashMap) o;

                person.setFirstName((String) rc.get("firstname_decrypted"));
                person.setMiddleName((String) rc.get("middlename_decrypted"));
                person.setLastName((String) rc.get("lastname_decrypted"));
                person.setBirthdate((Timestamp) rc.get("DOB"));
                person.setDateCreated((Timestamp) rc.get("CreateDate"));
                
                
                switch((int)rc.get("Sex")){
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
                    
                    
                    
                    switch((int)rc.get("MaritalStatus")){
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
                pi.setIdentifier((String) rc.get("IQNumber"));
                pi.setIdentifierType(IdentifierType.CCC_NUMBER);
                identifiers.add(pi);

                if ((String) rc.get("ANCNumber") != null && !((String) rc.get("ANCNumber")).equals("")) {
                    pi = new PersonIdentifier();
                    pi.setIdentifier((String) rc.get("ANCNumber"));
                    pi.setIdentifierType(IdentifierType.ANC_NUMBER);
                    identifiers.add(pi);
                }

                if ((String) rc.get("PMTCTNumber") != null && !((String) rc.get("PMTCTNumber")).equals("")) {
                    pi = new PersonIdentifier();
                    pi.setIdentifier((String) rc.get("PMTCTNumber"));
                    pi.setIdentifierType(IdentifierType.PMTCT_NUMBER);
                    identifiers.add(pi);
                }

                if ((String) rc.get("[ID/PassportNo]") != null && !((String) rc.get("[ID/PassportNo]")).equals("")) {
                    pi = new PersonIdentifier();
                    pi.setIdentifier((String) rc.get("[ID/PassportNo]"));
                    pi.setIdentifierType(IdentifierType.NATIONAL_ID);
                    identifiers.add(pi);
                }

                //Add identifiers to the set
                person.setPersonIdentifiers(identifiers);

            }
        } else {
            person = null;
        }

        return person;
    }

}
