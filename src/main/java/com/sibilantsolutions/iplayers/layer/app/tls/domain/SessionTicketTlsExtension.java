package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class SessionTicketTlsExtension implements ExtensionI
{

    public static SessionTicketTlsExtension parse( String data )
    {
        SessionTicketTlsExtension ext = new SessionTicketTlsExtension();

            //TODO: Finish parsing; add getters.

        return ext;
    }

    @Override
    public String build()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

}
