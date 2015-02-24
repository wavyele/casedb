/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changetracker.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Vicky
 */
public class PropertiesManager {
    
   public static synchronized String readConfigFile(String key, String prop_file)
    {
        Properties prop = new Properties();
	String value=null;
        try(FileInputStream input =new FileInputStream(prop_file)) {
                prop.load(input);
                value = prop.getProperty(key);
        } catch (IOException ex) {
		System.err.println(ex.getMessage());
	}
        return value;
    }
   
   public static synchronized void modifyConfigFile(String key, String value, String prop_file){
        Properties prop = new Properties();

        try(FileInputStream fis =new FileInputStream(prop_file)) {
            prop.load(fis);            
            prop.setProperty(key, value);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        try(FileOutputStream fos=new FileOutputStream(prop_file)) {
            prop.store(fos, null);            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
