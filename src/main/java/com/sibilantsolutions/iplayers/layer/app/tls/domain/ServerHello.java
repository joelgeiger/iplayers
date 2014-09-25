package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.util.ArrayList;
import java.util.List;

public class ServerHello implements HandshakeMessageI
{

    private Version version;
    private Random random;
    private String sessionId;
    private CipherSuite cipherSuite;
    private CompressionMethod compressionMethod;
    private final List<ExtensionI> extensions = new ArrayList<ExtensionI>();

    @Override
    public String build()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

    public Version getVersion()
    {
        return version;
    }

    public void setVersion( Version version )
    {
        this.version = version;
    }

    public Random getRandom()
    {
        return random;
    }

    public void setRandom( Random random )
    {
        this.random = random;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId( String sessionId )
    {
        this.sessionId = sessionId;
    }

    public CipherSuite getCipherSuite()
    {
        return cipherSuite;
    }

    public void setCipherSuite( CipherSuite cipherSuite )
    {
        this.cipherSuite = cipherSuite;
    }

    public CompressionMethod getCompressionMethod()
    {
        return compressionMethod;
    }

    public void setCompressionMethod( CompressionMethod compressionMethod )
    {
        this.compressionMethod = compressionMethod;
    }

    public List<ExtensionI> getExtensions()
    {
        return extensions;
    }

    public static ServerHello parse( String data )
    {
        ServerHello sh = new ServerHello();

        int i = 0;

        sh.version = Version.fromValue( data.charAt( i++ ), data.charAt( i++ ) );
        final int RANDOM_LENGTH = 32;
        sh.random = Random.parse( data.substring( i, i + RANDOM_LENGTH ) );
        i += RANDOM_LENGTH;
        int sessionIdLength = data.charAt( i++ );
        sh.sessionId = data.substring( i, i + sessionIdLength );
        i += sessionIdLength;

        int csVal = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
        sh.cipherSuite = CipherSuite.fromValue( csVal );

        char cmVal = data.charAt( i++ );
        sh.compressionMethod = CompressionMethod.fromValue( cmVal );

        if ( i < data.length() )
        {
            int extensionsLength = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );

            for ( int end = i + extensionsLength; i < end; )
            {
                int extensionTypeVal = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
                Extension extensionType = Extension.fromValue( extensionTypeVal );
                int length = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
                String extData = data.substring( i, i + length );
                i += length;

                ExtensionI extension = extensionType.parse( extData );

                sh.extensions.add( extension );
            }
        }

        return sh;
    }

}
