package at.ac.ait.ubicity.commons.templates;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractDTO {

	private static Gson gson = new GsonBuilder().create();

	private final static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");
	{
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * Returns the date as standard formated string representation or null.
	 * 
	 * @param date
	 * @return
	 */
	protected String dateAsString(Date date) {

		if (date != null) {
			return df.format(date);
		}

		return null;
	}

	public String toJson() {
		return gson.toJson(this);
	}
}
