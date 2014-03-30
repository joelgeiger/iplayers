package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum ContentType
{
    CHANGE_CIPHER_SPEC  ( Values.CHANGE_CIPHER_SPEC ),
    ALERT               ( Values.ALERT ),
    HANDSHAKE           ( Values.HANDSHAKE ),
    APPLICATION         ( Values.APPLICATION );

    static private class Values
    {
        final static private char CHANGE_CIPHER_SPEC  = 0x14;   //20
        final static private char ALERT               = 0x15;   //21
        final static private char HANDSHAKE           = 0x16;   //22
        final static private char APPLICATION         = 0x17;   //23
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

    static public ContentType valueOf( char value )
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

}
