package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class ClientHello
{

    private Version version;
    private Random random;

    public Random getRandom()
    {
        return random;
    }

    public Version getVersion()
    {
        return version;
    }

    public static ClientHello parse( String data )
    {
        ClientHello ch = new ClientHello();

        int i = 0;

        ch.version = Version.valueOf( data.charAt( i++ ), data.charAt( i++ ) );
        final int RANDOM_LENGTH = 32;
        ch.random = Random.parse( data.substring( i, i + RANDOM_LENGTH ) );
        i += RANDOM_LENGTH;

        return ch;
    }

}
