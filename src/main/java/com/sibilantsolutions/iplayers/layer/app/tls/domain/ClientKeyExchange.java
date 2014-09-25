package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.nio.ByteBuffer;

public class ClientKeyExchange implements HandshakeMessageI
{

    private byte[] encryptedPreMaster;

    @Override
    public byte[] toDatastream()
    {
        ByteBuffer bb = ByteBuffer.allocate( 2 + encryptedPreMaster.length );

        bb.putChar( (char)encryptedPreMaster.length );
        bb.put( encryptedPreMaster );

        return bb.array();
    }

    public byte[] getEncryptedPreMaster()
    {
        return encryptedPreMaster;
    }

    public void setEncryptedPreMaster( byte[] encryptedPreMaster )
    {
        this.encryptedPreMaster = encryptedPreMaster;
    }

    public static ClientKeyExchange parse( byte[] data, int offset, int length )
    {
        ClientKeyExchange cke = new ClientKeyExchange();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

        int len = bb.getChar();

        byte[] epm = new byte[len];
        bb.get( epm );

        cke.encryptedPreMaster = epm;

        return cke;
    }

}
