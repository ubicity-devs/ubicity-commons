
package at.ac.ait.ubicity.commons.protocol;

import java.io.Serializable;

/**
 *
 * @author jan
 */
public final class Term implements Serializable    {
    
    
    
    private final String value;
    
    public Term( String _value )    {
        value = _value;
    }
    
    
    public final String getValue()  {
        return value;
    }
}
