package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum CompressionMethod
{
    Null( Values.Null );

    static private interface Values
    {
        final static public char Null = 0;
    }

    final private char value;

    private CompressionMethod( char value )
    {
        this.value = value;
    }

    public char getValue()
    {
        return value;
    }

    static public CompressionMethod fromValue( char value )
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
