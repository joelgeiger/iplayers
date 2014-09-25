package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class NextProtocolNegotiationExtension implements ExtensionI
{

    @Override
    public byte[] toDatastream()
    {
        return new byte[0];
    }

    @Override
    public Extension getExtensionType()
    {
        return Extension.next_protocol_negotiation;
    }

    public static NextProtocolNegotiationExtension parse( byte[] data, int offset, int length )
    {
        NextProtocolNegotiationExtension ext = new NextProtocolNegotiationExtension();

        //TODO: Finish parsing; add getters.

        return ext;
    }

}
