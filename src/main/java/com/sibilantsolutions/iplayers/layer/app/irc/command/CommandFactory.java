package com.sibilantsolutions.iplayers.layer.app.irc.command;

import com.sibilantsolutions.iplayers.layer.app.irc.ServerStatusI;

public class CommandFactory
{
    private ServerStatusI serverStatus;

    public Command buildCommand( String commandName )
    {
        switch ( commandName )
        {
            case "001":
            case "002":
            case "003":
            case "004":
            case "005":
            case "042":
            case "251":
            case "252":
            case "253":
            case "254":
            case "255":
            case "265":
            case "266":
            case "372":
            case "375":
            case "376":
            case "396":
            case "ERROR":
            case "MODE":
            case "NOTICE":
                return new ServerStatusCommand( serverStatus );


            case "433":
            case "451":
                return new ServerStatusCommand( serverStatus );

            case "353":
            case "366":
                return new ServerStatusCommand( serverStatus );

            case "JOIN":
            case "PRIVMSG":
                return new ServerStatusCommand( serverStatus );

            case "PING":
                return new CMD_PING();

            default:
                throw new IllegalArgumentException( "Unexpected command=" + commandName );
        }
    }

    public ServerStatusI getServerStatus()
    {
        return serverStatus;
    }

    public void setServerStatus( ServerStatusI serverStatus )
    {
        this.serverStatus = serverStatus;
    }

}
