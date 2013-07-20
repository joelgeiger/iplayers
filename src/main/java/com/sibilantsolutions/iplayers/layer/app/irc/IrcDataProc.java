package com.sibilantsolutions.iplayers.layer.app.irc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.iptools.event.ReceiveEvt;
import com.sibilantsolutions.iptools.event.SocketListenerI;
import com.sibilantsolutions.iplayers.layer.app.irc.command.Command;
import com.sibilantsolutions.iplayers.layer.app.irc.command.CommandContext;
import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcLine;

public class IrcDataProc implements SocketListenerI
{
    final static private Logger log = LoggerFactory.getLogger( IrcDataProc.class );

    @Override
    public void onReceive( ReceiveEvt evt )
    {
            //TODO: Make sure that this uses the correct encoding.
        String line = new String( evt.getData(), evt.getOffset(), evt.getLength() );

        IrcLine ircLine = IrcLineParser.parse( line );
        //log.info( "prefix={}, command={}, parameters={}", ircLine.getPrefix(), ircLine.getCommand(), ircLine.getParameters() );

/*
        try
        {
            Commands command = Commands.valueOf( "CMD_" + ircLine.getCommand() );
            //log.info( "Command={}.", command );
        }
        catch ( IllegalArgumentException e )
        {
            log.error( "Unexpected command=" + ircLine.getCommand(), e );
        }
*/

            //e.g. "com.sibilantsolutions.iplayers.layer.app.irc.command"
        String commandPackage = Command.class.getPackage().getName();
            //e.g. "com.sibilantsolutions.iplayers.layer.app.irc.command.CMD_ERROR"
        String commandClass = commandPackage + '.' + "CMD_" + ircLine.getCommand();

        try
        {
            Command cmd = (Command)Class.forName( commandClass ).newInstance();
            cmd.execute( new CommandContext( ircLine, evt ) );
        }
        catch ( InstantiationException | IllegalAccessException | ClassNotFoundException e1 )
        {
            // TODO Auto-generated catch block
            //throw new UnsupportedOperationException( "OGTE TODO!", e1 );
        }

    }
}
