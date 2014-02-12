
package at.ac.ait.ubicity.commons.protocol;

import java.io.Serializable;

/**
 *
 * @author jan van oort
 */
public  class Command implements Serializable   {
    
    
    private final Terms terms;
    
    private final Media media;
    
    private final Control control;
    
    
    public Command( Terms _terms, Media _media, Control _control )  {
        terms = _terms;
        media = _media;
        control = _control;
    }

    
    
    
    /**
     * @return the terms
     */
    public Terms getTerms() {
        return terms;
    }

    /**
     * @return the media
     */
    public Media getMedia() {
        return media;
    }


    /**
     * @return the control
     */
    public Control getControl() {
        return control;
    }
    
    
    
    @Override
    public final String toString()  {
        return toRESTString();
    }
    
    
    /**
     * Return a String representation of this command exactly as it was sent to the REST endpoint whence it originated
     */
    public final String toRESTString()  {
        StringBuilder sb = new StringBuilder();
        if( ! ( null == terms ) ) sb.append( terms.toString() );
        if( ! ( null == media ) ) sb.append( media.toString() );
        if( ! ( null == control ) ) sb.append( control.toString() );
        return sb.toString();
    }
}
