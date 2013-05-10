package com.sibilantsolutions.iplayers.layer.app.irc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.iptools.event.ReceiveEvt;
import com.sibilantsolutions.iptools.event.SocketListenerI;
import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcLine;
import com.sibilantsolutions.iptools.util.Socker;

public class IrcClient
{
    final static private Logger log = LoggerFactory.getLogger( IrcClient.class );
    
    final static public String USER_NICK = "user.nick";
    final static public String USER_USERNAME = "user.username";
    final static public String USER_REALNAME = "user.realname";
    final static public String HOST_NAME = "host.name";
    final static public String HOST_PORT = "host.port";

    final static public String CRLF = "\r\n";

    public IrcClient( Properties properties )
    {
        String userNick = properties.getProperty( USER_NICK );
        String userUsername = properties.getProperty( USER_USERNAME );
        String userRealName = properties.getProperty( USER_REALNAME );
        String hostName = properties.getProperty( HOST_NAME );
        int hostPort = Integer.parseInt( properties.getProperty( HOST_PORT ) );

        Socket socket;
        try
        {
            log.info( "Connecting to host={}:{}.", hostName, hostPort );
            socket = new Socket( hostName, hostPort );
            log.info( "Connected to host={}.", socket );
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        Socker.send( "NICK " + userNick + CRLF, socket );
        Socker.send( "USER " + userUsername + ' ' + userUsername + ' ' + hostName + " :" + userRealName + CRLF, socket );
        
        SocketListenerI l = new SocketListenerI() {
            
            @Override
            public void onReceive( ReceiveEvt evt )
            {
                doReceive( evt );
            }
        };
        Socker.readLoopThread( socket, l );
    }

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

}
