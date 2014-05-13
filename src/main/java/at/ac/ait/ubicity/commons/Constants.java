package at.ac.ait.ubicity.commons;

import java.util.logging.Logger;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 Copyright (C) 2013  AIT / Austrian Institute of Technology
 http://www.ait.ac.at

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as
 published by the Free Software Foundation, either version 3 of the
 License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see http://www.gnu.org/licenses/agpl-3.0.html
 */

/**
 *
 * @author Jan van Oort
 * @version 0.2.1
 */
public class Constants {

	private static Logger logger = Logger.getLogger(Constants.class.getName());

	// the default size for the BlockingQueue, associated within the Core, with
	// each plugin separately
	public static int DEFAULT_QUEUE_SIZE;

	// delay, in milliseconds, granted to a plugin to orderly process the
	// mustStop() method
	public static int PLUGIN_GRACEFUL_TERMINATION_DELAY;

	public static String PLUGIN_DIRECTORY_NAME;

	public static int REVERSE_COMMAND_AND_CONTROL_PORT;

	static {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(
					"commons.cfg");

			DEFAULT_QUEUE_SIZE = config.getInt("commons.lmax.queue_size");
			PLUGIN_GRACEFUL_TERMINATION_DELAY = config
					.getInt("commons.plugins.graceful_termination_delay");
			PLUGIN_DIRECTORY_NAME = config
					.getString("commons.plugins.directory");
			REVERSE_COMMAND_AND_CONTROL_PORT = config
					.getInt("commons.plugins.reverse_cac_port");

		} catch (ConfigurationException e) {
			logger.warning("Could not configure from commons.cfg file");
		}
	}

}
