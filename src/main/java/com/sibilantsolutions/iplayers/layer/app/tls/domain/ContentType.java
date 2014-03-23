package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum ContentType
{
    CHANGE_CIPHER_SPEC  ( Values.CHANGE_CIPHER_SPEC ),
    ALERT               ( Values.ALERT ),
    HANDSHAKE           ( Values.HANDSHAKE ),
    APPLICATION         ( Values.APPLICATION );

    final private int value;

    private ContentType( int value )
    {
        this.value = value;
    }

    static public ContentType valueOf( int value )
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

    static private class Values
    {
        final static private int CHANGE_CIPHER_SPEC  = 0x14;   //20
        final static private int ALERT               = 0x15;   //21
        final static private int HANDSHAKE           = 0x16;   //22
        final static private int APPLICATION         = 0x17;   //23
    }

}
