package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public enum Extension
{
    server_name( Values.server_name ),
    renegotiation_info( Values.renegotiation_info ),
    elliptic_curves( Values.elliptic_curves ),
    ec_point_formats( Values.ec_point_formats ),
    sessionTicket_TLS( Values.sessionTicket_TLS ),
    next_protocol_negotiation( Values.next_protocol_negotiation ),
    status_request( Values.status_request ),
    unknown01( Values.unknown01 );

    static private interface Values
    {
        final static public int server_name = 0x0000;
        final static public int renegotiation_info = 0xFF01;
        final static public int elliptic_curves = 0x000A;
        final static public int ec_point_formats = 0x000B;
        final static public int sessionTicket_TLS = 0x0023;
        final static public int next_protocol_negotiation = 0x3374;
        final static public int status_request = 0x0005;
        final static public int unknown01 = 0x000D;
    }

    final private int value;

    private Extension( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    static public Extension fromValue( int value )
    {
        switch( value )
        {
            case Values.server_name:
                return server_name;
            case Values.renegotiation_info:
                return renegotiation_info;
            case Values.elliptic_curves:
                return elliptic_curves;
            case Values.ec_point_formats:
                return ec_point_formats;
            case Values.sessionTicket_TLS:
                return sessionTicket_TLS;
            case Values.next_protocol_negotiation:
                return next_protocol_negotiation;
            case Values.status_request:
                return status_request;
            case Values.unknown01:
                return unknown01;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }
}
