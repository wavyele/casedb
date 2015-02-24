package org.kemricdc.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * {@link AppProperties used for getting static system properties from local
 * Disk C: using an ap.properties file stored in a folder ("hapi")}
 * 
 * @author Stanslaus Odhiambo
 * 
 * 
 */

public class AppProperties {

	private Properties properties;
	private FileInputStream fileInputStream;
	private File propertiesFile;

	/**
	 * 2 argument constructor . The properties object is autowired to ensure
	 * every module within the system uses a singleton {@link Properties} object
	 * for loading and storing runtime properties for the application
	 * 
	 * @param properties
	 *            - the runtime properties object
	 * @param fileInputStream
	 *            - {@link FileInputStream} object used to load properties from
	 *            the properties files
	 */

	public AppProperties() {

	}

	public File getPropertiesFile() {
		return propertiesFile;
	}

	public void setPropertiesFile(File propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public FileInputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public AppProperties(Properties properties, File propertiesFile) {
		this.properties = properties;
		try {
			this.fileInputStream = new FileInputStream(propertiesFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			properties.load(this.fileInputStream);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Method to get the value of a property from the property file using a key
	 * 
	 * @param key
	 *            - The key used to retrieve a given value from the properties
	 *            file
	 * @return - Returns the value if retrieved by the key, NULL otherwise
	 */
	public Object getProperty(String key) {

		return properties.getProperty(key);
	}

	/**
	 * Sets in memory, a runtime property that should be available system wide
	 * 
	 * @param key
	 *            - the key used to store and retrieve the value
	 * @param value
	 *            - The actual system property to be stored
	 */
	public void setProperty(String key, String value) {
		properties.setProperty(key, value);

	}

}
