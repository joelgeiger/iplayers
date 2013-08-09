package com.sibilantsolutions.iplayers.layer.app.irc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcAddress;
import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcLine;

public class IrcLineParser
{
    final static private Logger log = LoggerFactory.getLogger( IrcLineParser.class );

    public static IrcLine parse( String line )
    {
        String prefix = null;
        String command = null;
        String params = null;

        int offset = 0;

        if ( line.charAt( 0 ) == ':' )
        {
            int space = line.indexOf( ' ' );
            prefix = line.substring( 1, space );
            offset = space + 1;
        }

        offset = skipSpaces( line, offset );

        int space = line.indexOf( ' ', offset );

        command = line.substring( offset, space );

        offset = skipSpaces( line, space + 1 );

        params = line.substring( offset );

        IrcLine ircLine = new IrcLine();
        ircLine.setPrefix( nullIfEmpty( prefix ) );
        ircLine.setCommand( nullIfEmpty( command ) );
        ircLine.setParameters( nullIfEmpty( params ) );

        log.debug( "Parsed line={}", ircLine );

        return ircLine;
    }

    /*package*/ public static IrcAddress parseAddress( String addr )
    {
        final char userSep = '!';
        final char hostSep = '@';

        int uIndex = addr.indexOf( userSep );
        int hIndex = addr.indexOf( hostSep );

        String server = null;
        String nick = null;
        String user = null;
        String host = null;

        if ( uIndex > -1 )
        {
            nick = addr.substring( 0, uIndex );

            if ( hIndex > -1 )
            {
                user = addr.substring( uIndex + 1, hIndex );
                host = addr.substring( hIndex + 1 );
            }
            else
            {
                user = addr.substring( uIndex + 1 );
            }
        }
        else
        {
            server = addr;
        }

        IrcAddress ia = new IrcAddress();
        ia.setServer( server );
        ia.setNick( nick );
        ia.setUser( user );
        ia.setHost( host );

        return ia;
    }

    static private String nullIfEmpty( String str )
    {
        if ( str == null || str.trim().length() == 0 )
            return null;
        else
            return str;
    }

    static private int skipSpaces( String str, int offset )
    {
        for ( ; str.charAt( offset ) == ' '; offset++ ) { /* No-op. */ }

        return offset;
    }

}
