package com.ctf.i2c.dp;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;

import java.io.IOException;

public class DS1803 {

    private final I2CBus bus;
    private I2CDevice device;
    private final int address;

    public DS1803(I2CBus bus, int address) {
        this.bus = bus;
        try {
            this.device = bus.getDevice(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.address = address;
    }

    public void setSpeed(Wiper wiper, int value) throws IOException {
        device.write(wiper.getNum(), (byte) value);
    }

}
