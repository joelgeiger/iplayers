package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ServerHello implements HandshakeMessageI
{

    private Version version;
    private Random random;
    private byte[] sessionId;
    private CipherSuite cipherSuite;
    private CompressionMethod compressionMethod;
    private final List<ExtensionI> extensions = new ArrayList<ExtensionI>();

    @Override
    public byte[] toDatastream()
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

    public byte[] getSessionId()
    {
        return sessionId;
    }

    public void setSessionId( byte[] sessionId )
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

    public static ServerHello parse( byte[] data, int offset, int length )
    {
        ServerHello sh = new ServerHello();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

        sh.version = Version.fromValue( bb.get(), bb.get() );
        final int RANDOM_LENGTH = 32;
        byte[] rDat = new byte[RANDOM_LENGTH];
        bb.get( rDat );
        sh.random = Random.parse( rDat, 0, rDat.length );
        int sessionIdLength = bb.get();
        byte[] sessionId = new byte[sessionIdLength];
        bb.get( sessionId );
        sh.sessionId = sessionId;

        int csVal = bb.getChar();
        sh.cipherSuite = CipherSuite.fromValue( csVal );

        byte cmVal = bb.get();
        sh.compressionMethod = CompressionMethod.fromValue( cmVal );

        if ( bb.hasRemaining() )
        {
            int extensionsLength = bb.getChar();

            for ( int end = bb.position() + extensionsLength; bb.position() < end; )
            {
                int extensionTypeVal = bb.getChar();
                Extension extensionType = Extension.fromValue( extensionTypeVal );
                int extLen = bb.getChar();
                byte[] extData = new byte[extLen];
                bb.get( extData );

                ExtensionI extension = extensionType.parse( extData, 0, extData.length );

                sh.extensions.add( extension );
            }
        }

        return sh;
    }

}
