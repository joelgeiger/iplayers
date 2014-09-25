package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertPath;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TlsRecordTest
{
    final static private List<CipherSuite> expectedCipherSuites = Arrays.asList(
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
            CipherSuite.TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA,
            CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
            CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
            CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
            CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
            CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA,
            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
            CipherSuite.TLS_DHE_DSS_WITH_AES_256_CBC_SHA,
            CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA,
            CipherSuite.TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA,
            CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA,
            CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA,
            CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA,
            CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA,
            CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA,
            CipherSuite.TLS_RSA_WITH_RC4_128_SHA,
            CipherSuite.TLS_RSA_WITH_RC4_128_MD5
    );

    @Test
    public void testBuild01()
    {
        TlsRecord record = new TlsRecord();

        record.setContentType( ContentType.HANDSHAKE );
        record.setVersion( Version.TLS_1_0 );

        HandshakeProtocol handshake = new HandshakeProtocol();
        record.getProtocolMessages().add( handshake );

        handshake.setHandshakeMessageType( HandshakeMessageType.ClientHello );

        ClientHello clientHello = new ClientHello();
        handshake.setData( clientHello );

        clientHello.setVersion( Version.TLS_1_0 );
        Random random = new Random();
        clientHello.setRandom( random );
        random.setDate( 0x149BB5DB );
        random.setRandom( new byte[] {
                (byte)0x41, (byte)0x9F, (byte)0x12, (byte)0xCB,
                (byte)0xD6, (byte)0x97, (byte)0xDF, (byte)0x23,
                (byte)0x1F, (byte)0x39, (byte)0x28, (byte)0x3A,
                (byte)0x69, (byte)0x74, (byte)0xD3, (byte)0xEA,
                (byte)0x7B, (byte)0x93, (byte)0xC9, (byte)0xE5,
                (byte)0xAE, (byte)0x5D, (byte)0x90, (byte)0x60,
                (byte)0xC2, (byte)0x06, (byte)0xC6, (byte)0xCF } );
        clientHello.setSessionId( new byte[] {
                (byte)0x05, (byte)0xb1, (byte)0x56, (byte)0x4b, (byte)0x5a, (byte)0xa3,
                (byte)0x6a, (byte)0x85, (byte)0x82, (byte)0xf4, (byte)0x2f, (byte)0x28,
                (byte)0xec, (byte)0xbf, (byte)0x91, (byte)0x27, (byte)0x2a, (byte)0x3a,
                (byte)0xcd, (byte)0xb7, (byte)0x5f, (byte)0x76, (byte)0x0c, (byte)0xb5,
                (byte)0x31, (byte)0xc3, (byte)0x63, (byte)0xd5, (byte)0xdc, (byte)0x61,
                (byte)0xce, (byte)0x76 } );
        clientHello.getCipherSuites().addAll( expectedCipherSuites );
        clientHello.getCompressionMethods().add( CompressionMethod.Null );
        ServerNameExtension serverName = new ServerNameExtension();
        serverName.setServerNameType( 0 );
        serverName.setServerName( "www.amazon.com" );
        clientHello.getExtensions().add( serverName );

        RenegotiationInfoExtension rie = new RenegotiationInfoExtension();
        clientHello.getExtensions().add( rie );

        EllipticCurvesExtension ece = new EllipticCurvesExtension();
        clientHello.getExtensions().add( ece );
        ece.getEllipticCurves().add( EllipticCurve.secp256r1 );
        ece.getEllipticCurves().add( EllipticCurve.secp384r1 );
        ece.getEllipticCurves().add( EllipticCurve.secp521r1 );

        EcPointFormatsExtension ecpf = new EcPointFormatsExtension();
        clientHello.getExtensions().add( ecpf );
        ecpf.setData( new byte[] { 0x00 } );

        SessionTicketTlsExtension stt = new SessionTicketTlsExtension();
        clientHello.getExtensions().add( stt );

        NextProtocolNegotiationExtension npn = new NextProtocolNegotiationExtension();
        clientHello.getExtensions().add( npn );

        StatusRequestExtension sr = new StatusRequestExtension();
        clientHello.getExtensions().add( sr );
        sr.setData( new byte[] { 0x01, 0x00, 0x00, 0x00, 0x00 } );

        byte[] expected = loadResource( "/samples/amazon https_04-clientHello.bin" );
        byte[] actual = record.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( actual ) );

        assertArrayEquals( expected, actual );
    }

    @Test
    public void testParse01()
    {
        byte[] bin = loadResource( "/samples/client_hello.bin" );

        assertEquals( 181, bin.length );
        TlsRecord record = TlsRecord.parse( bin, 0, bin.length );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ClientHello, hand.getHandshakeMessageType() );
        ClientHello ch = (ClientHello)hand.getData();
        assertEquals( Version.TLS_1_2, ch.getVersion() );
        assertEquals( 0x7DB02DD0, ch.getRandom().getDate() );
        assertEquals( 28, ch.getRandom().getRandom().length );
        assertArrayEquals( new byte[] {
                (byte)0x32, (byte)0x58, (byte)0x07, (byte)0x4A,
                (byte)0xCA, (byte)0x47, (byte)0x9F, (byte)0xD5,
                (byte)0x57, (byte)0xE9, (byte)0x0E, (byte)0x25,
                (byte)0xB9, (byte)0xAC, (byte)0x2E, (byte)0x99,
                (byte)0x07, (byte)0x9A, (byte)0x70, (byte)0xD2,
                (byte)0x98, (byte)0x64, (byte)0x42, (byte)0x3F,
                (byte)0xA9, (byte)0x1C, (byte)0x89, (byte)0xC8 }, ch.getRandom().getRandom() );


        assertEquals( 0, ch.getSessionId().length );
        assertArrayEquals( new byte[0], ch.getSessionId() );

        assertEquals( 46 / 2, ch.getCipherSuites().size() );

        assertEquals( expectedCipherSuites.size(), ch.getCipherSuites().size() );

        for ( int i = 0; i < expectedCipherSuites.size(); i++ )
        {
            assertEquals( expectedCipherSuites.get( i ), ch.getCipherSuites().get( i ) );
        }

        assertEquals( 1, ch.getCompressionMethods().size() );
        assertEquals( CompressionMethod.Null, ch.getCompressionMethods().get( 0 ) );

        assertEquals( 8, ch.getExtensions().size() );

        ServerNameExtension serverName = (ServerNameExtension)ch.getExtensions().get( 0 );
        assertEquals( 0, serverName.getServerNameType() );
        assertEquals( "www.amazon.com", serverName.getServerName() );

        EllipticCurvesExtension ellipticCurves = (EllipticCurvesExtension)ch.getExtensions().get( 2 );
        EllipticCurve[] expectedEllipticCurves = {
                EllipticCurve.secp256r1, EllipticCurve.secp384r1, EllipticCurve.secp521r1 };
        assertEquals( expectedEllipticCurves.length, ellipticCurves.getEllipticCurves().size() );
        for ( int i = 0; i < expectedEllipticCurves.length; i++ )
        {
            assertEquals( expectedEllipticCurves[i], ellipticCurves.getEllipticCurves().get( i ) );
        }
    }

    @Test
    public void testParse02()
    {
        byte[] bin = loadResource( "/samples/server_hello01.bin" );

        assertEquals( 86, bin.length );
        TlsRecord record = TlsRecord.parse( bin, 0, bin.length );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ServerHello, hand.getHandshakeMessageType() );

    }

    @Test
    public void testParse03()
    {
        byte[] bin = loadResource( "/samples/amazon https_13-certificate.bin" );

        assertEquals( 4145, bin.length );

        TlsRecord record = TlsRecord.parse( bin, 0, bin.length );

        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );

        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.Certificate, hand.getHandshakeMessageType() );

        Certificate c = (Certificate)hand.getData();

        CertPath certPath = c.getCertPath();
        assertEquals( 3, certPath.getCertificates().size() );
        X509Certificate cert = (X509Certificate)certPath.getCertificates().get( 0 );
        assertEquals( "CN=www.amazon.com, O=Amazon.com Inc., L=Seattle, ST=Washington, C=US",
                cert.getSubjectDN().getName() );
    }

    @Test
    public void testParse04()
    {
        byte[] bin = loadResource( "/samples/amazon https_13-serverHelloDone.bin" );

        assertEquals( 9, bin.length );
        TlsRecord record = TlsRecord.parse( bin, 0, bin.length );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ServerHelloDone, hand.getHandshakeMessageType() );
        ServerHelloDone shd = (ServerHelloDone)hand.getData();
        assertNotNull( shd );
    }

    @Test
    public void testParse05()
    {
        byte[] bin = loadResource( "/samples/amazon https_04-clientHello.bin" );

        assertEquals( 191, bin.length );
        TlsRecord record = TlsRecord.parse( bin, 0, bin.length );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ClientHello, hand.getHandshakeMessageType() );
        ClientHello ch = (ClientHello)hand.getData();
        assertEquals( Version.TLS_1_0, ch.getVersion() );
        assertEquals( 0x149BB5DB, ch.getRandom().getDate() );
        assertEquals( 28, ch.getRandom().getRandom().length );
        assertArrayEquals( new byte[] {
                (byte)0x41, (byte)0x9F, (byte)0x12, (byte)0xCB,
                (byte)0xD6, (byte)0x97, (byte)0xDF, (byte)0x23,
                (byte)0x1F, (byte)0x39, (byte)0x28, (byte)0x3A,
                (byte)0x69, (byte)0x74, (byte)0xD3, (byte)0xEA,
                (byte)0x7B, (byte)0x93, (byte)0xC9, (byte)0xE5,
                (byte)0xAE, (byte)0x5D, (byte)0x90, (byte)0x60,
                (byte)0xC2, (byte)0x06, (byte)0xC6, (byte)0xCF }, ch.getRandom().getRandom() );

        assertEquals( 32, ch.getSessionId().length );
        assertArrayEquals( new byte[] {
                (byte)0x05, (byte)0xb1, (byte)0x56, (byte)0x4b, (byte)0x5a, (byte)0xa3,
                (byte)0x6a, (byte)0x85, (byte)0x82, (byte)0xf4, (byte)0x2f, (byte)0x28,
                (byte)0xec, (byte)0xbf, (byte)0x91, (byte)0x27, (byte)0x2a, (byte)0x3a,
                (byte)0xcd, (byte)0xb7, (byte)0x5f, (byte)0x76, (byte)0x0c, (byte)0xb5,
                (byte)0x31, (byte)0xc3, (byte)0x63, (byte)0xd5, (byte)0xdc, (byte)0x61,
                (byte)0xce, (byte)0x76 }, ch.getSessionId() );

        assertEquals( 46 / 2, ch.getCipherSuites().size() );

        assertEquals( expectedCipherSuites.size(), ch.getCipherSuites().size() );

        for ( int i = 0; i < expectedCipherSuites.size(); i++ )
        {
            assertEquals( expectedCipherSuites.get( i ), ch.getCipherSuites().get( i ) );
        }

        assertEquals( 1, ch.getCompressionMethods().size() );
        assertEquals( CompressionMethod.Null, ch.getCompressionMethods().get( 0 ) );

        assertEquals( 7, ch.getExtensions().size() );

        ServerNameExtension serverName = (ServerNameExtension)ch.getExtensions().get( 0 );
        assertEquals( 0, serverName.getServerNameType() );
        assertEquals( "www.amazon.com", serverName.getServerName() );

        EllipticCurvesExtension ellipticCurves = (EllipticCurvesExtension)ch.getExtensions().get( 2 );
        EllipticCurve[] expectedEllipticCurves = {
                EllipticCurve.secp256r1, EllipticCurve.secp384r1, EllipticCurve.secp521r1 };
        assertEquals( expectedEllipticCurves.length, ellipticCurves.getEllipticCurves().size() );
        for ( int i = 0; i < expectedEllipticCurves.length; i++ )
        {
            assertEquals( expectedEllipticCurves[i], ellipticCurves.getEllipticCurves().get( i ) );
        }

        EcPointFormatsExtension epf = (EcPointFormatsExtension)ch.getExtensions().get( 3 );
        assertEquals( 1, epf.getData().length );
        assertArrayEquals( new byte[] { 0x00 }, epf.getData() );
    }

    @Test
    public void testParse06()
    {
        byte[] bin = loadResource( "/samples/amazon https_09-serverHello.bin" );

        assertEquals( 86, bin.length );

        TlsRecord record = TlsRecord.parse( bin, 0, bin.length );

        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );

        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ServerHello, hand.getHandshakeMessageType() );

        ServerHello sh = (ServerHello)hand.getData();
        assertEquals( Version.TLS_1_0, sh.getVersion() );
        assertEquals( 0x532F5B79, sh.getRandom().getDate() );
        assertEquals( 28, sh.getRandom().getRandom().length );
        assertArrayEquals( new byte[] {
                (byte)0x3C, (byte)0x17, (byte)0xA6, (byte)0x49,
                (byte)0xD6, (byte)0xC3, (byte)0x69, (byte)0x0F,
                (byte)0x1F, (byte)0xAD, (byte)0x17, (byte)0xD3,
                (byte)0x62, (byte)0x09, (byte)0x53, (byte)0x2D,
                (byte)0x9D, (byte)0x0F, (byte)0xC6, (byte)0xD1,
                (byte)0x54, (byte)0xC9, (byte)0x5B, (byte)0xB5,
                (byte)0xBF, (byte)0xE5, (byte)0xB5, (byte)0x8F }, sh.getRandom().getRandom() );

        assertEquals( 32, sh.getSessionId().length );
        assertArrayEquals( new byte[] {
                (byte)0xFA, (byte)0x20, (byte)0x04, (byte)0xF6, (byte)0xA6, (byte)0x12,
                (byte)0xEE, (byte)0xEF, (byte)0x64, (byte)0x04, (byte)0xBE, (byte)0x35,
                (byte)0x43, (byte)0xDE, (byte)0x6D, (byte)0xC8, (byte)0x8A, (byte)0xF5,
                (byte)0x76, (byte)0x75, (byte)0xE5, (byte)0x1A, (byte)0x20, (byte)0x0C,
                (byte)0x5F, (byte)0x6D, (byte)0x91, (byte)0x2E, (byte)0xE9, (byte)0x94,
                (byte)0x3B, (byte)0xEF }, sh.getSessionId() );

        assertEquals( CipherSuite.TLS_RSA_WITH_RC4_128_SHA, sh.getCipherSuite() );
        assertEquals( CompressionMethod.Null, sh.getCompressionMethod() );

        assertEquals( 1, sh.getExtensions().size() );

        RenegotiationInfoExtension ri = (RenegotiationInfoExtension)sh.getExtensions().get( 0 );
        assertEquals( 0, ri.getData().length );

    }

    @Test
    public void testParse07()
    {
        byte[] bin = loadResource( "/samples/amazon https_15-clientKeyExchange.bin" );

        assertEquals( 267, bin.length );

        TlsRecord record = TlsRecord.parse( bin, 0, bin.length );

        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );

        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ClientKeyExchange, hand.getHandshakeMessageType() );

        ClientKeyExchange cke = (ClientKeyExchange)hand.getData();

        assertEquals( 0x0100, cke.getEncryptedPreMaster().length );   //256
    }

    @Test
    public void testParse08()
    {
        byte[] bin = loadResource( "/samples/amazon https_15-changeCipherSpec.bin" );

        assertEquals( 6, bin.length );

        TlsRecord record = TlsRecord.parse( bin, 0, bin.length );

        assertEquals( ContentType.CHANGE_CIPHER_SPEC, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );

        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        ChangeCipherSpec ccs = (ChangeCipherSpec)protocolMessages.get( 0 );
        assertArrayEquals( new byte[] { 0x01 }, ccs.getChangeCipherSpecMessageType() );
    }
/* TODO
    @Test
    public void testParse09()
    {
        byte[] bin = loadResource( "/samples/amazon https_15-encryptedHandshakeMessage.bin" );

        assertEquals( 41, bin.length );

        TlsRecord record = TlsRecord.parse( bin, 0, bin.length );

        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x0024, record.getLength() ); //36

        //TODO
    }
*/

    @Test
    public void testParse10()
    {
        byte[] bin = loadResource( "/samples/server_hello02.bin" );

        assertEquals( 79, bin.length );
        TlsRecord record = TlsRecord.parse( bin, 0, bin.length );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_2, record.getVersion() );
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ServerHello, hand.getHandshakeMessageType() );

    }

    static private byte[] loadResource( String path )
    {
        //TODO: Use ResourceReader.

        InputStream ins = TlsRecordTest.class.getResourceAsStream( path );

        ByteArrayOutputStream baos = new ByteArrayOutputStream( 1024 );

        byte[] buf = new byte[1024];

        try
        {
            for ( int numRead; ( numRead = ins.read( buf ) ) != -1; )
            {
                baos.write( buf, 0, numRead );
            }
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }

        return baos.toByteArray();
    }
}
