package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum CompressionMethod
{
    Null( Values.Null );

    static private interface Values
    {
        final static public int Null = 0;
    }

    final private int value;

    private CompressionMethod( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    static public CompressionMethod fromValue( int value )
    {
        switch( value )
        {
            case Values.Null:
                return Null;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

}
