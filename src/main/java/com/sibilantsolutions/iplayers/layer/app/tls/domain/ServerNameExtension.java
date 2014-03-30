package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import com.sibilantsolutions.iptools.util.HexUtils;

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

    public void setServerNameType( int serverNameType )
    {
        this.serverNameType = serverNameType;
    }

    public void setServerName( String serverName )
    {
        this.serverName = serverName;
    }

    @Override
    public String build()
    {
        StringBuilder buf = new StringBuilder();

            //serverNameType + serverName length bytes + length of serverName
        buf.append( HexUtils.encodeNum( 1 + 2 + serverName.length(), 2 ) );

        buf.append( HexUtils.encodeNum( serverNameType, 1 ) );
        buf.append( HexUtils.encodeNum( serverName.length(), 2 ) );
        buf.append( serverName );

        return buf.toString();
    }

}
