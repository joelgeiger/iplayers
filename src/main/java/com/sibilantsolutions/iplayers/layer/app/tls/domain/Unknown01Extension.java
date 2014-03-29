package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class Unknown01Extension implements ExtensionI
{

    private String data;

    public String getData()
    {
        return data;
    }

    public static Unknown01Extension parse( String data )
    {
        Unknown01Extension ext = new Unknown01Extension();

        ext.data = data;

        return ext;
    }

}
