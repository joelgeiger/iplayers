package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class ServerHello implements HandshakeMessageI
{

    @Override
    public String build()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

    public static ServerHello parse( String data )
    {
        return new ServerHello();   //TODO
    }

}
