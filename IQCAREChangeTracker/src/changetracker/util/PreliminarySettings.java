/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changetracker.util;

import changetracker.main.MainClass;
import static changetracker.util.ChangeTracker.enableDBChangeTracking;
import static changetracker.util.ChangeTracker.enableTableChangeTracking;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vicky
 */
public class PreliminarySettings {
    
    public static void execute()
    {
        if( PropertiesManager.readConfigFile("initial_setup","connection.properties").equals(String.valueOf("0")))
        {
            preliminarySettings();
        }
    }
    public final static void preliminarySettings(){
        
        try {
            //dtl_patientLabResults doesnt have a primary key field, so we set it first! This is just a quick hack
             ChangeTracker.setPK("dtl_patientLabResults", "LabID");
            //Enable change tracking on db if it hasn't been done already
            enableDBChangeTracking();
            
            //Enable change tracking on tables if it hasn't been done already
            enableTableChangeTracking("dbo.LNK_PatientPRogramStart");
            enableTableChangeTracking("dbo.dtl_patientLabResults");
            enableTableChangeTracking("dbo.dtl_PatientARVEligibility");
            enableTableChangeTracking("dbo.dtl_PatientHivPrevCareEnrollment");
            enableTableChangeTracking("dbo.dtl_PriorArvAndHivCare");
            enableTableChangeTracking("dbo.ord_PatientPharmacyOrder");
            enableTableChangeTracking("dbo.dtl_PatientHivPrevCareIE");
            enableTableChangeTracking("dbo.dtl_PatientCareEnded");
            
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
