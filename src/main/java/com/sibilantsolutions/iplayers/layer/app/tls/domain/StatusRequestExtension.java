package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class StatusRequestExtension implements ExtensionI
{

    private String data;

    public String getData()
    {
        return data;
    }

    public static StatusRequestExtension parse( String data )
    {
        StatusRequestExtension ext = new StatusRequestExtension();

        ext.data = data;

        return ext;
    }

}