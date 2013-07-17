package com.sibilantsolutions.iplayers.layer.app.irc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.iptools.event.ReceiveEvt;
import com.sibilantsolutions.iptools.event.SocketListenerI;
import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcLine;

public class IrcReceiver implements SocketListenerI
{
    final static private Logger log = LoggerFactory.getLogger( IrcReceiver.class );

    private void doReceive( ReceiveEvt evt )
    {
        //TODO: Handle partial receives.
        ByteArrayInputStream bis = new ByteArrayInputStream( evt.getData(), evt.getOffset(), evt.getLength() );
        InputStreamReader isr = new InputStreamReader( bis );
        BufferedReader reader = new BufferedReader( isr );

        String line;

        try
        {
            while ( ( line = reader.readLine() ) != null )
            {
                log.info( "The line={}", line );
                IrcLine ircLine = IrcLineParser.parse( line );
            }
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }
    }

    @Override
    public void onReceive( ReceiveEvt evt )
    {
        doReceive( evt );
    }

}
