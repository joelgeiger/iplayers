package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.nio.ByteBuffer;

import com.sibilantsolutions.utils.util.Convert;
import com.sibilantsolutions.utils.util.HexDump;
import com.sibilantsolutions.utils.util.HexUtils;

public class HandshakeProtocol extends ProtocolMessage
{
    private HandshakeMessageType handshakeMessageType;
    //private int length; //This is a 3-byte field indicating the length of the handshake data, not including the header.
    private HandshakeMessageI data;

    public HandshakeMessageType getHandshakeMessageType()
    {
        return handshakeMessageType;
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

    public static HandshakeProtocol parse( byte[] data, int offset, int l )
    {
        HandshakeProtocol h = new HandshakeProtocol();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, l );

        h.handshakeMessageType = HandshakeMessageType.fromValue( bb.get() );
        int length = (int)Convert.toNum( bb, 3 );

        //String hmiData = str.substring( i, i + length );
        byte[] hmiData = new byte[length];
        bb.get( hmiData );

        h.data = h.handshakeMessageType.parse( hmiData, 0, hmiData.length );

        return h;
    }

    @Override
    public byte[] toDatastream()
    {
        byte[] hmiData = data.toDatastream();

        ByteBuffer bb = ByteBuffer.allocate( 4 + hmiData.length );

        bb.put( handshakeMessageType.getValue() );
        String encodeNum = HexUtils.encodeNum( hmiData.length, 3 );
        byte[] lengthBytes = encodeNum.getBytes( HexDump.cs );
        bb.put( lengthBytes );
        bb.put( hmiData );

        return bb.array();
    }

}
