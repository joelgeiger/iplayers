package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class ServerHelloDone implements HandshakeMessageI
{

    private String data;

    @Override
    public String build()
    {
        return data;
    }

    public static ServerHelloDone parse( String data )
    {
        ServerHelloDone serverHelloDone = new ServerHelloDone();

        serverHelloDone.data = data;

        return serverHelloDone;
    }

}
