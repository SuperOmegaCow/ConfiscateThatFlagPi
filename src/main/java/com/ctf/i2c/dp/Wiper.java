package com.ctf.i2c.dp;

public enum Wiper {

    ZERO((byte) 0xA9),
    ONE((byte) 0xAA),
    BOTH((byte) 0xAF);

    private byte num;

    private Wiper(byte value) {
        this.num = value;
    }

    public byte getNum() {
        return num;
    }

}