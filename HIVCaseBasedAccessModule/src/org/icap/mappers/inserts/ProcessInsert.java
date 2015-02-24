/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.icap.mappers.inserts;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.icap.constants.TablesOfInterest;
import org.icap.mappers.TransactionMapper;
import org.icap.utils.TransactionSet;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class ProcessInsert {

    private final TransactionSet set;
    private TablesOfInterest toi;

    public ProcessInsert(TransactionSet set) {
        this.set = set;

    }

    public void processInsert() {
        //this is a new record being inserted into the DB
        try {
            toi = TablesOfInterest.valueOf(set.getTableName().toUpperCase());//to review
            switch (toi) {
                case TBLPATIENT_INFORMATION: {
                    new ProcessPersonInformationInsert(set).processTblPersonInformationInsert();
                    break;

                }
                case TBLADDRESS: {
                    new ProcessAddressInsert(set).processTblAddressInsert();
                    break;
                }

                case TBLVISIT_INFORMATION: {
                    new ProcessVisitInformationInsert(set).processTblVisitInformationInsert();
                    break;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(TransactionMapper.class.getName()).log(Level.SEVERE, null, e.getMessage());
            Logger.getLogger(TransactionMapper.class.getName()).log(Level.SEVERE, null, "Cannot process records from the table++++++++++++++++++++++++++++++++++++++");
        }

    }

}
