package at.ac.ait.ubicity.commons.util;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class ESIndexCreatorTest {

	@Test
	public void noPatternTest() {
		ESIndexCreator ic = new ESIndexCreator("index", "type");
		assertEquals("index", ic.getIndex());
		assertEquals("type", ic.getType());
	}

	@Test
	public void dailyPatternTest() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ESIndexCreator ic = new ESIndexCreator("index", "type", "yyyy-MM-dd");
		assertEquals("index-" + df.format(new Date()), ic.getIndex());
		assertEquals("type", ic.getType());
	}

	@Test
	public void monthlyPatternTest() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		ESIndexCreator ic = new ESIndexCreator("index", "type", "yyyy-MM");
		assertEquals("index-" + df.format(new Date()), ic.getIndex());
		assertEquals("type", ic.getType());
	}

	@Test
	public void yearlyPatternTest() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		ESIndexCreator ic = new ESIndexCreator("index", "type", "yyyy");
		assertEquals("index-" + df.format(new Date()), ic.getIndex());
		assertEquals("type", ic.getType());
	}

	@Test
	public void invalidDatePatternTest() {
		ESIndexCreator ic = new ESIndexCreator("index", "type", "yyyy-qq-dd");
		assertEquals("index", ic.getIndex());
		assertEquals("type", ic.getType());
	}

	@Test
	public void invalidPatternTest() {
		ESIndexCreator ic = new ESIndexCreator("index", "type", "");
		assertEquals("index", ic.getIndex());
		assertEquals("type", ic.getType());

		ic = new ESIndexCreator("index", "type", "date");
		assertEquals("index", ic.getIndex());
		assertEquals("type", ic.getType());
	}
}
