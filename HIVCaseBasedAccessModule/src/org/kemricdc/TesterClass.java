/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc;

import org.kemricdc.dao.CRUDOperations;
import org.kemricdc.entities.Person;
import org.kemricdc.mappers.TransactionMapper;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class TesterClass {

    public static void main(String args[]) {
        String theString = "1975-08-25 00:00:00";
//        Person id = CRUDOperations.getInstance().getPersonIdByIdentifier("987987.0");
//        System.out.println(id);
        
        TransactionMapper.parseDate(theString);
    }

}
