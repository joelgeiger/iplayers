package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EllipticCurvesExtension implements ExtensionI
{
    private final List<EllipticCurve> ellipticCurves = new ArrayList<EllipticCurve>();

    @Override
    public byte[] toDatastream()
    {
        ByteBuffer bb = ByteBuffer.allocate( 2 + ellipticCurves.size() * 2 );

        bb.putChar( (char)( ellipticCurves.size() * 2 ) );

        for ( Iterator<EllipticCurve> iterator = ellipticCurves.iterator(); iterator.hasNext(); )
        {
            EllipticCurve ec = iterator.next();
            bb.putChar( (char)ec.getValue() );
        }

        return bb.array();
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

    public static EllipticCurvesExtension parse( byte[] data, int offset, int length )
    {
        EllipticCurvesExtension ext = new EllipticCurvesExtension();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

        int ecLength = bb.getChar();
        for ( int j = 0; j < ecLength; j += 2 )
        {
            EllipticCurve ec = EllipticCurve.fromValue( bb.getChar() );
            ext.ellipticCurves.add( ec );
        }

        return ext;
    }

}
