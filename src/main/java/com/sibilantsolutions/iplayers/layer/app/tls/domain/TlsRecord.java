package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TlsRecord
{

    private ContentType contentType;
    private Version version;
    //private int length; //The length of Protocol message(s), not to exceed 2^14 bytes (16 KiB).
    private final List<ProtocolMessage> protocolMessages = new ArrayList<ProtocolMessage>();
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

    public void setMac( Mac mac )
    {
        this.mac = mac;
    }

    public void setPadding( Padding padding )
    {
        this.padding = padding;
    }

    static public TlsRecord parse( byte[] data, int offset, int length )
    {
        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

        TlsRecord r = new TlsRecord();

        byte contentType = bb.get();
        r.contentType = ContentType.fromValue( contentType );
        r.version = Version.fromValue( bb.get(), bb.get() );
        int len = bb.getChar();

        final int HEADER_LEN = 5;   //content type, version, length

        while ( bb.position() < len + HEADER_LEN )
        {
            byte[] pmData = new byte[len];
            bb.get( pmData );

            ProtocolMessage msg = r.contentType.parse( pmData, 0, pmData.length );
            r.protocolMessages.add( msg );
        }

        return r;
    }

    public byte[] toDatastream()
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for ( Iterator<ProtocolMessage> iterator = protocolMessages.iterator(); iterator.hasNext(); )
        {
            ProtocolMessage pm = iterator.next();
            byte[] pmData = pm.toDatastream();
            try
            {
                baos.write( pmData );
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                throw new UnsupportedOperationException( "MY TODO", e );
            }
        }

        byte[] pms = baos.toByteArray();

        ByteBuffer bb = ByteBuffer.allocate( 5 + pms.length );

        bb.put( contentType.getValue() );

        bb.put( version.getMajor() );
        bb.put( version.getMinor() );

        bb.putChar( (char)pms.length );

        bb.put( pms );

        return bb.array();
    }

}
