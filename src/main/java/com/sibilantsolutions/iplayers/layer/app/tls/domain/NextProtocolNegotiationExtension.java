package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class NextProtocolNegotiationExtension implements ExtensionI
{

    @Override
    public String build()
    {
        return "";
    }

    @Override
    public Extension getExtensionType()
    {
        return Extension.next_protocol_negotiation;
    }

    public static NextProtocolNegotiationExtension parse( String data )
    {
        NextProtocolNegotiationExtension ext = new NextProtocolNegotiationExtension();

        //TODO: Finish parsing; add getters.

        return ext;
    }

}
