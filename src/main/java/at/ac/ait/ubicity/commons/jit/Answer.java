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

public final class Answer {

	private static Gson gson = new GsonBuilder().create();

	public enum Status {
		PROCESSED, INTERNAL_ERROR, //
		NOT_RESPONSIBLE, COMMAND_NOT_RECOGNIZED
	};

	private final Status status;

	private final Action action;

	private final String data;

	public Answer(Action action, Status status, String data) {
		this.action = action;
		this.status = status;
		this.data = data;
	}

	public Answer(Action action, Status status) {
		this(action, status, null);
	}

	public Action getAction() {
		return this.action;
	}

	public String getData() {
		return this.data;
	}

	public String toJson() {
		return gson.toJson(this);
	}

	public Status getStatus() {
		return status;
	}
}
