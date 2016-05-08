package com.ctf;

import com.ctf.servo.Servo;
import com.ctf.servo.ServoManager;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;

public class Main {

    // https://christianloris.wordpress.com/2013/11/19/netduino-plus-2-with-mcp23017-3-advanced-interrupt-handling-intcap/

    public static void main(String[] args) throws Exception {
        final GpioController gpio = GpioFactory.getInstance();

        Servo servo = new Servo(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_20), 90);
        ServoManager servoManager = new ServoManager(servo);
        Thread.sleep(1000);
        servo.setPosition(0);
        Thread.sleep(1000);
        servo.setPosition(180);
        Thread.sleep(0);
        servo.setPosition(90);
    }

}
