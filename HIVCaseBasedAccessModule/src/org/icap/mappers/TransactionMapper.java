/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.icap.mappers;

import org.icap.mappers.inserts.ProcessInsert;
import org.icap.constants.Operation;
import org.icap.mappers.deletions.ProcessDelete;
import org.icap.mappers.updates.ProcessUpdates;
import org.icap.utils.TransactionSet;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class TransactionMapper {

    private Operation operation;
    

    public void mapTransaction(TransactionSet set) {
        operation = set.getOperation();

        switch (operation) {
            case INSERT: {
                processInsert(set);
                break;
            }
            case UPDATE: {
                processUpdate(set);
                break;
            }
            case DELETE: {
                processDelete(set);
                break;
            }
        }
    }

    private void processInsert(TransactionSet set) {
        new ProcessInsert(set).processInsert();

    }

    private void processUpdate(TransactionSet set) {
        new ProcessUpdates(set).processUpdate();
    }

    private void processDelete(TransactionSet set) {
        new ProcessDelete(set).processDeletion();
    }

    
    
}
