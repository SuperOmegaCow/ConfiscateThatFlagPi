package com.ctf;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.i2c.I2CBus;

public class Main {

    public static void main(String[] args) throws Exception {
        final GpioController gpio = GpioFactory.getInstance();
        DistanceSensor sensor = new DistanceSensor(I2CBus.BUS_0, 12);
    }

}
