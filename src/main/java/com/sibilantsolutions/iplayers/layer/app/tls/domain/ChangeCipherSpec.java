package com.sibilantsolutions.iplayers.layer.app.tls.domain;

public class ChangeCipherSpec extends ProtocolMessage
{

    private byte[] changeCipherSpecMessageType;

    @Override
    public byte[] toDatastream()
    {
        return changeCipherSpecMessageType;
    }

    public byte[] getChangeCipherSpecMessageType()
    {
        return changeCipherSpecMessageType;
    }

    public void setChangeCipherSpecMessageType( byte[] changeCipherSpecMessageType )
    {
        this.changeCipherSpecMessageType = changeCipherSpecMessageType;
    }

    public static ChangeCipherSpec parse( byte[] data, int offset, int length )
    {
        ChangeCipherSpec ccs = new ChangeCipherSpec();

        byte[] d = new byte[length];
        System.arraycopy( data, offset, d, 0, length );
        ccs.changeCipherSpecMessageType = d;

        return ccs;
    }

}
