package com.sibilantsolutions.iplayers.layer.app.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import com.sibilantsolutions.iplayers.layer.app.http.domain.HttpHeaders;
import com.sibilantsolutions.iplayers.layer.app.http.domain.HttpRequest;
import com.sibilantsolutions.iplayers.layer.app.http.domain.RequestLine;

public class Parser
{
    //final static private Logger log = LoggerFactory.getLogger( Parser.class );

    public static HttpRequest parseRequest( String str )
    {
        BufferedReader reader = new BufferedReader( new StringReader( str ) );
        String line;
        try
        {
            line = reader.readLine();
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
        RequestLine requestLine = RequestLine.parse( line );
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setRequestLine( requestLine );

        HttpHeaders headers = new HttpHeaders();
        boolean isDone = false;
        while ( ! isDone )
        {
            try
            {
                line = reader.readLine();
            }
            catch ( IOException e )
            {
                throw new RuntimeException( e );
            }

            if ( line != null )
            {
                if ( line.length() > 0 )
                {
                    //TODO: Account for headers that wrap lines, and multiple occurrences of a header.
                    int index = line.indexOf( ':' );
                    String key = line.substring( 0, index );
                    String value = line.substring( index + 1 ).trim();
                    headers.setHeader( key, value );
                }
                else
                {
                    //log.info( "Found blank line." );
                    isDone = true;
                }
            }
            else
                isDone = true;
        }

        httpRequest.setHttpHeaders( headers );

        return httpRequest;
    }

}
