package com.sibilantsolutions.iplayers.layer.app.irc;

import com.sibilantsolutions.iptools.event.ReceiveEvt;
import com.sibilantsolutions.iptools.event.SocketListenerI;
import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcLine;

public class IrcDataProc implements SocketListenerI
{

    @Override
    public void onReceive( ReceiveEvt evt )
    {
            //TODO: Make sure that this uses the correct encoding.
        String line = new String( evt.getData(), evt.getOffset(), evt.getLength() );

        IrcLine ircLine = IrcLineParser.parse( line );
    }
}
