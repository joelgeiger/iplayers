package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum Version
{
    SSL_3_0( Values.MAJOR, Values.SSL_3_0_MINOR ),    //SSL 3.0
    TLS_1_0( Values.MAJOR, Values.TLS_1_0_MINOR ),    //TLS 1.0
    TLS_1_1( Values.MAJOR, Values.TLS_1_1_MINOR ),    //TLS 1.1
    TLS_1_2( Values.MAJOR, Values.TLS_1_2_MINOR );    //TLS 1.2

    static private interface Values
    {
        final static public byte MAJOR = 3;

        final static public byte SSL_3_0_MINOR = 0;
        final static public byte TLS_1_0_MINOR = 1;
        final static public byte TLS_1_1_MINOR = 2;
        final static public byte TLS_1_2_MINOR = 3;
    }

    final private byte major;
    final private byte minor;

    private Version( byte major, byte minor )
    {
        this.major = major;
        this.minor = minor;
    }

    static public Version fromValue( byte major, byte minor )
    {
        switch( major )
        {
            case Values.MAJOR:
            {
                switch( minor )
                {
                    case Values.SSL_3_0_MINOR:
                        return SSL_3_0;

                    case Values.TLS_1_0_MINOR:
                        return TLS_1_0;

                    case Values.TLS_1_1_MINOR:
                        return TLS_1_1;

                    case Values.TLS_1_2_MINOR:
                        return TLS_1_2;

                    default:
                        throw new IllegalArgumentException( "Unexpected minor version=" + minor );
                }
            }

            default:
                throw new IllegalArgumentException( "Unexpected major version=" + major );
        }
    }

    public byte getMajor()
    {
        return major;
    }

    public byte getMinor()
    {
        return minor;
    }

}
