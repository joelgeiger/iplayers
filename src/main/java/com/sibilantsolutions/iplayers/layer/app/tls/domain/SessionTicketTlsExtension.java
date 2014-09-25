package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class SessionTicketTlsExtension implements ExtensionI
{

    @Override
    public byte[] toDatastream()
    {
        return new byte[0];
    }

    @Override
    public Extension getExtensionType()
    {
        return Extension.sessionTicket_TLS;
    }

    public static SessionTicketTlsExtension parse( byte[] data, int offset, int length )
    {
        SessionTicketTlsExtension ext = new SessionTicketTlsExtension();

            //TODO: Finish parsing; add getters.

        return ext;
    }

}
