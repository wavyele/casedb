/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kemricdc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class AppProperties {
    private static final String folderName="hapi";
    private static final String propsFile="app.properties";
    private static File fileName;
    private static final Properties props=new Properties();
    
    
    public static String getProperty(String key){    
        String value = null;
        fileName= new File(System.getProperty("user.home")+System.getProperty("file.separator")+folderName+
                System.getProperty("file.separator")+propsFile);
        try(FileInputStream fis=new FileInputStream(fileName)) {
            props.load(fis);
            value=props.getProperty(key);
            System.out.println(value);
        } catch (IOException ex) {
            Logger.getLogger(AppProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return value;
    }
    
    
    
    
    
    
    
}
