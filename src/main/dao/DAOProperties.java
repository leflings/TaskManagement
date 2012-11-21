package main.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import main.exceptions.DAOConfigurationException;

public class DAOProperties {

	public static final String CONNECTION_NAME = "dtu_remote_jesper";
	private static final String PROPERTIES_FILE = "dao.properties";
	private static final Properties PROPERTIES = new Properties();

	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

		if (propertiesFile == null) {
			throw new DAOConfigurationException("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
		}

		try {
			PROPERTIES.load(propertiesFile);
		} catch (IOException e) {
			throw new DAOConfigurationException("Cannot load properties file '" + PROPERTIES_FILE + "'.", e);
		}
	}

	private String specificKey;

	public DAOProperties(String specificKey) throws DAOConfigurationException {
		this.specificKey = specificKey;
	}

	// Actions
	// ------------------------------------------------------------------------------------

	/**
	 * Returns the DAOProperties instance specific property value associated
	 * with the given key with the option to indicate whether the property is
	 * mandatory or not.
	 * 
	 * @param key
	 *            The key to be associated with a DAOProperties instance
	 *            specific value.
	 * @param mandatory
	 *            Sets whether the returned property value should not be null
	 *            nor empty.
	 * @return The DAOProperties instance specific property value associated
	 *         with the given key.
	 * @throws DAOConfigurationException
	 *             If the returned property value is null or empty while it is
	 *             mandatory.
	 */
	public String getProperty(String key, boolean mandatory) throws DAOConfigurationException {
		String fullKey = specificKey + "." + key;
		String property = PROPERTIES.getProperty(fullKey);

		if (property == null || property.trim().length() == 0) {
			if (mandatory) {
				throw new DAOConfigurationException("Required property '" + fullKey + "'" + " is missing in properties file '" + PROPERTIES_FILE + "'.");
			} else {
				// Make empty value null. Empty Strings are evil.
				property = null;
			}
		}

		return property;
	}

}
