package com.sibilantsolutions.iplayers.layer.app.irc.command;

import com.sibilantsolutions.iptools.event.ReceiveEvt;
import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcLine;

public class CommandContext
{

    private IrcLine ircLine;
    private ReceiveEvt receiveEvt;

    public CommandContext( IrcLine ircLine, ReceiveEvt receiveEvt )
    {
        this.ircLine = ircLine;
        this.receiveEvt = receiveEvt;
    }

    public IrcLine getIrcLine()
    {
        return ircLine;
    }

    public ReceiveEvt getReceiveEvt()
    {
        return receiveEvt;
    }

}
