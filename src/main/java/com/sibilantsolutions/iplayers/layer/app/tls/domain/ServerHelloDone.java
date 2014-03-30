package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class ServerHelloDone implements HandshakeMessageI
{

    @Override
    public String build()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

    public static ServerHelloDone parse( String data )
    {
        return new ServerHelloDone();   //TODO
    }

}
