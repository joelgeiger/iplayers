package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import com.sibilantsolutions.iptools.util.Convert;

public class Certificate implements HandshakeMessageI
{

    private CertPath certPath;

    @Override
    public byte[] toDatastream()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

    public CertPath getCertPath()
    {
        return certPath;
    }

    public void setCertPath( CertPath certPath )
    {
        this.certPath = certPath;
    }

    public static Certificate parse( byte[] data, int offset, int length )
    {
        Certificate c = new Certificate();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

        int certsLen = (int)Convert.toNum( bb, 3 );

        CertificateFactory cf;

        try
        {
            cf = CertificateFactory.getInstance( "X.509" );
        }
        catch ( CertificateException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        List<java.security.cert.Certificate> certs = new ArrayList<java.security.cert.Certificate>();

        for ( int end = bb.position() + certsLen; bb.position() < end; )
        {
            int certLen = (int)Convert.toNum( bb, 3 );
            byte[] certBin = new byte[certLen];
            bb.get( certBin );

            ByteArrayInputStream ins = new ByteArrayInputStream( certBin );

            java.security.cert.Certificate cert;

            try
            {
                cert = cf.generateCertificate( ins );
            }
            catch ( CertificateException e )
            {
                // TODO Auto-generated catch block
                throw new UnsupportedOperationException( "OGTE TODO!", e );
            }

            certs.add( cert );
        }

        CertPath certPath;

        try
        {
            certPath = cf.generateCertPath( certs );
        }
        catch ( CertificateException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        c.certPath = certPath;

        return c;
    }

}
