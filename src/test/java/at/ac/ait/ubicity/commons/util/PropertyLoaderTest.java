package at.ac.ait.ubicity.commons.util;

import org.junit.Test;

public class PropertyLoaderTest {

	@Test
	public void test() {

		PropertyLoader prop = new PropertyLoader(null);

		PropertyLoader.setEncrKey("Test");

		String encr = prop.encrypt("My message");
		System.out.println(encr);

		String dec = prop.decrypt(encr);
		System.out.println(dec);
	}
}
