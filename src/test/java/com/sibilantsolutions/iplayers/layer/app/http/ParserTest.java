package com.sibilantsolutions.iplayers.layer.app.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.sibilantsolutions.iplayers.layer.app.http.domain.HttpHeaders;
import com.sibilantsolutions.iplayers.layer.app.http.domain.HttpRequest;
import com.sibilantsolutions.iplayers.layer.app.http.domain.RequestLine;

public class ParserTest
{

    @Test
    public void test()
    {
        String rsc = loadResource( "/samples/get_firefox.bin" );
        assertEquals( 303, rsc.length() );
        
        HttpRequest req = Parser.parseRequest( rsc );
        assertNotNull( req );
        RequestLine requestLine = req.getRequestLine();
        assertNotNull( requestLine );
        assertEquals( HttpMethod.GET, requestLine.getMethod() );
        assertEquals( "/foo", requestLine.getRequestUri() );
        assertEquals( "HTTP/1.1", requestLine.getHttpVersion() );
        HttpHeaders headers = req.getHttpHeaders();
        assertNotNull( headers );
        assertEquals( "localhost:8888", headers.getHeader( "Host" ) );
        assertEquals( "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:20.0) Gecko/20100101 Firefox/20.0", headers.getHeader( "User-Agent" ) );
        assertEquals( "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", headers.getHeader( "Accept" ) );
        assertEquals( "en-US,en;q=0.5", headers.getHeader( "Accept-Language" ) );
        assertEquals( "gzip, deflate", headers.getHeader( "Accept-Encoding" ) );
        assertEquals( "1", headers.getHeader( "DNT" ) );
        assertEquals( "keep-alive", headers.getHeader( "Connection" ) );
        assertNull( req.getHttpEntity() );
    }

    static private String loadResource( String path )
    {
        StringBuilder sBuf = new StringBuilder();
        InputStream ins = ParserTest.class.getResourceAsStream( path );
        int numRead;
        byte[] buf = new byte[1024];
        try
        {
            while ( ( numRead = ins.read( buf ) ) != -1 )
            {
                String s = new String( buf, 0, numRead );
                sBuf.append( s );
            }
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
        
        return sBuf.toString();
    }

}
