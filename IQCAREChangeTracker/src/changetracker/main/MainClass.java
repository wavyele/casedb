/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changetracker.main;

import changetracker.wrappers.PatientEnrollmentTracker;
import java.util.Timer;

/**
 *
 * @author Vicky
 */
public class MainClass {

    
    
     public static void main(String[] args) {
        
         Timer timer = new Timer();
         timer.schedule(new PatientEnrollmentTracker(), 0, 4000);
        
    }
    
}
