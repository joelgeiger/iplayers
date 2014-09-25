package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        random.setRandom( "" +
                (char)0x41 + (char)0x9F + (char)0x12 + (char)0xCB +
                (char)0xD6 + (char)0x97 + (char)0xDF + (char)0x23 +
                (char)0x1F + (char)0x39 + (char)0x28 + (char)0x3A +
                (char)0x69 + (char)0x74 + (char)0xD3 + (char)0xEA +
                (char)0x7B + (char)0x93 + (char)0xC9 + (char)0xE5 +
                (char)0xAE + (char)0x5D + (char)0x90 + (char)0x60 +
                (char)0xC2 + (char)0x06 + (char)0xC6 + (char)0xCF );
        clientHello.setSessionId( "" +
                (char)0x05 + (char)0xb1 + (char)0x56 + (char)0x4b + (char)0x5a + (char)0xa3 +
                (char)0x6a + (char)0x85 + (char)0x82 + (char)0xf4 + (char)0x2f + (char)0x28 +
                (char)0xec + (char)0xbf + (char)0x91 + (char)0x27 + (char)0x2a + (char)0x3a +
                (char)0xcd + (char)0xb7 + (char)0x5f + (char)0x76 + (char)0x0c + (char)0xb5 +
                (char)0x31 + (char)0xc3 + (char)0x63 + (char)0xd5 + (char)0xdc + (char)0x61 +
                (char)0xce + (char)0x76 );
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
        ecpf.setData( "" + (char)0x00 );

        SessionTicketTlsExtension stt = new SessionTicketTlsExtension();
        clientHello.getExtensions().add( stt );

        NextProtocolNegotiationExtension npn = new NextProtocolNegotiationExtension();
        clientHello.getExtensions().add( npn );

        StatusRequestExtension sr = new StatusRequestExtension();
        clientHello.getExtensions().add( sr );
        sr.setData( "" + (char)0x01 + (char)0x00 + (char)0x00 + (char)0x00 + (char)0x00 );

        //System.out.println( HexDump.prettyDump( record.build() ) );

        assertEquals( loadResource( "/samples/amazon https_04-clientHello.bin" ), record.build() );
    }

    @Test
    public void testParse01()
    {
        String hello = loadResource( "/samples/client_hello.bin" );

        assertEquals( 181, hello.length() );
        TlsRecord record = TlsRecord.parse( hello );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x00B0, record.getLength() ); //176
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ClientHello, hand.getHandshakeMessageType() );
        assertEquals( 0x0000AC, hand.getLength() ); //172
        ClientHello ch = (ClientHello)hand.getData();
        assertEquals( Version.TLS_1_2, ch.getVersion() );
        assertEquals( 0x7DB02DD0, ch.getRandom().getDate() );
        assertEquals( 28, ch.getRandom().getRandom().length() );
        assertEquals( "" +
                (char)0x32 + (char)0x58 + (char)0x07 + (char)0x4A +
                (char)0xCA + (char)0x47 + (char)0x9F + (char)0xD5 +
                (char)0x57 + (char)0xE9 + (char)0x0E + (char)0x25 +
                (char)0xB9 + (char)0xAC + (char)0x2E + (char)0x99 +
                (char)0x07 + (char)0x9A + (char)0x70 + (char)0xD2 +
                (char)0x98 + (char)0x64 + (char)0x42 + (char)0x3F +
                (char)0xA9 + (char)0x1C + (char)0x89 + (char)0xC8, ch.getRandom().getRandom() );


        assertEquals( 0, ch.getSessionIdLength() );
        assertEquals( "", ch.getSessionId() );

        assertEquals( 46, ch.getCipherSuitesLength() ); //0x2E
        assertEquals( 46 / 2, ch.getCipherSuites().size() );

        assertEquals( expectedCipherSuites.size(), ch.getCipherSuites().size() );

        for ( int i = 0; i < expectedCipherSuites.size(); i++ )
        {
            assertEquals( expectedCipherSuites.get( i ), ch.getCipherSuites().get( i ) );
        }

        assertEquals( 1, ch.getCompressionMethodsLength() );
        assertEquals( 1, ch.getCompressionMethods().size() );
        assertEquals( CompressionMethod.Null, ch.getCompressionMethods().get( 0 ) );

        assertEquals( 85, ch.getExtensionsLength() );   //0x0055

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
        String serverHello = loadResource( "/samples/server_hello01.bin" );

        assertEquals( 86, serverHello.length() );
        TlsRecord record = TlsRecord.parse( serverHello );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x0051, record.getLength() ); //81
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ServerHello, hand.getHandshakeMessageType() );
        assertEquals( 0x00004D, hand.getLength() ); //77

    }

    @Test
    public void testParse03()
    {
        String bin = loadResource( "/samples/amazon https_13-certificate.bin" );

        assertEquals( 4145, bin.length() );

        TlsRecord record = TlsRecord.parse( bin );

        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x102C, record.getLength() ); //4140

        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.Certificate, hand.getHandshakeMessageType() );
        assertEquals( 0x001028, hand.getLength() ); //4136

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
        String bin = loadResource( "/samples/amazon https_13-serverHelloDone.bin" );

        assertEquals( 9, bin.length() );
        TlsRecord record = TlsRecord.parse( bin );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x0004, record.getLength() ); //4
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ServerHelloDone, hand.getHandshakeMessageType() );
        assertEquals( 0x000000, hand.getLength() ); //0
        ServerHelloDone shd = (ServerHelloDone)hand.getData();
        assertNotNull( shd );
    }

    @Test
    public void testParse05()
    {
        String hello = loadResource( "/samples/amazon https_04-clientHello.bin" );

        assertEquals( 191, hello.length() );
        TlsRecord record = TlsRecord.parse( hello );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x00BA, record.getLength() ); //186
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ClientHello, hand.getHandshakeMessageType() );
        assertEquals( 0x0000B6, hand.getLength() ); //182
        ClientHello ch = (ClientHello)hand.getData();
        assertEquals( Version.TLS_1_0, ch.getVersion() );
        assertEquals( 0x149BB5DB, ch.getRandom().getDate() );
        assertEquals( 28, ch.getRandom().getRandom().length() );
        assertEquals( "" +
                (char)0x41 + (char)0x9F + (char)0x12 + (char)0xCB +
                (char)0xD6 + (char)0x97 + (char)0xDF + (char)0x23 +
                (char)0x1F + (char)0x39 + (char)0x28 + (char)0x3A +
                (char)0x69 + (char)0x74 + (char)0xD3 + (char)0xEA +
                (char)0x7B + (char)0x93 + (char)0xC9 + (char)0xE5 +
                (char)0xAE + (char)0x5D + (char)0x90 + (char)0x60 +
                (char)0xC2 + (char)0x06 + (char)0xC6 + (char)0xCF, ch.getRandom().getRandom() );

        assertEquals( 32, ch.getSessionIdLength() );
        assertEquals( "" +
                (char)0x05 + (char)0xb1 + (char)0x56 + (char)0x4b + (char)0x5a + (char)0xa3 +
                (char)0x6a + (char)0x85 + (char)0x82 + (char)0xf4 + (char)0x2f + (char)0x28 +
                (char)0xec + (char)0xbf + (char)0x91 + (char)0x27 + (char)0x2a + (char)0x3a +
                (char)0xcd + (char)0xb7 + (char)0x5f + (char)0x76 + (char)0x0c + (char)0xb5 +
                (char)0x31 + (char)0xc3 + (char)0x63 + (char)0xd5 + (char)0xdc + (char)0x61 +
                (char)0xce + (char)0x76, ch.getSessionId() );

        assertEquals( 46, ch.getCipherSuitesLength() ); //0x2E
        assertEquals( 46 / 2, ch.getCipherSuites().size() );

        assertEquals( expectedCipherSuites.size(), ch.getCipherSuites().size() );

        for ( int i = 0; i < expectedCipherSuites.size(); i++ )
        {
            assertEquals( expectedCipherSuites.get( i ), ch.getCipherSuites().get( i ) );
        }

        assertEquals( 1, ch.getCompressionMethodsLength() );
        assertEquals( 1, ch.getCompressionMethods().size() );
        assertEquals( CompressionMethod.Null, ch.getCompressionMethods().get( 0 ) );

        assertEquals( 63, ch.getExtensionsLength() );   //0x003F

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
        assertEquals( 1, epf.getData().length() );
        assertEquals( "" + (char)0x00, epf.getData() );
    }

    @Test
    public void testParse06()
    {
        String bin = loadResource( "/samples/amazon https_09-serverHello.bin" );

        assertEquals( 86, bin.length() );

        TlsRecord record = TlsRecord.parse( bin );

        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x0051, record.getLength() ); //81

        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ServerHello, hand.getHandshakeMessageType() );
        assertEquals( 0x00004D, hand.getLength() ); //77

        ServerHello sh = (ServerHello)hand.getData();
        assertEquals( Version.TLS_1_0, sh.getVersion() );
        assertEquals( 0x532F5B79, sh.getRandom().getDate() );
        assertEquals( 28, sh.getRandom().getRandom().length() );
        assertEquals( "" +
                (char)0x3C + (char)0x17 + (char)0xA6 + (char)0x49 +
                (char)0xD6 + (char)0xC3 + (char)0x69 + (char)0x0F +
                (char)0x1F + (char)0xAD + (char)0x17 + (char)0xD3 +
                (char)0x62 + (char)0x09 + (char)0x53 + (char)0x2D +
                (char)0x9D + (char)0x0F + (char)0xC6 + (char)0xD1 +
                (char)0x54 + (char)0xC9 + (char)0x5B + (char)0xB5 +
                (char)0xBF + (char)0xE5 + (char)0xB5 + (char)0x8F, sh.getRandom().getRandom() );

        assertEquals( 32, sh.getSessionId().length() );
        assertEquals( "" +
                (char)0xFA + (char)0x20 + (char)0x04 + (char)0xF6 + (char)0xA6 + (char)0x12 +
                (char)0xEE + (char)0xEF + (char)0x64 + (char)0x04 + (char)0xBE + (char)0x35 +
                (char)0x43 + (char)0xDE + (char)0x6D + (char)0xC8 + (char)0x8A + (char)0xF5 +
                (char)0x76 + (char)0x75 + (char)0xE5 + (char)0x1A + (char)0x20 + (char)0x0C +
                (char)0x5F + (char)0x6D + (char)0x91 + (char)0x2E + (char)0xE9 + (char)0x94 +
                (char)0x3B + (char)0xEF, sh.getSessionId() );

        assertEquals( CipherSuite.TLS_RSA_WITH_RC4_128_SHA, sh.getCipherSuite() );
        assertEquals( CompressionMethod.Null, sh.getCompressionMethod() );

        assertEquals( 1, sh.getExtensions().size() );

        RenegotiationInfoExtension ri = (RenegotiationInfoExtension)sh.getExtensions().get( 0 );
        assertEquals( 0, ri.getData().length() );

    }

    @Test
    public void testParse07()
    {
        String bin = loadResource( "/samples/amazon https_15-clientKeyExchange.bin" );

        assertEquals( 267, bin.length() );

        TlsRecord record = TlsRecord.parse( bin );

        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x0106, record.getLength() ); //262

        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ClientKeyExchange, hand.getHandshakeMessageType() );
        assertEquals( 0x000102, hand.getLength() ); //258

        ClientKeyExchange cke = (ClientKeyExchange)hand.getData();

        assertEquals( 0x0100, cke.getEncryptedPreMaster().length() );   //256
    }

    @Test
    public void testParse08()
    {
        String bin = loadResource( "/samples/amazon https_15-changeCipherSpec.bin" );

        assertEquals( 6, bin.length() );

        TlsRecord record = TlsRecord.parse( bin );

        assertEquals( ContentType.CHANGE_CIPHER_SPEC, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x0001, record.getLength() ); //1

        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        ChangeCipherSpec ccs = (ChangeCipherSpec)protocolMessages.get( 0 );
        assertEquals( "" + (char)0x01, ccs.getChangeCipherSpecMessageType() );
    }
/* TODO
    @Test
    public void testParse09()
    {
        String bin = loadResource( "/samples/amazon https_15-encryptedHandshakeMessage.bin" );

        assertEquals( 41, bin.length() );

        TlsRecord record = TlsRecord.parse( bin );

        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x0024, record.getLength() ); //36

        //TODO
    }
*/

    @Test
    public void testParse10()
    {
        String serverHello = loadResource( "/samples/server_hello02.bin" );

        assertEquals( 79, serverHello.length() );
        TlsRecord record = TlsRecord.parse( serverHello );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_2, record.getVersion() );
        assertEquals( 0x004A, record.getLength() ); //74
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ServerHello, hand.getHandshakeMessageType() );
        assertEquals( 0x000046, hand.getLength() ); //70

    }

    static private String loadResource( String path )
    {
        StringBuilder sBuf = new StringBuilder();
        InputStream ins = TlsRecordTest.class.getResourceAsStream( path );
        int numRead;
        byte[] buf = new byte[1024];
        try
        {
            while ( ( numRead = ins.read( buf ) ) != -1 )
            {
                String s = new String( buf, 0, numRead, "ISO_8859_1" );
                sBuf.append( s );
            }
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }

        return sBuf.toString();
    }

}
