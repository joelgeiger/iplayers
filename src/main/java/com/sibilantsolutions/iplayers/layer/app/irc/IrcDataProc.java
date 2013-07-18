package com.sibilantsolutions.iplayers.layer.app.irc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.iptools.event.ReceiveEvt;
import com.sibilantsolutions.iptools.event.SocketListenerI;
import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcLine;

public class IrcDataProc implements SocketListenerI
{
    final static private Logger log = LoggerFactory.getLogger( IrcDataProc.class );

    @Override
    public void onReceive( ReceiveEvt evt )
    {
            //TODO: Make sure that this uses the correct encoding.
        String line = new String( evt.getData(), evt.getOffset(), evt.getLength() );

        IrcLine ircLine = IrcLineParser.parse( line );
        //log.info( "prefix={}, command={}, parameters={}", ircLine.getPrefix(), ircLine.getCommand(), ircLine.getParameters() );

        try
        {
            Commands command = Commands.valueOf( "CMD_" + ircLine.getCommand() );
            //log.info( "Command={}.", command );
        }
        catch ( IllegalArgumentException e )
        {
            log.error( "Unexpected command=" + ircLine.getCommand(), e );
        }

    }
}
