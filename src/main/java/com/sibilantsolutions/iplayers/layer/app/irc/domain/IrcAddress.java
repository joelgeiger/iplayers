package com.sibilantsolutions.iplayers.layer.app.irc.domain;

public class IrcAddress
{

    public String server;
    public String nick;
    public String user;
    public String host;

    public String getServer()
    {
        return server;
    }
    public void setServer( String server )
    {
        this.server = server;
    }
    public String getNick()
    {
        return nick;
    }
    public void setNick( String nick )
    {
        this.nick = nick;
    }
    public String getUser()
    {
        return user;
    }
    public void setUser( String user )
    {
        this.user = user;
    }
    public String getHost()
    {
        return host;
    }
    public void setHost( String host )
    {
        this.host = host;
    }

}
