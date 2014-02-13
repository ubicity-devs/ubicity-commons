
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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jan van oort
 */
public final class Control implements Serializable   {
    
    static final long serialVersionUID = -2083026102020097962L;
    
    
    private final static int INTERNAL_STOP_CODE = 3;
    private final static int INTERNAL_PAUSE_CODE = 5;
    
    public final static Control PAUSE = new Control( INTERNAL_PAUSE_CODE );
    
    public final static Control STOP = new Control( INTERNAL_STOP_CODE );
    
    
    public final static Map< String, Control > knownControls = new HashMap();
    
    
    static  {
        knownControls.put( "pause", PAUSE );
        knownControls.put( "stop", STOP );
    }
    
    
    
    private final int code;
    
    
    protected Control( int _code )  {
        code = _code;
    }
    
    
    public final int getCode()  {
        return code;
    }
    
    
    @Override
    public final boolean equals( Object o ) {
        if( null == o ) return false;
        if( ! ( o instanceof Control ) ) return false;
        Control c = ( Control ) o;
        return code == c.code;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.code;
        return hash;
    }
    
    
    
    @Override
    public final String toString( ) {
        StringBuilder sb = new StringBuilder();
        sb.append( "c=" );
        switch( code )  {
            case INTERNAL_PAUSE_CODE:
                sb.append( "pause" );
            case INTERNAL_STOP_CODE:
                sb.append( "stop" );
        }
        return sb.toString();
    }
}