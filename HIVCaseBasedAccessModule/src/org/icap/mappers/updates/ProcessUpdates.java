/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.icap.mappers.updates;

import org.icap.constants.TablesOfInterest;
import org.icap.utils.TransactionSet;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class ProcessUpdates {
    private TablesOfInterest toi;
    private TransactionSet set;
    
    
    public ProcessUpdates(TransactionSet set){
        this.set=set;
        
    }
    
    
    public void processUpdate() {
        toi = TablesOfInterest.valueOf(set.getTableName().toUpperCase());
        switch (toi) {
            case TBLPATIENT_INFORMATION: {
                new ProcessPersonInformationUpdate(set).processTblPersonInformationUpdate();
//                processTblPersonInformationUpdate(set);
                break;
            }
            case TBLADDRESS: {
                new ProcessAddressUpdate(set).processTblAddressUpdate();
//                processTblAddressUpdate(set);
                break;
            }
            case TBLVISIT_INFORMATION: {
                new ProcessVisitInformationUpdate(set).processTblVisitInformationUpdate();
//                processTblVisitInformationUpdate(set);
                break;
            }
        }
    }
    
}
