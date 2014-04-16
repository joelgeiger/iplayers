package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import com.sibilantsolutions.iptools.util.HexUtils;

public class ClientKeyExchange implements HandshakeMessageI
{

    private String encryptedPreMaster;

    @Override
    public String build()
    {
        StringBuilder buf = new StringBuilder();

        buf.append( HexUtils.encodeNum( encryptedPreMaster.length(), 2 ) );
        buf.append( encryptedPreMaster );

        return buf.toString();
    }

    public String getEncryptedPreMaster()
    {
        return encryptedPreMaster;
    }

    public void setEncryptedPreMaster( String encryptedPreMaster )
    {
        this.encryptedPreMaster = encryptedPreMaster;
    }

    public static ClientKeyExchange parse( String data )
    {
        ClientKeyExchange cke = new ClientKeyExchange();

        int i = 0;

        int len = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );

        cke.encryptedPreMaster = data.substring( i, i + len );
        i += len;

        return cke;
    }

}
