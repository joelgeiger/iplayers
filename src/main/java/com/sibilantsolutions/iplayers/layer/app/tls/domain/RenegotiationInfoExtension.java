package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.nio.ByteBuffer;

public class RenegotiationInfoExtension implements ExtensionI
{

    private byte[] data = new byte[0];

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
        return Extension.renegotiation_info;
    }

    public static RenegotiationInfoExtension parse( byte[] data, int offset, int length )
    {
        RenegotiationInfoExtension ext = new RenegotiationInfoExtension();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

            //TODO: Finish parsing; add getters.
        int l = bb.get();
        byte[] d = new byte[l];
        bb.get( d );
        ext.data = d;

        return ext;
    }

}
