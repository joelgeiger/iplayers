package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public interface ExtensionI
{

    public String build();

        //HACK: This method should be in the Exetension enum so that the mapping from class to
        //enum is encapsulated in one place.
    public Extension getExtensionType();

}
