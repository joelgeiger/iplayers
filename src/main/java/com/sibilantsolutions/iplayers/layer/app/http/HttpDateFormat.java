package com.sibilantsolutions.iplayers.layer.app.http;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HttpDateFormat
{

        //"Sun, 06 Nov 1994 08:49:37 GMT"
    final static private DateFormat format = new SimpleDateFormat( "E, dd MMM yyyy HH:mm:ss z" );

        //"Sunday, 06-Nov-94 08:49:37 GMT"
    final static private DateFormat bad1 = new SimpleDateFormat( "EEEE, dd-MMM-yy HH:mm:ss z" );

        //"Sun Nov  6 08:49:37 1994"
    final static private DateFormat bad2 = new SimpleDateFormat( "E MMM d HH:mm:ss yyyy" );

    static
    {
        format.setTimeZone( TimeZone.getTimeZone( "GMT" ) );
        bad1.setTimeZone( TimeZone.getTimeZone( "GMT" ) );
        bad2.setTimeZone( TimeZone.getTimeZone( "GMT" ) );
    }

    static public String format( Date date )
    {
        return format.format( date );
    }

        //Package-private because we don't normally want to produce this format.
    static /*package*/ String format_bad1( Date date )
    {
        return bad1.format( date );
    }

        //Package-private because we don't normally want to produce this format.
    static /*package*/ String format_bad2( Date date )
    {
        return bad2.format( date );
    }

    static public Date parse( String str )
    {
        Date d = null;

        try
        {
            d = format.parse( str );
        }
        catch ( ParseException ignored )
        {
            //No-op.
        }

        if ( d == null )
        {
            try
            {
                d = bad1.parse( str );
            }
            catch ( ParseException ignored )
            {
                //No-op.
            }
        }

        if ( d == null )
        {
            try
            {
                d = bad2.parse( str );
            }
            catch ( ParseException e )
            {
                //Third attempt and still failed.  Should we be tolerant here?
                throw new UnsupportedOperationException( "OGTE TODO!", e );
            }
        }

        return d;
    }
}
