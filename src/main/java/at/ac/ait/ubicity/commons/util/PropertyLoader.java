package at.ac.ait.ubicity.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.elasticsearch.common.Base64;

public class PropertyLoader {

	private Configuration config;
	private boolean valid = false;

	private static Key encrKey = null;

	private Cipher cipher = null;

	private static final String ENC_PREFIX = "encr:";

	final static Logger logger = Logger.getLogger(PropertyLoader.class);

	public PropertyLoader(URL file) {

		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			config = new PropertiesConfiguration(file);
			valid = true;
		} catch (ConfigurationException noConfig) {
			logger.fatal("Configuration not found! " + noConfig.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public boolean isValid() {
		return valid;
	}

	String decrypt(String value) {

		if (value == null || !value.startsWith(ENC_PREFIX)) {
			return value;
		}

		String encrText = value.substring(ENC_PREFIX.length());

		try {
			cipher.init(Cipher.DECRYPT_MODE, encrKey);

			return new String(cipher.doFinal(Base64.decode(encrText
					.getBytes("UTF-8"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encrText;
	}

	String encrypt(String value) {

		String encText = value;

		try {
			cipher.init(Cipher.ENCRYPT_MODE, encrKey);

			encText = ENC_PREFIX
					+ org.elasticsearch.common.Base64.encodeBytes(cipher
							.doFinal(value.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encText;
	}

	public static void setEncrKey(String key) {

		MessageDigest sha = null;
		byte[] byteKey;
		try {
			byteKey = key.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			byteKey = sha.digest(byteKey);
			byteKey = Arrays.copyOf(byteKey, 16); // use only first 128 bit

			encrKey = new SecretKeySpec(byteKey, "AES");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
