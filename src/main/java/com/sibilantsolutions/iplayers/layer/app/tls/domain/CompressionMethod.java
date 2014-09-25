package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum CompressionMethod
{
    Null( Values.Null );

    static private interface Values
    {
        final static public byte Null = 0;
    }

    final private byte value;

    private CompressionMethod( byte value )
    {
        this.value = value;
    }

    public byte getValue()
    {
        return value;
    }

    static public CompressionMethod fromValue( byte value )
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
