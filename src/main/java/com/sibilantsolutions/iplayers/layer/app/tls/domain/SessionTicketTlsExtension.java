package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class SessionTicketTlsExtension implements ExtensionI
{

    @Override
    public String build()
    {
        return "";
    }

    @Override
    public Extension getExtensionType()
    {
        return Extension.sessionTicket_TLS;
    }

    public static SessionTicketTlsExtension parse( String data )
    {
        SessionTicketTlsExtension ext = new SessionTicketTlsExtension();

            //TODO: Finish parsing; add getters.

        return ext;
    }

}
