package com.sibilantsolutions.iplayers.layer.app.irc;

import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcLine;

public class IrcLineParser
{

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

        return ircLine;
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
