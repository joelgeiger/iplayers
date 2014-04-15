package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import com.sibilantsolutions.iptools.util.HexUtils;

public class EcPointFormatsExtension implements ExtensionI
{

    private String data;

    @Override
    public String build()
    {
        StringBuilder buf = new StringBuilder();

        buf.append( HexUtils.encodeNum( data.length(), 1 ) );
        buf.append(  data );

        return buf.toString();
    }

    public String getData()
    {
        return data;
    }

    public void setData( String data )
    {
        this.data = data;
    }

    @Override
    public Extension getExtensionType()
    {
        return Extension.ec_point_formats;
    }

    public static EcPointFormatsExtension parse( String data )
    {
        EcPointFormatsExtension ext = new EcPointFormatsExtension();

        int i = 0;

        int ecLength = data.charAt( i++ );
        ext.data = data.substring( i, i + ecLength );

        return ext;
    }

}
