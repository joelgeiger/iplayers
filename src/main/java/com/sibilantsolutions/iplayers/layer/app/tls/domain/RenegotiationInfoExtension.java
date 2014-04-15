package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import com.sibilantsolutions.iptools.util.HexUtils;

public class RenegotiationInfoExtension implements ExtensionI
{

    private String data = "";

    @Override
    public String build()
    {
        StringBuilder buf = new StringBuilder();

        buf.append( HexUtils.encodeNum( data.length(), 1 ) );
        buf.append( data );

        return buf.toString();
    }

    @Override
    public Extension getExtensionType()
    {
        return Extension.renegotiation_info;
    }

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
