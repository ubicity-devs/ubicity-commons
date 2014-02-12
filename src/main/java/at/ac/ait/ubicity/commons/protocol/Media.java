
package at.ac.ait.ubicity.commons.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jan
 */
public class Media {
    
    
    protected List< Medium > mediumList = new ArrayList();
    
    
    
    
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
