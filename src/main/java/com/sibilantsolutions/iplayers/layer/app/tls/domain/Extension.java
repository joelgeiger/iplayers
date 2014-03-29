package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum Extension
{
    server_name( Values.server_name );

    final static private class Values
    {
        final static private int server_name = 0x0000;

    }

    final private int value;

    private Extension( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    static public Extension fromValue( int value )
    {
        switch( value )
        {
            case Values.server_name:
                return server_name;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }
}
