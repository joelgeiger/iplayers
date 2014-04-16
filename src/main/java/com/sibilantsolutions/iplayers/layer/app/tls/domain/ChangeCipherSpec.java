package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class ChangeCipherSpec extends ProtocolMessage
{

    private String changeCipherSpecMessageType;

    @Override
    public String build()
    {
        return changeCipherSpecMessageType;
    }

    public String getChangeCipherSpecMessageType()
    {
        return changeCipherSpecMessageType;
    }

    public void setChangeCipherSpecMessageType( String changeCipherSpecMessageType )
    {
        this.changeCipherSpecMessageType = changeCipherSpecMessageType;
    }

    public static ChangeCipherSpec parse( String data )
    {
        ChangeCipherSpec ccs = new ChangeCipherSpec();

        ccs.changeCipherSpecMessageType = data;

        return ccs;
    }

}
