package com.sibilantsolutions.iplayers.layer.app.tls.domain;


public class StatusRequestExtension implements ExtensionI
{

    private String data;

    @Override
    public String build()
    {
        return data;
    }

    public String getData()
    {
        return data;
    }

    public void setData( String data )
    {
        this.data = data;
    }

    @Override
    public Extension getExtensionType()
    {
        return Extension.status_request;
    }

    public static StatusRequestExtension parse( String data )
    {
        StatusRequestExtension ext = new StatusRequestExtension();

        ext.data = data;

        return ext;
    }

}
