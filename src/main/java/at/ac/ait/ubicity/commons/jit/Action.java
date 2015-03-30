/**
    Copyright (C) 2014  AIT / Austrian Institute of Technology
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
package at.ac.ait.ubicity.commons.jit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Action object with data and commmand.
 *
 */
public class Action {

	private static Gson gson = new GsonBuilder().create();

	private String receiver;
	private String command;
	private String data;

	public Action() {
	}

	public Action(String receiver, String command, String data) {
		this.receiver = receiver;
		this.command = command;
		this.data = data;
	}

	public String getCommand() {
		return this.command;
	}

	public String getData() {
		return this.data;
	}

	public String getReceiver() {
		return receiver;
	}

	public String toJson() {
		return gson.toJson(this);
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setData(String data) {
		this.data = data;
	}
}
