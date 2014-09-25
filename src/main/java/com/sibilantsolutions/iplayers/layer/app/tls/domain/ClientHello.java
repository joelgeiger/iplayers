package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientHello implements HandshakeMessageI
{
    //final static private Logger log = LoggerFactory.getLogger( ClientHello.class );

    private Version version;
    private Random random;
    //private int sessionIdLength;
    private byte[] sessionId;
    //private int cipherSuitesLength;
    private final List<CipherSuite> cipherSuites = new ArrayList<CipherSuite>();
    //private int compressionMethodsLength;
    private final List<CompressionMethod> compressionMethods = new ArrayList<CompressionMethod>();
    //private int extensionsLength;
    private final List<ExtensionI> extensions = new ArrayList<ExtensionI>();

    public List<CipherSuite> getCipherSuites()
    {
        return cipherSuites;
    }

    public List<CompressionMethod> getCompressionMethods()
    {
        return compressionMethods;
    }

    public List<ExtensionI> getExtensions()
    {
        return extensions;
    }

    public Random getRandom()
    {
        return random;
    }

    public Version getVersion()
    {
        return version;
    }

    public byte[] getSessionId()
    {
        return sessionId;
    }

    public static ClientHello parse( byte[] data, int offset, int length )
    {
        ClientHello ch = new ClientHello();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

        ch.version = Version.fromValue( bb.get(), bb.get() );
        final int RANDOM_LENGTH = 32;
        byte[] randomDat = new byte[RANDOM_LENGTH];
        bb.get( randomDat );
        ch.random = Random.parse( randomDat, 0, randomDat.length );
        int sessionIdLength = bb.get();
        byte[] sessDat = new byte[sessionIdLength];
        bb.get( sessDat );
        ch.sessionId = sessDat;
        int cipherSuitesLength = bb.getChar();
        for ( int j = 0; j < cipherSuitesLength; j += 2 )
        {
            int csVal = bb.getChar();
            CipherSuite cs = CipherSuite.fromValue( csVal );
            ch.cipherSuites.add( cs );
        }
        int compressionMethodsLength = bb.get();
        for ( int j = 0; j < compressionMethodsLength; j++ )
        {
            byte cmVal = bb.get();
            CompressionMethod cm = CompressionMethod.fromValue( cmVal );
            ch.compressionMethods.add( cm );
        }
        int extensionsLength = bb.getChar();

        for ( int end = bb.position() + extensionsLength; bb.position() < end; )
        {
            int extensionTypeVal = bb.getChar();
            Extension extensionType = Extension.fromValue( extensionTypeVal );
            int len = bb.getChar();
            byte[] extData = new byte[len];
            bb.get( extData );

            ExtensionI extension = extensionType.parse( extData, 0, extData.length );

            ch.extensions.add( extension );
        }

        return ch;
    }

    @Override
    public byte[] toDatastream()
    {
        ByteArrayOutputStream extBAOS = new ByteArrayOutputStream();
        for ( Iterator<ExtensionI> iterator = extensions.iterator(); iterator.hasNext(); )
        {
            ExtensionI ext = iterator.next();

            byte[] extData = ext.toDatastream();
            ByteBuffer eBuf = ByteBuffer.allocate( 2 + 2 + extData.length );
            eBuf.putChar( (char)ext.getExtensionType().getValue() );
            eBuf.putChar( (char)extData.length );
            eBuf.put( extData );

            try
            {
                extBAOS.write( eBuf.array() );
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                throw new UnsupportedOperationException( "MY TODO", e );
            }
        }

        byte[] extsData = extBAOS.toByteArray();

        ByteBuffer bb = ByteBuffer.allocate(
                2 +
                4 + random.getRandom().length +
                1 + sessionId.length +
                2 + cipherSuites.size() * 2 +
                1 + compressionMethods.size() +
                2 + extsData.length );

        bb.put( version.getMajor() );
        bb.put( version.getMinor() );

        bb.putInt( (int)random.getDate() );
        bb.put( random.getRandom() );

        bb.put( (byte)sessionId.length );
        bb.put( sessionId );

        bb.putChar( (char)( cipherSuites.size() * 2 ) );
        for ( Iterator<CipherSuite> iterator = cipherSuites.iterator(); iterator.hasNext(); )
        {
            CipherSuite cs = iterator.next();
            bb.putChar( (char)cs.getValue() );
        }

        bb.put( (byte)compressionMethods.size() );
        for ( Iterator<CompressionMethod> iterator = compressionMethods.iterator(); iterator.hasNext(); )
        {
            CompressionMethod cm = iterator.next();
            bb.put( cm.getValue() );
        }

        bb.putChar( (char)extsData.length );
        bb.put( extsData );

        return bb.array();
    }

    public void setVersion( Version version )
    {
        this.version = version;
    }

    public void setRandom( Random random )
    {
        this.random = random;
    }

    public void setSessionId( byte[] sessionId )
    {
        this.sessionId = sessionId;
    }

}
