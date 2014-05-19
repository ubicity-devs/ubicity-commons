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
package at.ac.ait.ubicity.commons.protocol;

import java.io.Serializable;

/**
 *
 * @author jan van oort
 */
public class Command implements Serializable {

	static final long serialVersionUID = -3705600497924966465L;

	private final Terms terms;

	private final Media media;

	private final Control control;

	public Command(Terms _terms, Media _media, Control _control) {
		terms = _terms;
		media = _media;
		control = _control;
	}

	/**
	 * @return the terms
	 */
	public Terms getTerms() {
		return terms;
	}

	/**
	 * @return the media
	 */
	public Media getMedia() {
		return media;
	}

	/**
	 * @return the control
	 */
	public Control getControl() {
		return control;
	}

	@Override
	public final String toString() {
		return toRESTString();
	}

	/**
	 * Return a String representation of this command exactly as it was sent to
	 * the REST endpoint whence it originated
	 */
	public final String toRESTString() {
		StringBuilder sb = new StringBuilder();
		if (terms != null)
			sb.append(terms.toString());
		if (media != null)
			sb.append(media.toString());
		if (control != null)
			sb.append(control.toString());
		return sb.toString();
	}
}
