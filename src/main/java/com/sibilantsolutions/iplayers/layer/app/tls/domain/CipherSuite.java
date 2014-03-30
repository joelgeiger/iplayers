package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum CipherSuite
{
    TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256( Values.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 ),
    TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256( Values.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 ),
    TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA( Values.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA ),
    TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA( Values.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA ),
    TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA( Values.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA ),
    TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA( Values.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA ),
    TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA( Values.TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA ),
    TLS_ECDHE_ECDSA_WITH_RC4_128_SHA( Values.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA ),
    TLS_ECDHE_RSA_WITH_RC4_128_SHA( Values.TLS_ECDHE_RSA_WITH_RC4_128_SHA ),
    TLS_DHE_RSA_WITH_AES_128_CBC_SHA( Values.TLS_DHE_RSA_WITH_AES_128_CBC_SHA ),
    TLS_DHE_DSS_WITH_AES_128_CBC_SHA( Values.TLS_DHE_DSS_WITH_AES_128_CBC_SHA ),
    TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA( Values.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA ),
    TLS_DHE_RSA_WITH_AES_256_CBC_SHA( Values.TLS_DHE_RSA_WITH_AES_256_CBC_SHA ),
    TLS_DHE_DSS_WITH_AES_256_CBC_SHA( Values.TLS_DHE_DSS_WITH_AES_256_CBC_SHA ),
    TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA( Values.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA ),
    TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA( Values.TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA ),
    TLS_RSA_WITH_AES_128_CBC_SHA( Values.TLS_RSA_WITH_AES_128_CBC_SHA ),
    TLS_RSA_WITH_CAMELLIA_128_CBC_SHA( Values.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA ),
    TLS_RSA_WITH_AES_256_CBC_SHA( Values.TLS_RSA_WITH_AES_256_CBC_SHA ),
    TLS_RSA_WITH_CAMELLIA_256_CBC_SHA( Values.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA ),
    TLS_RSA_WITH_3DES_EDE_CBC_SHA( Values.TLS_RSA_WITH_3DES_EDE_CBC_SHA ),
    TLS_RSA_WITH_RC4_128_SHA( Values.TLS_RSA_WITH_RC4_128_SHA ),
    TLS_RSA_WITH_RC4_128_MD5( Values.TLS_RSA_WITH_RC4_128_MD5 );

    static private interface Values
    {
        final static public int TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 = 0xc02b;
        final static public int TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 = 0xc02f;
        final static public int TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA = 0xc00a;
        final static public int TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA = 0xc009;
        final static public int TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA = 0xc013;
        final static public int TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA = 0xc014;
        final static public int TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA = 0xc012;
        final static public int TLS_ECDHE_ECDSA_WITH_RC4_128_SHA = 0xc007;
        final static public int TLS_ECDHE_RSA_WITH_RC4_128_SHA = 0xc011;
        final static public int TLS_DHE_RSA_WITH_AES_128_CBC_SHA = 0x0033;
        final static public int TLS_DHE_DSS_WITH_AES_128_CBC_SHA = 0x0032;
        final static public int TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA = 0x0045;
        final static public int TLS_DHE_RSA_WITH_AES_256_CBC_SHA = 0x0039;
        final static public int TLS_DHE_DSS_WITH_AES_256_CBC_SHA = 0x0038;
        final static public int TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA = 0x0088;
        final static public int TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA = 0x0016;
        final static public int TLS_RSA_WITH_AES_128_CBC_SHA = 0x002f;
        final static public int TLS_RSA_WITH_CAMELLIA_128_CBC_SHA = 0x0041;
        final static public int TLS_RSA_WITH_AES_256_CBC_SHA = 0x0035;
        final static public int TLS_RSA_WITH_CAMELLIA_256_CBC_SHA = 0x0084;
        final static public int TLS_RSA_WITH_3DES_EDE_CBC_SHA = 0x000a;
        final static public int TLS_RSA_WITH_RC4_128_SHA = 0x0005;
        final static public int TLS_RSA_WITH_RC4_128_MD5 = 0x0004;
    }

    private int value;

    private CipherSuite( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    static public CipherSuite fromValue( int value )
    {
        switch( value )
        {
            case Values.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256:
                return TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256;
            case Values.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256:
                return TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256;
            case Values.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA:
                return TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA;
            case Values.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA:
                return TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA;
            case Values.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA:
                return TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA;
            case Values.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA:
                return TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA;
            case Values.TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA:
                return TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA;
            case Values.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA:
                return TLS_ECDHE_ECDSA_WITH_RC4_128_SHA;
            case Values.TLS_ECDHE_RSA_WITH_RC4_128_SHA:
                return TLS_ECDHE_RSA_WITH_RC4_128_SHA;
            case Values.TLS_DHE_RSA_WITH_AES_128_CBC_SHA:
                return TLS_DHE_RSA_WITH_AES_128_CBC_SHA;
            case Values.TLS_DHE_DSS_WITH_AES_128_CBC_SHA:
                return TLS_DHE_DSS_WITH_AES_128_CBC_SHA;
            case Values.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA:
                return TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA;
            case Values.TLS_DHE_RSA_WITH_AES_256_CBC_SHA:
                return TLS_DHE_RSA_WITH_AES_256_CBC_SHA;
            case Values.TLS_DHE_DSS_WITH_AES_256_CBC_SHA:
                return TLS_DHE_DSS_WITH_AES_256_CBC_SHA;
            case Values.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA:
                return TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA;
            case Values.TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA:
                return TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA;
            case Values.TLS_RSA_WITH_AES_128_CBC_SHA:
                return TLS_RSA_WITH_AES_128_CBC_SHA;
            case Values.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA:
                return TLS_RSA_WITH_CAMELLIA_128_CBC_SHA;
            case Values.TLS_RSA_WITH_AES_256_CBC_SHA:
                return TLS_RSA_WITH_AES_256_CBC_SHA;
            case Values.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA:
                return TLS_RSA_WITH_CAMELLIA_256_CBC_SHA;
            case Values.TLS_RSA_WITH_3DES_EDE_CBC_SHA:
                return TLS_RSA_WITH_3DES_EDE_CBC_SHA;
            case Values.TLS_RSA_WITH_RC4_128_SHA:
                return TLS_RSA_WITH_RC4_128_SHA;
            case Values.TLS_RSA_WITH_RC4_128_MD5:
                return TLS_RSA_WITH_RC4_128_MD5;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }
}
