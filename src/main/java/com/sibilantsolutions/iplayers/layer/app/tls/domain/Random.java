package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.nio.ByteBuffer;

public class Random
{

    private long date;      //4 bytes
    private byte[] random;  //28 bytes

    public long getDate()
    {
        return date;
    }

    public byte[] getRandom()
    {
        return random;
    }

    public void setDate( long date )
    {
        this.date = date;
    }

    public void setRandom( byte[] random )
    {
        this.random = random;
    }

    static public Random parse( byte[] data, int offset, int length )
    {
        Random r = new Random();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

        long dateNum = bb.getInt();
        r.date = dateNum;
        byte[] rByte = new byte[28];
        bb.get( rByte );
        r.random = rByte;

        return r;
    }

}
