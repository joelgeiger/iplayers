package com.sibilantsolutions.iplayers.layer.app.irc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.sibilantsolutions.iplayers.layer.app.irc.domain.IrcLine;

public class IrcLineParserTest
{

    @Test
    public void testParse1()
    {
        IrcLine line = IrcLineParser.parse( ":nutime.de.SpotChat.org 255 Bumps :I have 365 clients and 1 servers" );

        assertEquals( "nutime.de.SpotChat.org", line.getPrefix() );
        assertEquals( "255", line.getCommand() );
        assertEquals( "Bumps :I have 365 clients and 1 servers", line.getParameters() );
    }

    @Test
    public void testParse15()
    {
            //Not seen IRL, just testing space handling.
        IrcLine line = IrcLineParser.parse( ":nutime.de.SpotChat.org    255    Bumps :I have 365 clients and 1 servers" );

        assertEquals( "nutime.de.SpotChat.org", line.getPrefix() );
        assertEquals( "255", line.getCommand() );
        assertEquals( "Bumps :I have 365 clients and 1 servers", line.getParameters() );
    }

    @Test
    public void testParse2()
    {
        IrcLine line = IrcLineParser.parse( "PING :nutime.de.SpotChat.org" );

        assertNull( line.getPrefix() );
        assertEquals( "PING", line.getCommand() );
        assertEquals( ":nutime.de.SpotChat.org", line.getParameters() );
    }

    @Test
    public void testParse25()
    {
            //Not seen IRL, just testing space handling.
        IrcLine line = IrcLineParser.parse( "   PING   :nutime.de.SpotChat.org" );

        assertNull( line.getPrefix() );
        assertEquals( "PING", line.getCommand() );
        assertEquals( ":nutime.de.SpotChat.org", line.getParameters() );
    }

    @Test
    public void testParse3()
    {
        IrcLine line = IrcLineParser.parse( ":InfoServ!InfoServ@services.SpotChat.org NOTICE Bumps :On connecting to this Network you are agreeing to be scanned for open Proxies. Please disregard any messages from Monitoring or Security software about this matter, it helps us keeping our Network a safe place." );

        assertEquals( "InfoServ!InfoServ@services.SpotChat.org", line.getPrefix() );
        assertEquals( "NOTICE", line.getCommand() );
        assertEquals( "Bumps :On connecting to this Network you are agreeing to be scanned for open Proxies. Please disregard any messages from Monitoring or Security software about this matter, it helps us keeping our Network a safe place.", line.getParameters() );
    }

    @Test
    public void testParse4()
    {
        IrcLine line = IrcLineParser.parse( ":Bumps!assum@SpotChat-hjuu7s.pa.comcast.net MODE Bumps +ix" );

        assertEquals( "Bumps!assum@SpotChat-hjuu7s.pa.comcast.net", line.getPrefix() );
        assertEquals( "MODE", line.getCommand() );
        assertEquals( "Bumps +ix", line.getParameters() );
    }

}
