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
public final class Answer implements Serializable    {
    
    static final long serialVersionUID = 8298698192183635193L;
    
    public static final Answer ACK = new Answer( 200 );
    
    public static final Answer PARTIAL_ACK = new Answer( 202 );
    
    public static final Answer FAIL = new Answer( 400 );
    
    public static final Answer NOT_IMPLEMENTED_YET = new Answer( 503 );
    
    public static final Answer ERROR = new Answer( 900 );
    
    
    
    private final int code;
    
    
    
    public Answer( int _code )  {
        code = _code;
    }
    
    
    public final boolean equals( Object o ) {
        if( null == o ) return false;
        if( ! ( o instanceof Answer ) ) return false;
        Answer a = ( Answer ) o;
        return code == a.code;
        
    }
}
