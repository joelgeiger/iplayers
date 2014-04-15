package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.io.ByteArrayInputStream;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import com.sibilantsolutions.iptools.util.HexDump;

public class Certificate implements HandshakeMessageI
{

    private CertPath certPath;

    @Override
    public String build()
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

    public static Certificate parse( String data )
    {
        Certificate c = new Certificate();

        int i = 0;

        int certsLen = data.charAt( i++ ) * 0x010000 + data.charAt( i++ ) * 0x0100 + data.charAt( i++ );

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

        for ( int end = i + certsLen; i < end; )
        {
            int certLen = data.charAt( i++ ) * 0x010000 + data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
            String certBin = data.substring( i, i + certLen );
            i += certLen;

            ByteArrayInputStream ins = new ByteArrayInputStream( certBin.getBytes( HexDump.cs ) );

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
