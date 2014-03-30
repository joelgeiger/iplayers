package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.util.ArrayList;
import java.util.List;

public class EllipticCurvesExtension implements ExtensionI
{
    private List<EllipticCurve> ellipticCurves = new ArrayList<EllipticCurve>();

    public List<EllipticCurve> getEllipticCurves()
    {
        return ellipticCurves;
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

    @Override
    public String build()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

}
