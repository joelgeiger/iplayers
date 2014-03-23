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
        final static private int HelloRequest       = 0;    //0x00
        final static private int ClientHello        = 1;    //0x01
        final static private int ServerHello        = 2;    //0x02
        final static private int NewSessionTicket   = 4;    //0x04
        final static private int Certificate        = 11;   //0x0B
        final static private int ServerKeyExchange  = 12;   //0x0C
        final static private int CertificateRequest = 13;   //0x0D
        final static private int ServerHelloDone    = 14;   //0x0E
        final static private int CertificateVerify  = 15;   //0x0F
        final static private int ClientKeyExchange  = 16;   //0x10
        final static private int Finished           = 20;   //0x14
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
