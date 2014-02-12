
package at.ac.ait.ubicity.commons.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jan
 */
public class Terms implements Serializable   {
    
    
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
}
