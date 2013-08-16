package com.sibilantsolutions.iplayers.layer.app.irc;

import java.net.Socket;
import java.util.Properties;

import com.sibilantsolutions.iplayers.layer.app.irc.command.CommandFactory;
import com.sibilantsolutions.iptools.util.LineParserBuffer;
import com.sibilantsolutions.iptools.util.Socker;

public class IrcClient
{
//    final static private Logger log = LoggerFactory.getLogger( IrcClient.class );

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

        Socket socket = Socker.connect( hostName, hostPort );

        Socker.send( "NICK " + userNick + CRLF, socket );
        Socker.send( "USER " + userUsername + ' ' + userUsername + ' ' + hostName + " :" + userRealName + CRLF, socket );

        LineParserBuffer listener = new LineParserBuffer();
        IrcDataProc idp = new IrcDataProc();
        CommandFactory cf = new CommandFactory();
        cf.setServerStatus( new LogServerStatusImpl() );
        idp.setCommandFactory( cf );
        listener.setReceiver( idp );
        Socker.readLoopThread( socket, listener );

//        log.info( "Sleeping..." );
//        try
//        {
//            Thread.sleep( 5 * 1000 );
//        }
//        catch ( InterruptedException ignored )
//        {
//        }
//        log.info( "...Awake." );
//
//        final String CHANNEL = "#mytest123abc";
//        Socker.send( "JOIN " + CHANNEL + CRLF, socket );
//
//
//        log.info( "Sleeping..." );
//        try
//        {
//            Thread.sleep( 5 * 1000 );
//        }
//        catch ( InterruptedException ignored )
//        {
//        }
//        log.info( "...Awake." );
//
//        String msg = "Hello, how are you?";
//        Socker.send( "PRIVMSG " + CHANNEL + " :" + msg + CRLF, socket );
//
//
//        log.info( "Sleeping..." );
//        try
//        {
//            Thread.sleep( 5 * 1000 );
//        }
//        catch ( InterruptedException ignored )
//        {
//        }
//        log.info( "...Awake." );
//
//        Socker.send( "PRIVMSG " + "unicrown`" + " :" + "Frogger." + CRLF, socket );

    }

}
