

package at.ac.ait.ubicity.commons.protocol;

import java.io.Serializable;

/**
 *
 * @author jan van oort
 */
public final class Control implements Serializable   {
    
    private final static int INTERNAL_STOP_CODE = 3;
    private final static int INTERNAL_PAUSE_CODE = 5;
    
    public final static Control PAUSE = new Control( INTERNAL_PAUSE_CODE );
    
    public final static Control STOP = new Control( INTERNAL_STOP_CODE );
    
    
    
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