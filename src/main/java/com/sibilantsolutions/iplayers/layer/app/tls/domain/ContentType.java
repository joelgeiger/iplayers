package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum ContentType
{
    CHANGE_CIPHER_SPEC  ( Values.CHANGE_CIPHER_SPEC ),
    ALERT               ( Values.ALERT ),
    HANDSHAKE           ( Values.HANDSHAKE ),
    APPLICATION         ( Values.APPLICATION );

    static private interface Values
    {
        final static public char CHANGE_CIPHER_SPEC  = 0x14;   //20
        final static public char ALERT               = 0x15;   //21
        final static public char HANDSHAKE           = 0x16;   //22
        final static public char APPLICATION         = 0x17;   //23
    }

    final private char value;

    private ContentType( char value )
    {
        this.value = value;
    }

    public char getValue()
    {
        return value;
    }

    static public ContentType fromValue( char value )
    {
       switch( value )
       {
           case Values.CHANGE_CIPHER_SPEC:
               return CHANGE_CIPHER_SPEC;

           case Values.ALERT:
               return ALERT;

           case Values.HANDSHAKE:
               return HANDSHAKE;

           case Values.APPLICATION:
               return APPLICATION;

           default:
               throw new IllegalArgumentException( "Unexpected value=" + value );
       }

    }

    public ProtocolMessage parse( String data )
    {
        switch( this )
        {
            case HANDSHAKE:
                return HandshakeProtocol.parse( data );

            case CHANGE_CIPHER_SPEC:
                return ChangeCipherSpec.parse( data );

            default:
                throw new IllegalArgumentException( "Unexpected contentType=" + this );
        }
    }

}
