/**
Copyright (C) 2013 AIT / Austrian Institute of Technology
http://www.ait.ac.at

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see http://www.gnu.org/licenses/agpl-3.0.html
 */

package at.ac.ait.ubicity.commons.interfaces;

import java.util.SortedSet;

import net.xeoh.plugins.base.Plugin;
import at.ac.ait.ubicity.commons.addon.JSONObjectHandler;

/**
 *
 * "Marker" interface for all ubicity add-ons. Add-ons are basically different
 * from plugins in that they 1) may bring EventHandlers for dealing with
 * JSONObjects 2) provide a set of functionalities different from ( "stupid" or
 * ReverseControllable ) plugins
 *
 * @author jan
 */
public interface UbicityAddOn extends Plugin {

	public SortedSet<Class<? extends JSONObjectHandler>> getDeclaredEventHandlers();

	public boolean shutDown();
}