package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class Unknown01Extension implements ExtensionI
{

    private byte[] data;

    public byte[] getData()
    {
        return data;
    }

    @Override
    public Extension getExtensionType()
    {
        return Extension.unknown01;
    }

    public static Unknown01Extension parse( byte[] data, int offset, int length )
    {
        Unknown01Extension ext = new Unknown01Extension();

        byte[] d = new byte[length];
        System.arraycopy( data, offset, d, 0, length );
        ext.data = d;

        return ext;
    }

    @Override
    public byte[] toDatastream()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

}
