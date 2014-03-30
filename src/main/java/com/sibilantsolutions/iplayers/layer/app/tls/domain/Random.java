package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class Random
{

    private long date;      //4 bytes
    private String random;  //28 bytes

    public long getDate()
    {
        return date;
    }

    public String getRandom()
    {
        return random;
    }

    public void setDate( long date )
    {
        this.date = date;
    }

    public void setRandom( String random )
    {
        this.random = random;
    }

    static public Random parse( String str )
    {
        Random r = new Random();

        String dateStr = str.substring( 0, 4 );
        long dateNum = strBytesToLong( dateStr, dateStr.length() );
        r.date = dateNum;
        String random = str.substring( 4 );
        r.random = random;

        return r;
    }

    static public long strBytesToLong( String str, int length )
    {
        long num = 0;

        for( int i = 0; i < length; i++ )
        {
            num = num << 8;

            char ch = str.charAt( i );

            num |= ch;
        }

        return num;
    }
}
