

package at.ac.ait.ubicity.commons.protocol;

import java.util.Objects;

/**
 *
 * @author jan
 */
public class Medium {
    
    
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
