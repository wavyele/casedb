/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kemricdc.mappers;

import org.kemricdc.constants.Operation;
import org.kemricdc.constants.TablesOfInterest;
import org.kemricdc.entities.TransactionSet;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class TransactionMapper {
    Operation operation;
    TablesOfInterest toi;
    
    
    public void mapTransaction(TransactionSet set){
        operation=set.getOperation();
        
        switch(operation){
            case INSERT:{
                processInsert(set);
            }
            case UPDATE:{
                processUpdate(set);
            }
            case DELETE:{
                processDelete(set);
            }
        }
    }

    private void processInsert(TransactionSet set) {
        //this is a new record being inserted into the DB
        toi=null;
        toi=TablesOfInterest.valueOf(set.getTableName());
        switch(toi){
            case TBLPATIENT_INFORMATION:{
                
            }
        }
        
    }

    private void processUpdate(TransactionSet set) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void processDelete(TransactionSet set) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
