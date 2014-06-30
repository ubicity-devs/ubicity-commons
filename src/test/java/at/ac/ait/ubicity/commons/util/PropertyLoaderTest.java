package at.ac.ait.ubicity.commons.util;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class PropertyLoaderTest {

	@Test
	public void encTest() {
		System.setProperty("ubicity.enc_key", "<key>");
		PropertyLoader loader = new PropertyLoader();
		assertEquals("test", loader.decrypt(loader.encrypt("test")));
	}

	@Test
	@Ignore
	public void printEncText() {
		System.setProperty("ubicity.enc_key", "<key>");

		PropertyLoader loader = new PropertyLoader();

		System.out.println(loader.encrypt("test"));
	}
}
