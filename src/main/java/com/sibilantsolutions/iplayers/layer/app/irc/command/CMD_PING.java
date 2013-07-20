package com.sibilantsolutions.iplayers.layer.app.irc.command;

import com.sibilantsolutions.iplayers.layer.app.irc.IrcClient;
import com.sibilantsolutions.iptools.util.Socker;

public class CMD_PING implements Command
{

    @Override
    public void execute( CommandContext ctx )
    {
        String response = "PONG " + ctx.getIrcLine().getParameters() + IrcClient.CRLF;

        Socker.send( response, ctx.getReceiveEvt().getSource() );
    }

}
