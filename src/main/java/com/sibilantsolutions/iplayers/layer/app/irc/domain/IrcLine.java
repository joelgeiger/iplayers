package com.sibilantsolutions.iplayers.layer.app.irc.domain;

public class IrcLine
{

    private String prefix;
    private String command;
    private String parameters;

    public String getPrefix()
    {
        return prefix;
    }
    public void setPrefix( String prefix )
    {
        this.prefix = prefix;
    }
    public String getCommand()
    {
        return command;
    }
    public void setCommand( String command )
    {
        this.command = command;
    }
    public String getParameters()
    {
        return parameters;
    }
    public void setParameters( String parameters )
    {
        this.parameters = parameters;
    }

    @Override
    public String toString()
    {
        return "PREFIX=\"" + prefix + "\", COMMAND=\"" + command + "\", PARAMETERS=\"" + parameters + "\"";
    }
}
