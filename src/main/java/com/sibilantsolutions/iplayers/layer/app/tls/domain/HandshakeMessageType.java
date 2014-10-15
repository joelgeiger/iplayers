package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import com.sibilantsolutions.utils.util.HexUtils;

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
        final static public byte HelloRequest       = 0;    //0x00
        final static public byte ClientHello        = 1;    //0x01
        final static public byte ServerHello        = 2;    //0x02
        final static public byte NewSessionTicket   = 4;    //0x04
        final static public byte Certificate        = 11;   //0x0B
        final static public byte ServerKeyExchange  = 12;   //0x0C
        final static public byte CertificateRequest = 13;   //0x0D
        final static public byte ServerHelloDone    = 14;   //0x0E
        final static public byte CertificateVerify  = 15;   //0x0F
        final static public byte ClientKeyExchange  = 16;   //0x10
        final static public byte Finished           = 20;   //0x14
    }

    private byte value;

    private HandshakeMessageType( byte value )
    {
        this.value = value;
    }

    public byte getValue()
    {
        return value;
    }

    static public HandshakeMessageType fromValue( byte value )
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
                throw new IllegalArgumentException( "Unexpected value=0x" + HexUtils.numToHex( value ) );
        }
    }

    public HandshakeMessageI parse( byte[] data, int offset, int length )
    {
        //TODO: It is good that parse() is an instance method and not static.  But it would be
        //nice if the parser class was set when each enum is constructed so that we don't
        //need the switch here, but the parse() methods in the HMIs are static and we wouldn't
        //be able to invoke them easily without reflection.

        switch( this )
        {
            case ClientHello:
                return com.sibilantsolutions.iplayers.layer.app.tls.domain.ClientHello.parse( data, offset, length );

            case ServerHello:
                return com.sibilantsolutions.iplayers.layer.app.tls.domain.ServerHello.parse( data, offset, length );

            case Certificate:
                return com.sibilantsolutions.iplayers.layer.app.tls.domain.Certificate.parse( data, offset, length );

            case ServerHelloDone:
                return com.sibilantsolutions.iplayers.layer.app.tls.domain.ServerHelloDone.parse( data, offset, length );

            case ClientKeyExchange:
                return com.sibilantsolutions.iplayers.layer.app.tls.domain.ClientKeyExchange.parse( data, offset, length );

            default:
                throw new IllegalArgumentException( "Unexpected value=" + this );
        }
    }

}
