/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.icap;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.icap.mappers.TransactionMapper;
import org.icap.utils.TransactionSet;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class AccessDBApplication {

    private final static TransactionMapper transactionMapper = new TransactionMapper();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<TransactionSet> sets;

        while (true) {
            MiningThread thread = new MiningThread();
            //thread.processTransactions();
            sets = thread.processTransactions();

            for (TransactionSet set : sets) {
                System.out.println(set);
                transactionMapper.mapTransaction(set);

            }
            
            System.out.println("\nChecking out in the next ten 5 seconds\n");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AccessDBApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
