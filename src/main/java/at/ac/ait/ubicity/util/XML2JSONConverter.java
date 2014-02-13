
package at.ac.ait.ubicity.util;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.LineIterator;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author jan van oort
 */
public final class XML2JSONConverter {

    public final static String XML_EXTENSION = ".xml";

    public final static String JSON_EXTENSION = ".json";
    
    public final static double TWO  = new Double( "2" );
    
    
    public final static void main( String... args ) throws IOException  {
        File in = new File( args[ 0 ] );
        File outputDirectory = new File( args[ 1 ] );
        if( in.isDirectory() ) handleDirectory( in, outputDirectory );
        else    {
            long _start, _lapse;
            _start = System.nanoTime();
            new FileHandler( outputDirectory ).convert( in );
            _lapse = ( System.nanoTime() - _start ) / 1000;
            System.out.println( "processing xml file of " + in.length() + " bytes took " + _lapse + " microseconds" );
        }
    }

    
    
    private static void handleDirectory(File in, File outputDirectory ) {
        final FileFilter _xmlFilter = (File _f) -> _f.getName().toLowerCase().endsWith( XML_EXTENSION );
        
        File[] _xmlFiles = in.listFiles( _xmlFilter );
        
        final ExecutorService exec = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors() * 2 );        
        final Disruptor< XMLFile > disruptor = new Disruptor( FileHandler.EVENT_FACTORY, ( int ) Math.pow( TWO, 17 ), exec );
        
        final EventHandler< XMLFile > _eventHandler = new FileHandler( outputDirectory );
        disruptor.handleEventsWith( _eventHandler );
        RingBuffer< XMLFile > ringBuffer = disruptor.start();
        
        int fileCounter = 0;
        
        System.out.println( "[ INFO ] beginning processing of XML files in " + in.getAbsolutePath() );
        long _start, _lapse;
        _start = System.nanoTime();
        for( File __f: _xmlFiles )    {
            long _sequence = ringBuffer.next();
            XMLFile _xmlFile = ringBuffer.get( _sequence );
            _xmlFile.theRealFile = __f;
            ringBuffer.publish( _sequence );
            fileCounter++;
        }
        disruptor.shutdown();
        _lapse = ( System.nanoTime() - _start ) / 1000;
        System.out.println( "[ INFO ] processed " + fileCounter + " files in " + _lapse + " microseconds" );
    }
}


final class FileHandler implements EventHandler< XMLFile> {

    private final File outputDirectory;
    

     //Nifty: Java 8 allows for lambda expressions. Let's use them. 
     public final static EventFactory< XMLFile > EVENT_FACTORY = () -> new XMLFile();    
    
    FileHandler( final File _outputDirectory )   { outputDirectory = _outputDirectory; }

    
    @Override
    public void onEvent( XMLFile t, long l, boolean bln) throws Exception {
        convert( t.theRealFile );
    }
    
    public void convert( File _f ) throws IOException  {
        LineIterator reader = new LineIterator( new FileReader( _f ) );
        StringBuilder sb = new StringBuilder();
        while( reader.hasNext() )   {
            sb.append( reader.next() );
        }
        JSONObject json = XML.toJSONObject( sb.toString() );
        
        String __fileName = _f.getName();
        int _pointLocation = __fileName.indexOf( '.' );

        String __JSONfileName = outputDirectory + "/" + __fileName.substring( 0, _pointLocation ) + XML2JSONConverter.JSON_EXTENSION;
         try (FileWriter _jsonFile = new FileWriter( new File( __JSONfileName ) )) {
             _jsonFile.write( json.toString( 3 ) );
             _jsonFile.flush();
             _jsonFile.close();
         }        
    }
}

class XMLFile   {
    
    public XMLFile()    {}

    File theRealFile;
}