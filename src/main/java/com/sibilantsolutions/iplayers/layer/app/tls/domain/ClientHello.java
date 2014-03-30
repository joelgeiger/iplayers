package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sibilantsolutions.iptools.util.HexUtils;

public class ClientHello implements HandshakeMessageI
{
    //final static private Logger log = LoggerFactory.getLogger( ClientHello.class );

    private Version version;
    private Random random;
    private int sessionIdLength;
    private String sessionId;
    private int cipherSuitesLength;
    private List<CipherSuite> cipherSuites = new ArrayList<CipherSuite>();
    private int compressionMethodsLength;
    private List<CompressionMethod> compressionMethods = new ArrayList<CompressionMethod>();
    private int extensionsLength;
    private List<ExtensionI> extensions = new ArrayList<ExtensionI>();

    public List<CipherSuite> getCipherSuites()
    {
        return cipherSuites;
    }

    public int getCipherSuitesLength()
    {
        return cipherSuitesLength;
    }

    public int getCompressionMethodsLength()
    {
        return compressionMethodsLength;
    }

    public List<CompressionMethod> getCompressionMethods()
    {
        return compressionMethods;
    }

    public List<ExtensionI> getExtensions()
    {
        return extensions;
    }

    public int getExtensionsLength()
    {
        return extensionsLength;
    }

    public Random getRandom()
    {
        return random;
    }

    public Version getVersion()
    {
        return version;
    }

    public int getSessionIdLength()
    {
        return sessionIdLength;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public static ClientHello parse( String data )
    {
        ClientHello ch = new ClientHello();

        int i = 0;

        ch.version = Version.fromValue( data.charAt( i++ ), data.charAt( i++ ) );
        final int RANDOM_LENGTH = 32;
        ch.random = Random.parse( data.substring( i, i + RANDOM_LENGTH ) );
        i += RANDOM_LENGTH;
        ch.sessionIdLength = data.charAt( i++ );
        ch.sessionId = data.substring( i, i + ch.sessionIdLength );
        i += ch.sessionIdLength;
        ch.cipherSuitesLength = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
        for ( int j = 0; j < ch.cipherSuitesLength; j += 2 )
        {
            int csVal = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
            CipherSuite cs = CipherSuite.fromValue( csVal );
            ch.cipherSuites.add( cs );
        }
        ch.compressionMethodsLength = data.charAt( i++ );
        for ( int j = 0; j < ch.compressionMethodsLength; j++ )
        {
            char cmVal = data.charAt( i++ );
            CompressionMethod cm = CompressionMethod.fromValue( cmVal );
            ch.compressionMethods.add( cm );
        }
        ch.extensionsLength = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );

        for ( int end = i + ch.extensionsLength; i < end; )
        {
            int extensionTypeVal = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
            Extension extensionType = Extension.fromValue( extensionTypeVal );
            int length = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
            String extData = data.substring( i, i + length );
            i += length;

            ExtensionI extension;

                //TODO: Move this switch into a parse() method in Extension enum.
            switch( extensionType )
            {
                case server_name:
                    extension = ServerNameExtension.parse( extData );
                    break;
                case renegotiation_info:
                    extension = RenegotiationInfoExtension.parse( extData );
                    break;
                case elliptic_curves:
                    extension = EllipticCurvesExtension.parse( extData );
                    break;
                case ec_point_formats:
                    extension = EcPointFormatsExtension.parse( extData );
                    break;
                case sessionTicket_TLS:
                    extension = SessionTicketTlsExtension.parse( extData );
                    break;
                case next_protocol_negotiation:
                    extension = NextProtocolNegotiationExtension.parse( extData );
                    break;
                case status_request:
                    extension = StatusRequestExtension.parse( extData );
                    break;
                case unknown01:
                    extension = Unknown01Extension.parse( extData );
                    break;

                default:
                    throw new IllegalArgumentException( "Unexpected value=" + extensionType );
            }

            ch.extensions.add( extension );
        }

        return ch;
    }

    @Override
    public String build()
    {
        StringBuilder buf = new StringBuilder();

        buf.append( version.getMajor() );
        buf.append( version.getMinor() );
        buf.append( HexUtils.encodeNum( random.getDate(), 4 ) );
        buf.append( random.getRandom() );
        buf.append( HexUtils.encodeNum( sessionId.length(), 1 ) );
        buf.append( sessionId );

        StringBuilder csBuf = new StringBuilder();
        for ( Iterator<CipherSuite> iterator = cipherSuites.iterator(); iterator.hasNext(); )
        {
            CipherSuite cs = iterator.next();
            csBuf.append( HexUtils.encodeNum( cs.getValue(), 2 ) );
        }
        buf.append( HexUtils.encodeNum( csBuf.length(), 2 ) );
        buf.append( csBuf );

        StringBuilder cmBuf = new StringBuilder();
        for ( Iterator<CompressionMethod> iterator = compressionMethods.iterator(); iterator.hasNext(); )
        {
            CompressionMethod cm = iterator.next();
            cmBuf.append( cm.getValue() );
        }
        buf.append( HexUtils.encodeNum( cmBuf.length(), 1 ) );
        buf.append( cmBuf );

        StringBuilder extBuf = new StringBuilder();
        for ( Iterator<ExtensionI> iterator = extensions.iterator(); iterator.hasNext(); )
        {
            ExtensionI ext = iterator.next();

                //TODO this needs to be the value from Extension enum.
            extBuf.append( "" + (char)0xFF + (char)0xFF );

            String extData = ext.build();
            extBuf.append( HexUtils.encodeNum( extData.length(), 2 ) );
            extBuf.append( extData );
        }
        buf.append( HexUtils.encodeNum( extBuf.length(), 2 ) );
        buf.append( extBuf );

        return buf.toString();
    }

    public void setVersion( Version version )
    {
        this.version = version;
    }

    public void setRandom( Random random )
    {
        this.random = random;
    }

    public void setSessionId( String sessionId )
    {
        this.sessionId = sessionId;
    }

}
