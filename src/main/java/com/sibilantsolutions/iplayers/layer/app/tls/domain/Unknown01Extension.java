package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class Unknown01Extension implements ExtensionI
{

    private String data;

    public String getData()
    {
        return data;
    }

    @Override
    public Extension getExtensionType()
    {
        return Extension.unknown01;
    }

    public static Unknown01Extension parse( String data )
    {
        Unknown01Extension ext = new Unknown01Extension();

        ext.data = data;

        return ext;
    }

    @Override
    public String build()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

}
