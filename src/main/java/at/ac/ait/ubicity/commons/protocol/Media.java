
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

import static at.ac.ait.ubicity.commons.protocol.Medium.FLICKR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jan
 */
public class Media implements Serializable {
    
    static final long serialVersionUID = 2293768857111018211L;
    
    
    protected List< Medium > mediumList = new ArrayList();
    
    
    public static final Map< String, Medium > knownMedia = new HashMap();
    
    
    static  {
        knownMedia.put( FLICKR.name, FLICKR );
    }    
    
    
    
    public Media()  {
        
    }
    
    
    public final List< Medium > get()   {
        return mediumList;
    }
    
    
    
    @Override
    public final String toString()  {
        StringBuilder sb = new StringBuilder();
        if( mediumList.size() > 0 ) {
            sb.append( "m=(" );
            for( Medium m : mediumList )    {
                sb.append( m.getName() );
                sb.append( " " );
            }
            sb.append( ")" );
            return sb.toString();
        }
        return "";
    }
}
