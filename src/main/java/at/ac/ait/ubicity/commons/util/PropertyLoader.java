package at.ac.ait.ubicity.commons.util;

import java.net.URL;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class PropertyLoader {

	private Configuration config;
	private boolean valid = false;

	final static Logger logger = Logger.getLogger(PropertyLoader.class);

	public PropertyLoader(URL file) {

		try {
			config = new PropertiesConfiguration(file);
			valid = true;
		} catch (ConfigurationException noConfig) {
			logger.fatal("Configuration not found! " + noConfig.toString());
		}
	}

	public String getString(String key) {
		return config.getString(key);
	}

	public String[] getStringArray(String key) {
		return config.getStringArray(key);
	}

	public int getInt(String key) {
		return config.getInt(key);
	}

	public boolean isValid() {
		return valid;
	}
}
