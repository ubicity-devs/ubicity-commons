
package at.ac.ait.ubicity.commons.protocol;

import java.io.Serializable;

/**
 *
 * @author jan van oort
 */
public final class Answer implements Serializable    {
    
    
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
