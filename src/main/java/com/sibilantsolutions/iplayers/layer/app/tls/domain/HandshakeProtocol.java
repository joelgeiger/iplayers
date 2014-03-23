package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class HandshakeProtocol extends ProtocolMessage
{
    private HandshakeMessageType handshakeMessageType;
    private int length; //This is a 3-byte field indicating the length of the handshake data, not including the header.
    private String data;

    public HandshakeMessageType getHandshakeMessageType()
    {
        return handshakeMessageType;
    }

    public int getLength()
    {
        return length;
    }

    public String getData()
    {
        return data;
    }

    public static HandshakeProtocol parse( String str )
    {
        HandshakeProtocol h = new HandshakeProtocol();

        int i = 0;

        h.handshakeMessageType = HandshakeMessageType.valueOf( str.charAt( i++ ) );
        h.length = str.charAt( i++ ) * 0x010000 + str.charAt( i++ ) * 0x0100 + str.charAt( i++ );
        h.data = str.substring( i, i + h.length );

        return h;
    }

}
