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

    static private class Values
    {
        final static private int HelloRequest       = 0;
        final static private int ClientHello        = 1;
        final static private int ServerHello        = 2;
        final static private int NewSessionTicket   = 4;
        final static private int Certificate        = 11;
        final static private int ServerKeyExchange  = 12;
        final static private int CertificateRequest = 13;
        final static private int ServerHelloDone    = 14;
        final static private int CertificateVerify  = 15;
        final static private int ClientKeyExchange  = 16;
        final static private int Finished           = 20;
    }

    private int value;

    private HandshakeMessageType( int value )
    {
        this.value = value;
    }

    static public HandshakeMessageType valueOf( int value )
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
