/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kemricdc;

import org.kemricdc.hapi.adt.PatientRegistration;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class HapiModuleV1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PatientRegistration patientRegistration=new PatientRegistration();
        patientRegistration.processRegistration();
    }
    
}
