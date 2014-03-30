package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import com.sibilantsolutions.iptools.util.HexUtils;

public class HandshakeProtocol extends ProtocolMessage
{
    private HandshakeMessageType handshakeMessageType;
    private int length; //This is a 3-byte field indicating the length of the handshake data, not including the header.
    private HandshakeMessageI data;

    public HandshakeMessageType getHandshakeMessageType()
    {
        return handshakeMessageType;
    }

    public int getLength()
    {
        return length;
    }

    public HandshakeMessageI getData()
    {
        return data;
    }

    public void setHandshakeMessageType( HandshakeMessageType handshakeMessageType )
    {
        this.handshakeMessageType = handshakeMessageType;
    }

    public void setData( HandshakeMessageI data )
    {
        this.data = data;
    }

    public static HandshakeProtocol parse( String str )
    {
        HandshakeProtocol h = new HandshakeProtocol();

        int i = 0;

        h.handshakeMessageType = HandshakeMessageType.fromValue( str.charAt( i++ ) );
        h.length = str.charAt( i++ ) * 0x010000 + str.charAt( i++ ) * 0x0100 + str.charAt( i++ );

        String hmiData = str.substring( i, i + h.length );

        h.data = h.handshakeMessageType.parse( hmiData );

        return h;
    }

    @Override
    public String build()
    {
        StringBuilder buf = new StringBuilder();

        buf.append( handshakeMessageType.getValue() );
        String hmiData = data.build();
        buf.append( HexUtils.encodeNum( hmiData.length(), 3 ) );
        buf.append( hmiData );

        return buf.toString();
    }

}
