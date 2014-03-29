package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class RenegotiationInfoExtension implements ExtensionI
{

    public static RenegotiationInfoExtension parse( String data )
    {
        RenegotiationInfoExtension ext = new RenegotiationInfoExtension();

        int i = 0;

            //TODO: Finish parsing; add getters.
        int length = data.charAt( i++ );
        String renegotiationInfoExtension = data.substring( i, i + length );
        i += length;

        return ext;
    }

}
