package at.ac.ait.ubicity.commons.util;

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
import java.util.logging.Logger;

import org.apache.commons.io.LineIterator;
import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author jan van oort
 */
public final class XML2JSONConverter {


    public static boolean mustWriteJSON;
    
    public XML2JSONConverter() {
    }




    public final static String XML_EXTENSION = ".xml";

    public final static String JSON_EXTENSION = ".json";
    
    public final static double TWO  = new Double( "2" );
    
    static TransportClient esclient;
    
    public final static void main( String... args ) throws IOException  {
        File in = new File( args[ 0 ] );
        File outputDirectory = new File( args[ 1 ] );

        
        //default behaviour: do not write JSON output files to local file system
        boolean __shouldWriteJSON = false;
        if( args.length == 3 ) __shouldWriteJSON = args[ 2 ].toLowerCase().equals( "dojsonwrite" );
        
        XML2JSONConverter.mustWriteJSON = __shouldWriteJSON;
        XML2JSONConverter converter = new XML2JSONConverter();
        
        Settings settings = ImmutableSettings.settingsBuilder().build();        
        esclient = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress( "ubicity.ait.ac.at", 9300));
        
        if( in.isDirectory() ) {
            converter.handleDirectory( in, outputDirectory );
        }
        else    {
            long _start, _lapse;
            _start = System.nanoTime();
            
            FileHandler _handler = new FileHandler( outputDirectory, null );
            _handler.convert( in );
            _lapse = ( System.nanoTime() - _start ) / 1000;
            System.out.println( "processing xml file of " + in.length() + " bytes took " + _lapse + " microseconds" );
        }
        System.exit( 0 );
    }

     
    private  void handleDirectory(File in, File outputDirectory ) {
        final FileFilter _xmlFilter = (File _f) -> _f.getName().toLowerCase().endsWith( XML_EXTENSION );
        
        File[] _xmlFiles = in.listFiles( _xmlFilter );
        
        final ExecutorService exec = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors() * 2 );        
        final Disruptor< XMLFile > disruptor = new Disruptor( FileHandler.EVENT_FACTORY, ( int ) Math.pow( TWO, 17 ), exec );
        
        final EventHandler< XMLFile > _eventHandler = new FileHandler( outputDirectory, esclient );
        disruptor.handleEventsWith( _eventHandler );
        RingBuffer< XMLFile > ringBuffer = disruptor.start();
        
        
        try  {
            
            CreateIndexRequestBuilder createIndexRequestBuilder = esclient.admin().indices().prepareCreate( "story" );
            createIndexRequestBuilder.execute().actionGet();
        }
        catch( ElasticSearchException t )    {
            //do nothing, we may get an IndexAlreadyExistsException, but don't care about that, here and now
        }        
        int fileCounter = 0;
        
        System.out.println( "[INFO] beginning processing of XML files in " + in.getAbsolutePath() );
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
        System.out.println( "[INFO] processed " + fileCounter + " files in " + _lapse + " microseconds" );
    }
}


final class FileHandler implements EventHandler< XMLFile> {

    private final File outputDirectory;
    
    
     //Nifty: Java 8 allows for lambda expressions. Let's use them. 
     public final static EventFactory< XMLFile > EVENT_FACTORY = () -> new XMLFile();    
    
    TransportClient esclient;
     
     
    FileHandler( final File _outputDirectory, TransportClient _esclient )   { 
        outputDirectory = _outputDirectory; 
        esclient = _esclient;
    }

    
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
         String __id = __fileName.substring( 0, _pointLocation );
              try {
                    IndexRequestBuilder indexRequestBuilder = esclient.prepareIndex( "story", "doc" );
                    try {
                            indexRequestBuilder.setSource( json.toString() );
                            indexRequestBuilder.setId( __id );
                            IndexResponse response = indexRequestBuilder .execute().actionGet();
                            
                            if(  response == null )     {
                                Logger.getLogger( this.getClass().getName() ).warning( this.getClass().getName() + " : got a <null> IndexResponse when trying to index against elasticsearch " );
                            }
                    }
                    catch( ElasticSearchException tt )   {
                        tt.printStackTrace();
                    }
              }
            catch( JSONException somethingWrong )   {
                Logger.getLogger( this.getClass().getName() ).fine( "caught a JSONException : " + somethingWrong.toString() );
            }                    

          if( XML2JSONConverter.mustWriteJSON )   {
            String __JSONfileName = outputDirectory + "/" + __id + XML2JSONConverter.JSON_EXTENSION;
            try ( FileWriter _jsonFile = new FileWriter( new File( __JSONfileName ) )) {
                 _jsonFile.write( json.toString( 3 ) );
                 _jsonFile.flush();
                 _jsonFile.close();
             }
          }


	      /**
        String __JSONfileName = outputDirectory + "/" + __id + XML2JSONConverter.JSON_EXTENSION;
         try (FileWriter _jsonFile = new FileWriter( new File( __JSONfileName ) )) {
             _jsonFile.write( json.toString( 3 ) );
             _jsonFile.flush();
             _jsonFile.close();
         }       
	      */ 

    }
}

class XMLFile   {
    
    public XMLFile()    {}

    File theRealFile;
}