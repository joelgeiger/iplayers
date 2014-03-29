package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class ServerNameExtension implements ExtensionI
{
    private int serverNameType; //TODO: This should be a enum, I think.
    private String serverName;

    public int getServerNameType()
    {
        return serverNameType;
    }

    public String getServerName()
    {
        return serverName;
    }

    public static ServerNameExtension parse( String data )
    {
        ServerNameExtension sne = new ServerNameExtension();

        int i = 0;

            //TODO: This extension appears to be meant to be a list; our parsing is incomplete.
        int listLength = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
        sne.serverNameType = data.charAt( i++ );
        int serverNameLength = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
        sne.serverName = data.substring( i, i + serverNameLength );
        i += serverNameLength;

        return sne;
    }

}
