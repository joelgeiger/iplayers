package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sibilantsolutions.iptools.util.HexUtils;

public class EllipticCurvesExtension implements ExtensionI
{
    private List<EllipticCurve> ellipticCurves = new ArrayList<EllipticCurve>();

    @Override
    public String build()
    {
        StringBuilder buf = new StringBuilder();

        buf.append( HexUtils.encodeNum( ellipticCurves.size() * 2, 2 ) );
        for ( Iterator<EllipticCurve> iterator = ellipticCurves.iterator(); iterator.hasNext(); )
        {
            EllipticCurve ec = iterator.next();
            buf.append( HexUtils.encodeNum( ec.getValue(), 2 ) );
        }

        return buf.toString();
    }

    public List<EllipticCurve> getEllipticCurves()
    {
        return ellipticCurves;
    }

    @Override
    public Extension getExtensionType()
    {
        return Extension.elliptic_curves;
    }

    public static EllipticCurvesExtension parse( String data )
    {
        EllipticCurvesExtension ext = new EllipticCurvesExtension();

        int i = 0;

        int ecLength = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
        for ( int j = 0; j < ecLength; j += 2 )
        {
            EllipticCurve ec = EllipticCurve.fromValue( data.charAt( i++ ) * 0x0100 + data.charAt( i++ ) );
            ext.ellipticCurves.add( ec );
        }

        return ext;
    }

}
