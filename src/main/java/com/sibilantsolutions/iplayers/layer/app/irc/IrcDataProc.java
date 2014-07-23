package com.sibilantsolutions.iplayers.layer.app.irc;

import com.sibilantsolutions.iplayers.layer.app.irc.command.Command;
import com.sibilantsolutions.iplayers.layer.app.irc.command.CommandContext;
import com.sibilantsolutions.iplayers.layer.app.irc.command.CommandFactory;
import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcLine;
import com.sibilantsolutions.iptools.event.LostConnectionEvt;
import com.sibilantsolutions.iptools.event.ReceiveEvt;
import com.sibilantsolutions.iptools.event.SocketListenerI;

public class IrcDataProc implements SocketListenerI
{
    //final static private Logger log = LoggerFactory.getLogger( IrcDataProc.class );

    private CommandFactory commandFactory;

    @Override
    public void onLostConnection( LostConnectionEvt evt )
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

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
/*
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
*/
        Command command = commandFactory.buildCommand( ircLine.getCommand() );

        try
        {
            command.execute( new CommandContext( ircLine, evt ) );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    public CommandFactory getCommandFactory()
    {
        return commandFactory;
    }

    public void setCommandFactory( CommandFactory commandFactory )
    {
        this.commandFactory = commandFactory;
    }

}
