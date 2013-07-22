package com.sibilantsolutions.iplayers.layer.app.irc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.iplayers.layer.app.irc.command.CommandContext;

public class LogServerStatusImpl implements ServerStatusI
{
    final static private Logger log = LoggerFactory.getLogger( LogServerStatusImpl.class );

    @Override
    public void execute( CommandContext ctx )
    {
        String params = ctx.getIrcLine().getParameters();
        int colon = params.indexOf( ':' );
        if ( colon > -1 )
        {
            params = params.substring( colon + 1 );
        }
        log.info( params );
    }

}
