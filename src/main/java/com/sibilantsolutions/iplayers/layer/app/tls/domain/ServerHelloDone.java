package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class ServerHelloDone implements HandshakeMessageI
{

    private byte[] data;

    @Override
    public byte[] toDatastream()
    {
        return data;
    }

    public static ServerHelloDone parse( byte[] data, int offset, int length )
    {
        ServerHelloDone serverHelloDone = new ServerHelloDone();

        byte[] d = new byte[length];
        System.arraycopy( data, offset, d, 0, length );
        serverHelloDone.data = d;

        return serverHelloDone;
    }

}
