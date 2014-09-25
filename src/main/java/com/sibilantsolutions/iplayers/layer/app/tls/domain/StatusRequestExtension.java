package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class StatusRequestExtension implements ExtensionI
{

    private byte[] data;

    @Override
    public byte[] toDatastream()
    {
        return data;
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
        return Extension.status_request;
    }

    public static StatusRequestExtension parse( byte[] data, int offset, int length )
    {
        StatusRequestExtension ext = new StatusRequestExtension();

        byte[] d = new byte[length];
        System.arraycopy( data, offset, d, 0, length );
        ext.data = d;

        return ext;
    }

}
