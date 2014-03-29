package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

public class TlsRecordTest
{
    final static private CipherSuite[] expectedCipherSuites = {
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
    };

    @Test
    public void testParse1()
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
        assertEquals( 0x0000AC, hand.getData().length() );
        ClientHello ch = ClientHello.parse( hand.getData() );
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

        assertEquals( expectedCipherSuites.length, ch.getCipherSuites().size() );

        for ( int i = 0; i < expectedCipherSuites.length; i++ )
        {
            assertEquals( expectedCipherSuites[i], ch.getCipherSuites().get( i ) );
        }

        assertEquals( 1, ch.getCompressionMethodsLength() );
        assertEquals( 1, ch.getCompressionMethods().size() );
        assertEquals( CompressionMethod.Null, ch.getCompressionMethods().get( 0 ) );

        assertEquals( 85, ch.getExtensionsLength() );   //0x0055
    }

    @Test
    public void testParse2()
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
        assertEquals( 0x00004D, hand.getData().length() );

    }

    @Test
    public void testParse3()
    {
        String serverHello = loadResource( "/samples/server_hello02.bin" );

        assertEquals( 4145, serverHello.length() );
        TlsRecord record = TlsRecord.parse( serverHello );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x102C, record.getLength() ); //4140
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.Certificate, hand.getHandshakeMessageType() );
        assertEquals( 0x001028, hand.getLength() ); //4136
        assertEquals( 0x001028, hand.getData().length() );

    }

    @Test
    public void testParse4()
    {
        String serverHello = loadResource( "/samples/server_hello03.bin" );

        assertEquals( 9, serverHello.length() );
        TlsRecord record = TlsRecord.parse( serverHello );
        assertEquals( ContentType.HANDSHAKE, record.getContentType() );
        assertEquals( Version.TLS_1_0, record.getVersion() );
        assertEquals( 0x0004, record.getLength() ); //4
        List<ProtocolMessage> protocolMessages = record.getProtocolMessages();
        assertEquals( 1, protocolMessages.size() );
        HandshakeProtocol hand = (HandshakeProtocol)protocolMessages.get( 0 );
        assertEquals( HandshakeMessageType.ServerHelloDone, hand.getHandshakeMessageType() );
        assertEquals( 0, hand.getLength() ); //0
        assertEquals( 0, hand.getData().length() );

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
        assertEquals( 0x0000B6, hand.getData().length() );
        ClientHello ch = ClientHello.parse( hand.getData() );
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

        assertEquals( expectedCipherSuites.length, ch.getCipherSuites().size() );

        for ( int i = 0; i < expectedCipherSuites.length; i++ )
        {
            assertEquals( expectedCipherSuites[i], ch.getCipherSuites().get( i ) );
        }

        assertEquals( 1, ch.getCompressionMethodsLength() );
        assertEquals( 1, ch.getCompressionMethods().size() );
        assertEquals( CompressionMethod.Null, ch.getCompressionMethods().get( 0 ) );

        assertEquals( 63, ch.getExtensionsLength() );   //0x003F
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
