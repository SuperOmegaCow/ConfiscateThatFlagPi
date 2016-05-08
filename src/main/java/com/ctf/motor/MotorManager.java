package com.ctf.motor;

import com.ctf.i2c.dp.DS1803;
import com.ctf.i2c.dp.Wiper;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class MotorManager {

    private Motor left;
    private Motor right;

    public MotorManager(DS1803 speedController, GpioPinDigitalOutput pin1,
                        GpioPinDigitalOutput pin2,
                        GpioPinDigitalOutput pin3,
                        GpioPinDigitalOutput pin4) {
        this.left = new Motor(speedController, Wiper.ZERO, pin1, pin2);
        this.right = new Motor(speedController, Wiper.ONE, pin3, pin4);
    }

    public Motor getLeft() {
        return left;
    }

    public Motor getRight() {
        return right;
    }

    public void driveForward() {
        left.setDirection(Direction.Forward);
        right.setDirection(Direction.Forward);
    }

    public void driveBackward() {
        left.setDirection(Direction.Backward);
        right.setDirection(Direction.Backward);
    }

    public void stop() {
        left.setDirection(Direction.Stationary);
        right.setDirection(Direction.Stationary);
    }

}
