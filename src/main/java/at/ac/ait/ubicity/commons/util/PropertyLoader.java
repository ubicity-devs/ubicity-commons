package at.ac.ait.ubicity.commons.util;

import java.net.URL;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class PropertyLoader {

	private Configuration config;

	private static Key encrKey = null;

	private static Cipher cipher = null;

	private static final String ENC_PREFIX = "encr:";

	final static Logger logger = Logger.getLogger(PropertyLoader.class);

	public PropertyLoader(URL file) {
		this();
		try {
			config = new PropertiesConfiguration(file);
		} catch (ConfigurationException noConfig) {
			logger.fatal("Configuration not found! " + noConfig.toString());
		}
	}

	PropertyLoader() {
		try {
			if (cipher == null) {
				setEncrKey(System.getProperty("ubicity.enc_key"));
				cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			}
		} catch (Exception e) {
			logger.error("Exc caught in gettign cipher instance", e);
		}
	}

	public String getString(String key) {
		return decrypt(config.getString(key));
	}

	public String[] getStringArray(String key) {
		return config.getStringArray(key);
	}

	public int getInt(String key) {
		return config.getInt(key);
	}

	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	String decrypt(String value) {

		if (value == null || !value.startsWith(ENC_PREFIX)) {
			return value;
		}

		String encrText = value.substring(ENC_PREFIX.length());

		try {
			cipher.init(Cipher.DECRYPT_MODE, encrKey);

			return new String(cipher.doFinal(Base64.getDecoder().decode(
					encrText.getBytes("UTF-8"))));
		} catch (Exception e) {
			logger.error("Exc caught in decrypting", e);
		}
		return encrText;
	}

	String encrypt(String value) {

		String encText = value;

		try {
			cipher.init(Cipher.ENCRYPT_MODE, encrKey);

			encText = ENC_PREFIX
					+ new String(Base64.getEncoder().encode(
							cipher.doFinal(value.getBytes("UTF-8"))));
		} catch (Exception e) {
			logger.error("Exc caught in encrypting", e);
		}
		return encText;
	}

	void setEncrKey(String key) {

		if (key == null) {
			logger.error("Encryption key is null - exiting");
			return;
		}

		MessageDigest sha = null;
		byte[] byteKey;
		try {
			byteKey = key.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			byteKey = sha.digest(byteKey);
			byteKey = Arrays.copyOf(byteKey, 16);

			encrKey = new SecretKeySpec(byteKey, "AES");

		} catch (Exception e) {
			logger.error("Exc caught in setting encryption key", e);
		}
	}
}
