package org.kemricdc.entities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * {@link AppProperties used for getting static system properties from local Disk C: using an ap.properties file stored in a folder ("hapi")}
 * @author Stanslaus Odhiambo
 *
 *
 */
@Component
public class AppProperties {

	private Properties properties;
	private FileInputStream fileInputStream;

	/**
	 * 2 argument constructor .
	 * The properties object is autowired to ensure every module within the system uses a singleton {@link Properties} object for loading and 
	 * storing runtime properties for the application
	 * @param properties - the runtime properties object
	 * @param fileInputStream -  {@link FileInputStream} object used to load properties from the properties files
	 */
	@Autowired
	public AppProperties(Properties properties, FileInputStream fileInputStream) {
		this.properties = properties;
		this.fileInputStream = fileInputStream;

		try {
			properties.load(this.fileInputStream);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Method to get the value of a property from the property file using a key
	 * @param key - The key used to retrieve a given value from the properties file
	 * @return - Returns the value if retrieved by the key, NULL otherwise
	 */
	public String getProperty(String key) {

		return properties.getProperty(key);
	}

	/**
	 * Sets in memory, a runtime property that should be available system wide
	 * @param key - the key used to store and retrieve the value
	 * @param value - The actual system property to be stored
	 */
	public void setProperty(String key, String value) {
		properties.setProperty(key, value);

	}

}
