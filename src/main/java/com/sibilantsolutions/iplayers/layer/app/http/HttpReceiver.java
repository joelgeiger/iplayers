package com.sibilantsolutions.iplayers.layer.app.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.iptools.event.ReceiveEvt;
import com.sibilantsolutions.iptools.event.SocketListenerI;
import com.sibilantsolutions.iplayers.layer.app.http.domain.HttpHeaders;
import com.sibilantsolutions.iplayers.layer.app.http.domain.RequestLine;
import com.sibilantsolutions.iptools.util.Socker;


//TODO: Enforce maximum length of method/uri/version/overall request line.
//TODO: Handle data received in multiple packets or even one byte at a time.

public class HttpReceiver implements SocketListenerI
{
    final static private Logger log = LoggerFactory.getLogger( HttpReceiver.class );
    
//    private enum RecvState
//    {
//        NEED_METHOD,
//        NEED_URI,
//        NEED_HTTP_VERSION,
//        NEED_HOST
//    }
//    
//    private RecvState recvState = RecvState.NEED_METHOD;
    
    @Override
    public void onReceive( ReceiveEvt evt )
    {
        //Charset cs = Charset.forName( "US-ASCII" );
        //String s = new String( evt.getData(), evt.getOffset(), evt.getLength(), cs );
        ByteArrayInputStream bis = new ByteArrayInputStream( evt.getData(), evt.getOffset(), evt.getLength() );
        BufferedReader br = new BufferedReader( new InputStreamReader( bis ) );
        String s;
        try
        {
            s = br.readLine();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }
        RequestLine requestLine = RequestLine.parse( s );
        
//        byte[] data = evt.getData();
//        int offset = evt.getOffset();
//        int length = evt.getLength();
//        
//        switch ( recvState )
//        {
//            case NEED_METHOD:
//                HttpMethod method = parseMethod( data, offset, length );
//                recvState = RecvState.NEED_URI;
//                break;
//            default:
//                throw new IllegalStateException( "Unexpected state=" + recvState );
//        }
        
        switch( requestLine.getMethod() )
        {
            case GET:
                String uri = requestLine.getRequestUri();
                log.warn( "Getting uri as local file={}.", uri );
                File file = new File( uri );
                if ( file.isFile() && file.exists() )
                {
                    long length = file.length();
                    try ( FileInputStream fis = new FileInputStream( file ) )
                    {
                        String responseLine = "HTTP/1.1 200 OK" + Constants.CRLF;
                        try
                        {
                            Socker.send( responseLine, evt.getSource() );
                            Socker.send( HttpHeaders.DATE + ": " + HttpDateFormat.format( new Date() ) + Constants.CRLF, evt.getSource() );
                            Socker.send( HttpHeaders.CONTENT_LENGTH + ": " + length + Constants.CRLF, evt.getSource() );
                            //TODO: Headers here.
                            Socker.send( Constants.CRLF, evt.getSource() );

                            byte[] buf = new byte[1024];
                            int numRead;
                            while ( ( numRead = fis.read( buf ) ) != -1 )
                            {
                                Socker.send( buf, 0, numRead, evt.getSource() );
                            }
                        }
                        catch ( IOException e )
                        {
                            // TODO Auto-generated catch block
                            throw new UnsupportedOperationException( "OGTE TODO!", e );
                        }
                    }
                    catch ( IOException e )
                    {
                        // TODO Auto-generated catch block
                        throw new UnsupportedOperationException( "OGTE TODO!", e );
                    }
                }
                else
                {
                    throw new UnsupportedOperationException( "OGTE TODO!" );
                }
                break;
            default:
                //TODO: Send unimplemented status code.
                throw new UnsupportedOperationException( "OGTE TODO! Unexpected method=" + requestLine.getMethod() );
        }
    }

//    private HttpMethod parseMethod( byte[] data, int offset, int length )
//    {
//        StringBuilder buf = new StringBuilder( 16 );
//        
//        boolean isDone = false;
//        
//        for ( int i = offset; ! isDone && i < offset + length; i++ )
//        {
//            char c = (char)( data[i] & 0xFF );
//            
//            boolean isWhitespace = Character.isWhitespace( c );
//
//                //Skip leading whitespace.
//            if ( buf.length() == 0 )
//            {
//                if ( isWhitespace )
//                    continue;
//            }
//            
//            if ( isWhitespace )
//                isDone = true;
//            else
//                buf.append( c );
//        }
//        
//        String proposedMethod = buf.toString();
//        log.debug( "Proposed method=\"{}\".", proposedMethod );
//        HttpMethod method = HttpMethod.valueOf( proposedMethod );
//        
//        return method;
//    }

}
