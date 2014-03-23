package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

public class TlsRecordTest
{

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
