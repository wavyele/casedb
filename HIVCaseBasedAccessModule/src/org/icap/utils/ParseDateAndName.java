/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.icap.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class ParseDateAndName {

    public static Date parseDate(String dateString) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate = df.parse(dateString);
            System.out.println("The parsed date is : " + startDate);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        return startDate;
    }

    public static String[] parseLastName(String nameString) {
        String[] names = nameString.split(" ");
        for (String string : names) {
            System.out.println(string);

        }
        return names;

    }

}
