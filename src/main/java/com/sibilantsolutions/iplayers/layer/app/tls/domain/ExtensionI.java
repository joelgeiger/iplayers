package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public interface ExtensionI
{

    public byte[] toDatastream();

        //HACK: This method should be in the Exetension enum so that the mapping from class to
        //enum is encapsulated in one place.
    public Extension getExtensionType();

}
