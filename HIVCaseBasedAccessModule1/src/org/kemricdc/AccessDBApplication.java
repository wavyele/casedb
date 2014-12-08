/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc;

import java.util.List;
import org.kemricdc.constants.Event;
import org.kemricdc.entities.TransactionSet;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class AccessDBApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println(Event.valueOf("BIRTH"));

        List<TransactionSet> sets;

        MiningThread thread = new MiningThread();
        //thread.processTransactions();
        sets = thread.processTransactions();

        for (TransactionSet set : sets) {
            System.out.println(set);

        }

//        final String fileName = "C:\\\\Comprehensive Care Clinic Patient Application Database (C-PAD)\\\\CCC PATIENT APPLICATION DB30SEP13.mdb";
//
//        Connection con = null;
//
//        try {
//
//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//
//            String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + fileName;
//
//            con = DriverManager.getConnection(url, "", "");
//            if (con != null) {
//                System.out.println("Database Connection Successfull");
//                //String sql2 = "SELECT DISTINCT patient_id FROM tblpatient_information "
//                //       + "WHERE patient_id IS NOT NULL";
//                //Statement statement=con.createStatement();
//                //  ResultSet resultSet=statement.executeQuery(sql2);
//
//                //while(resultSet.next()){
//                //    System.out.printf("%s %s",resultSet.getString("patient_id"),"\n");
//                //}
//            } else {
//                System.out.println("Database Connection was not established");
//            }
//
//        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println(e.getMessage());
//
//        } finally {
//
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//
//        }

    }

}
