package at.ac.ait.ubicity.commons.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Calculates the ES Index based on the configured pattern.
 */
public class ESIndexCreator {
	private final static Logger logger = Logger.getLogger(ESIndexCreator.class);
	private SimpleDateFormat df;

	private final String idxName;
	private String idxPattern;
	private final String esType;

	public ESIndexCreator(String index, String type) {
		this(index, type, null);
	}

	public ESIndexCreator(String index, String type, String pattern) {
		this.idxName = index;
		this.esType = type;
		setPattern(pattern);
	}

	private void setPattern(String pattern) {
		if (isPatternValid(pattern)) {
			// check if valid date pattern
			try {
				df = new SimpleDateFormat(pattern);
				this.idxPattern = pattern;
			} catch (IllegalArgumentException e) {
				logger.warn("Invalid date pattern: " + pattern);
				this.idxPattern = null;
			}
		}
	}

	public String getIndex() {
		return createIndex();
	}

	public String getType() {
		return this.esType;
	}

	private String createIndex() {
		// create time based index
		if (isPatternValid(this.idxPattern) && df != null) {
			return this.idxName + "-" + df.format(new Date());
		}
		return this.idxName;
	}

	private boolean isPatternValid(String pat) {
		return pat != null && !pat.isEmpty();
	}
}
