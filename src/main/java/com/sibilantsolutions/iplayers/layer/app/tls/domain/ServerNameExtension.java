package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.nio.ByteBuffer;

import com.sibilantsolutions.utils.util.HexDump;

public class ServerNameExtension implements ExtensionI
{
    private int serverNameType; //TODO: This should be a enum, I think.
    private String serverName;

    @Override
    public Extension getExtensionType()
    {
        return Extension.server_name;
    }

    public int getServerNameType()
    {
        return serverNameType;
    }

    public String getServerName()
    {
        return serverName;
    }

    public static ServerNameExtension parse( byte[] data, int offset, int length )
    {
        ServerNameExtension sne = new ServerNameExtension();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

            //TODO: This extension appears to be meant to be a list; our parsing is incomplete.
        //int listLength = bb.getChar();
        bb.getChar();   //Skip this byte for now.

        sne.serverNameType = bb.get();
        int serverNameLength = bb.getChar();
        byte[] snld = new byte[serverNameLength];
        bb.get( snld );
        sne.serverName = new String( snld, HexDump.cs );

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
    public byte[] toDatastream()
    {
        byte[] serverNameBytes = serverName.getBytes( HexDump.cs );

            //serverNameType + serverName length bytes + length of serverName
        int len = 2 + 1 + 2 + serverNameBytes.length;
        ByteBuffer bb = ByteBuffer.allocate( len );

        bb.putChar( (char)( len - 2 ) );
        bb.put( (byte)serverNameType );
        bb.putChar( (char)serverNameBytes.length );
        bb.put( serverNameBytes );

        return bb.array();
    }

}
