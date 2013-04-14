package com.sibilantsolutions.iplayers.layer.app.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

public class RequestLineTest
{

    @Test
    public void testParse()
    {
        String sample = loadResource( "/samples/RequestLine1.txt" );
        RequestLine rl = RequestLine.parse( sample );
        assertNotNull( rl );
        assertEquals( HttpMethod.GET, rl.getMethod() );
    }

    @Test
    public void testParse_bigGaps()
    {
        RequestLine rl = RequestLine.parse( "GET                foo        \t\t\t\t    bar     " );
        assertNotNull( rl );
        assertEquals( HttpMethod.GET, rl.getMethod() );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testParse_invalidMethod()
    {
        RequestLine.parse( "INVALIDMETHOD foo bar" );
    }
    
    private String loadResource( String path )
    {
        InputStream ins = getClass().getResourceAsStream( path );
        if ( ins == null )
            throw new RuntimeException( "Could not load resource=" + path );
        
        BufferedReader reader = new BufferedReader( new InputStreamReader( ins ) );
        StringBuilder buf = new StringBuilder();
        try
        {
            String line;
            while ( ( line = reader.readLine() ) != null )
            {
                buf.append( line );
            }
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
        
        return buf.toString();
    }

}
