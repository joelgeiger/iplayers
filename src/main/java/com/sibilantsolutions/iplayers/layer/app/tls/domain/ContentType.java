package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum ContentType
{
    CHANGE_CIPHER_SPEC  ( Values.CHANGE_CIPHER_SPEC ),
    ALERT               ( Values.ALERT ),
    HANDSHAKE           ( Values.HANDSHAKE ),
    APPLICATION         ( Values.APPLICATION );

    static private interface Values
    {
        final static public byte CHANGE_CIPHER_SPEC  = 0x14;   //20
        final static public byte ALERT               = 0x15;   //21
        final static public byte HANDSHAKE           = 0x16;   //22
        final static public byte APPLICATION         = 0x17;   //23
    }

    final private byte value;

    private ContentType( byte value )
    {
        this.value = value;
    }

    public byte getValue()
    {
        return value;
    }

    static public ContentType fromValue( byte value )
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

    public ProtocolMessage parse( byte[] data, int offset, int length )
    {
        switch( this )
        {
            case HANDSHAKE:
                return HandshakeProtocol.parse( data, offset, length );

            case CHANGE_CIPHER_SPEC:
                return ChangeCipherSpec.parse( data, offset, length );

            default:
                throw new IllegalArgumentException( "Unexpected contentType=" + this );
        }
    }

}
