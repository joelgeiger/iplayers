package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class EcPointFormatsExtension implements ExtensionI
{

    public static EcPointFormatsExtension parse( String data )
    {
        EcPointFormatsExtension ext = new EcPointFormatsExtension();

        int i = 0;

            //TODO: Finish parsing; add getters.
        int ecLength = data.charAt( i++ );
        int ecPointFormat = data.charAt( i++ );

        return ext;
    }

}
