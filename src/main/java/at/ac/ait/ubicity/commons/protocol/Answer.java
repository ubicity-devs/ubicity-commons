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
    
    public final static int HTTP_OK = 200;
    public final static int HTTP_ACCEPTED = 202;
    public final static int HTTP_BAD_REQUEST = 400;
    public final static int HTTP_UNAVAILABLE = 503;
    public final static int HTTP_SERVERSIDE_ERROR = 900;
    public final static int HTTP_UNSPECIFIED_ERROR = 907;
    
    
    
    public static final Answer ACK = new Answer( HTTP_OK );
    
    public static final Answer PARTIAL_ACK = new Answer( HTTP_ACCEPTED );
    
    public static final Answer FAIL = new Answer( HTTP_BAD_REQUEST );
    
    public static final Answer NOT_IMPLEMENTED_YET = new Answer( HTTP_UNAVAILABLE );
    
    public static final Answer ERROR = new Answer( HTTP_SERVERSIDE_ERROR );

    public static final Answer NIL = new Answer( HTTP_UNSPECIFIED_ERROR );
    
    
    
    
    private final int code;
    
    
    
    public Answer( int _code )  {
        code = _code;
    }
    
    
    public final int getCode()  {
        return code;
    }
    
    public final boolean equals( Object o ) {
        if( null == o ) return false;
        if( ! ( o instanceof Answer ) ) return false;
        Answer a = ( Answer ) o;
        return code == a.code;
        
    }
}
