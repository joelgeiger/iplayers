package com.sibilantsolutions.iplayers.layer.app.http;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

public class HttpDateFormatTest
{
    private Date date;

    @Before
    public void setUp()
    {
        Calendar cal = Calendar.getInstance( TimeZone.getTimeZone( "GMT" ) );
        cal.clear();    //Clear out anything pre-set, like milliseconds.
        cal.set( 1994, Calendar.NOVEMBER, 6, 8, 49, 37 );
        date = cal.getTime();
    }

    @Test
    public void testFormat()
    {
        assertEquals( "Sun, 06 Nov 1994 08:49:37 GMT", HttpDateFormat.format( date ) );
        assertEquals( "Sunday, 06-Nov-94 08:49:37 GMT", HttpDateFormat.format_bad1( date ) );

        //This is technically wrong... there should be an extra space-pad before the date,
        //but since we are only parsing this format and NOT producing it, it doesn't matter.
        //The test for parse() should test for the space-pad.
        //assertEquals( "Sun Nov  6 08:49:37 1994", HttpDateFormat.format_bad2( date ) );
        assertEquals( "Sun Nov 6 08:49:37 1994", HttpDateFormat.format_bad2( date ) );
    }

    @Test( expected = NullPointerException.class )
    public void testFormat_null()
    {
        HttpDateFormat.format( null );
    }

    @Test
    public void testParse()
    {
        assertEquals( date.getTime(), HttpDateFormat.parse( "Sun, 06 Nov 1994 08:49:37 GMT" ).getTime() );
        assertEquals( date, HttpDateFormat.parse( "Sun, 06 Nov 1994 08:49:37 GMT" ) );
        assertEquals( date, HttpDateFormat.parse( "Sunday, 06-Nov-94 08:49:37 GMT" ) );
        assertEquals( date, HttpDateFormat.parse( "Sun Nov  6 08:49:37 1994" ) );

            //This format is invalid because date should be space-padded, but our parser handles it.
        assertEquals( date, HttpDateFormat.parse( "Sun Nov 6 08:49:37 1994" ) );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void testParse_junk()
    {
        HttpDateFormat.parse( "fsdfsdf" );
    }

    @Test( expected = NullPointerException.class )
    public void testParse_null()
    {
        HttpDateFormat.parse( null );
    }

}
