package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum EllipticCurve
{
    secp256r1( Values.secp256r1 ),
    secp384r1( Values.secp384r1 ),
    secp521r1( Values.secp521r1 );

    static private interface Values
    {
        final static public int secp256r1 = 0x0017;
        final static public int secp384r1 = 0x0018;
        final static public int secp521r1 = 0x0019;
    }

    private int value;

    private EllipticCurve( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public static EllipticCurve fromValue( int value )
    {
        switch( value )
        {
            case Values.secp256r1:
                return secp256r1;
            case Values.secp384r1:
                return secp384r1;
            case Values.secp521r1:
                return secp521r1;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

}
