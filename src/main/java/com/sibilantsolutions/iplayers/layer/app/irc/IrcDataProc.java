package com.sibilantsolutions.iplayers.layer.app.irc;

import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcLine;

public class IrcDataProc implements IrcDataProcI
{

    @Override
    public void onReceiveLine( String line )
    {
        IrcLine ircLine = IrcLineParser.parse( line );
    }
}
