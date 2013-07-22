package com.sibilantsolutions.iplayers.layer.app.irc.command;

import com.sibilantsolutions.iplayers.layer.app.irc.ServerStatusI;

public class ServerStatusCommand implements Command
{

    private ServerStatusI serverStatus;

    public ServerStatusCommand( ServerStatusI serverStatus )
    {
        this.serverStatus = serverStatus;
    }

    @Override
    public void execute( CommandContext ctx )
    {
        serverStatus.execute( ctx );
    }

}
