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
import java.util.Objects;

/**
 *
 * @author jan
 */
public class Medium implements Serializable {
    
    static final long serialVersionUID = 8779385666816516110L;

    
    public final static Medium FLICKR =  new Medium( "flickr" );
    
    
    protected final String name;
    
    
    
    protected Medium( String _name )    {
        name = _name;
    }
    
    
    
    public final String getName()   {
        return name;
    }
    
    
    @Override
    public final boolean equals( Object o ) {
        if( null == o ) return false;
        if( ! ( o instanceof Medium ) ) return false;
        Medium m = ( Medium ) o;
        return name.toLowerCase().equals( m.getName().toLowerCase() );
    }

    
    
    @Override
    public final int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode( name.toLowerCase() );
        return hash;
    }
}
