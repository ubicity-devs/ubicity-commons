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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jan
 */
public class Terms implements Serializable   {
    
    static final long serialVersionUID = 901098758911623997L;
    
    
    private final List< Term > termList = new ArrayList();
    
    
    public Terms()  {
        
    }
    
    
    public final List< Term > get()   {
        return termList;
    }
    
    
    @Override
    public final String toString()  {
        StringBuilder sb = new StringBuilder();
        if( termList.size() > 0 )   {
            sb.append( "q=(" );
            for( Term t: termList ) {
                sb.append( t.getValue().toLowerCase() ).append( " " );
            }
            sb.append( ")" );
            return sb.toString();
        }
        return "";
    }
    
    public final String getType()   {
        StringBuilder sb = new StringBuilder();
        for( Term t: termList ) {
            sb.append( t.getValue() );
        }
        return sb.toString().toLowerCase();
    }
}
