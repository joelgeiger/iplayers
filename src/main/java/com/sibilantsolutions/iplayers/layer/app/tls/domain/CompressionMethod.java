package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum CompressionMethod
{
    Null( Values.Null );

    final static private class Values
    {
        final static private int Null = 0;
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
