package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.nio.ByteBuffer;

public class EcPointFormatsExtension implements ExtensionI
{

    private byte[] data;

    @Override
    public byte[] toDatastream()
    {
        ByteBuffer bb = ByteBuffer.allocate( 1 + data.length );

        bb.put( (byte)data.length );
        bb.put( data );

        return bb.array();
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData( byte[] data )
    {
        this.data = data;
    }

    @Override
    public Extension getExtensionType()
    {
        return Extension.ec_point_formats;
    }

    public static EcPointFormatsExtension parse( byte[] data, int offset, int length )
    {
        EcPointFormatsExtension ext = new EcPointFormatsExtension();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

        int ecLength = bb.get();
        byte[] d = new byte[ecLength];
        bb.get( d );
        ext.data = d;

        return ext;
    }

}
