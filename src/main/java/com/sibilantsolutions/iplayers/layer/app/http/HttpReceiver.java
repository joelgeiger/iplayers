package com.sibilantsolutions.iplayers.layer.app.http;

import java.nio.charset.Charset;

import com.sibilantsolutions.iptools.event.ReceiveEvt;
import com.sibilantsolutions.iptools.event.SocketListenerI;

public class HttpReceiver implements SocketListenerI
{

    @Override
    public void onReceive( ReceiveEvt evt )
    {
        Charset cs = Charset.forName( "US-ASCII" );
        String s = new String( evt.getData(), evt.getOffset(), evt.getLength(), cs );
        RequestLine requestLine = RequestLine.parse( s );
        
        throw new UnsupportedOperationException( "TODO" );
    }

}
