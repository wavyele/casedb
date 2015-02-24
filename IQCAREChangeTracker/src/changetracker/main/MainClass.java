/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changetracker.main;

import changetracker.util.PreliminarySettings;
import changetracker.wrappers.DeathAndTO;
import changetracker.wrappers.HIVCareInitiation;
import changetracker.wrappers.InitialEvaluation;
import changetracker.wrappers.PatientEnrollmentTracker;
import changetracker.wrappers.PrevCareEnrollment;
import changetracker.wrappers.WHOStaging;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vicky
 */
public class MainClass {

    
    
     public static void main(String[] args) {
        
        Executor executor = Executors.newCachedThreadPool();
        while(true)
        {
            System.out.println("Going For The Changes.................................");
            executor.execute(new PatientEnrollmentTracker());
            executor.execute(new PrevCareEnrollment());
            executor.execute(new WHOStaging());
            
            executor.execute(new HIVCareInitiation());
            executor.execute(new InitialEvaluation());
            
            executor.execute(new DeathAndTO());
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public MainClass() {
        PreliminarySettings.execute();
    }
    
    
     
    
}
