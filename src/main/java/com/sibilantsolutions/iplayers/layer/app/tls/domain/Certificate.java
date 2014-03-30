package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class Certificate implements HandshakeMessageI
{

    @Override
    public String build()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

    public static Certificate parse( String data )
    {
        return new Certificate();   //TODO
    }

}
