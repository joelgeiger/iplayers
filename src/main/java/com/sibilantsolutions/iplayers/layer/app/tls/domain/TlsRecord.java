package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TlsRecord
{

    private ContentType contentType;
    private Version version;
    private int length; //The length of Protocol message(s), not to exceed 2^14 bytes (16 KiB).
    private List<ProtocolMessage> protocolMessages = new ArrayList<ProtocolMessage>();
    private Mac mac;
    private Padding padding;

    public ContentType getContentType()
    {
        return contentType;
    }

    public Version getVersion()
    {
        return version;
    }

    public int getLength()
    {
        return length;
    }

    public List<ProtocolMessage> getProtocolMessages()
    {
        return protocolMessages;
    }

    public Mac getMac()
    {
        return mac;
    }

    public Padding getPadding()
    {
        return padding;
    }

    public void setContentType( ContentType contentType )
    {
        this.contentType = contentType;
    }

    public void setVersion( Version version )
    {
        this.version = version;
    }

    public void setLength( int length )
    {
        this.length = length;
    }

    public void setProtocolMessages( List<ProtocolMessage> protocolMessages )
    {
        this.protocolMessages = protocolMessages;
    }

    public void setMac( Mac mac )
    {
        this.mac = mac;
    }

    public void setPadding( Padding padding )
    {
        this.padding = padding;
    }

    static public TlsRecord parse( String record )
    {
        TlsRecord r = new TlsRecord();

        int i = 0;

        char contentType = record.charAt( i++ );
        r.contentType = ContentType.valueOf( contentType );
        r.version = Version.valueOf( record.charAt( i++ ), record.charAt( i++ ) );
        r.length = record.charAt( i++ ) * 0x0100 + record.charAt( i++ );

        final int HEADER_LEN = 5;   //content type, version, length

        while ( i < r.length + HEADER_LEN )
        {
            switch( r.contentType )
            {
                case HANDSHAKE:
                    String str = record.substring( i, i + r.length );
                    i += r.length;
                    ProtocolMessage msg = HandshakeProtocol.parse( str );
                    r.protocolMessages.add( msg );
                    break;

                default:
                    throw new IllegalArgumentException( "Unexpected contentType=" + contentType );
            }
        }

        return r;
    }

    public String build()
    {
        StringBuilder buf = new StringBuilder();

        buf.append( contentType.getValue() );

        StringBuilder pms = new StringBuilder();

        for ( Iterator<ProtocolMessage> iterator = protocolMessages.iterator(); iterator.hasNext(); )
        {
            ProtocolMessage pm = iterator.next();
            pms.append( pm.build() );
        }

        buf.append( pms.length() );

        buf.append( pms );



        return buf.toString();
    }

}
