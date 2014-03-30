package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum HandshakeMessageType
{
    HelloRequest( Values.HelloRequest ),
    ClientHello( Values.ClientHello ),
    ServerHello( Values.ServerHello ),
    NewSessionTicket( Values.NewSessionTicket ),
    Certificate( Values.Certificate ),
    ServerKeyExchange( Values.ServerKeyExchange ),
    CertificateRequest( Values.CertificateRequest ),
    ServerHelloDone( Values.ServerHelloDone ),
    CertificateVerify( Values.CertificateVerify ),
    ClientKeyExchange( Values.ClientKeyExchange ),
    Finished( Values.Finished );

    static private interface Values
    {
        final static public char HelloRequest       = 0;    //0x00
        final static public char ClientHello        = 1;    //0x01
        final static public char ServerHello        = 2;    //0x02
        final static public char NewSessionTicket   = 4;    //0x04
        final static public char Certificate        = 11;   //0x0B
        final static public char ServerKeyExchange  = 12;   //0x0C
        final static public char CertificateRequest = 13;   //0x0D
        final static public char ServerHelloDone    = 14;   //0x0E
        final static public char CertificateVerify  = 15;   //0x0F
        final static public char ClientKeyExchange  = 16;   //0x10
        final static public char Finished           = 20;   //0x14
    }

    private char value;

    private HandshakeMessageType( char value )
    {
        this.value = value;
    }

    public char getValue()
    {
        return value;
    }

    static public HandshakeMessageType fromValue( char value )
    {
        switch( value )
        {
            case Values.HelloRequest:
                return HelloRequest;

            case Values.ClientHello:
                return ClientHello;

            case Values.ServerHello:
                return ServerHello;

            case Values.NewSessionTicket:
                return NewSessionTicket;

            case Values.Certificate:
                return Certificate;

            case Values.ServerKeyExchange:
                return ServerKeyExchange;

            case Values.CertificateRequest:
                return CertificateRequest;

            case Values.ServerHelloDone:
                return ServerHelloDone;

            case Values.CertificateVerify:
                return CertificateVerify;

            case Values.ClientKeyExchange:
                return ClientKeyExchange;

            case Values.Finished:
                return Finished;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

}
