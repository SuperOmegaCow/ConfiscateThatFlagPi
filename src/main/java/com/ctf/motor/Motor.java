package com.ctf.motor;

import com.ctf.i2c.dp.DS1803;
import com.ctf.i2c.dp.Wiper;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

public class Motor {

    private DS1803 speedController;
    private Wiper wiper;
    private GpioPinDigitalOutput high;
    private GpioPinDigitalOutput low;
    private volatile Direction direction;
    private volatile int speed; // 0-100%

    public Motor(DS1803 speedController, Wiper wiper, GpioPinDigitalOutput high, GpioPinDigitalOutput low) {
        this.speedController = speedController;
        this.wiper = wiper;
        this.high = high;
        this.low = low;
        this.direction = Direction.Stationary;
    }

    private int map(long x, long in_min, long in_max, long out_min, long out_max) {
        return (int) ((x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if (direction == Direction.Stationary) {
            high.setState(PinState.LOW);
            low.setState(PinState.LOW);
        } else if(direction == Direction.Forward) {
            high.setState(PinState.HIGH);
            low.setState(PinState.LOW);
        } else {
            high.setState(PinState.LOW);
            low.setState(PinState.HIGH);
        }
        this.direction = direction;
    }

    public int getSpeed() {
        return (byte) map(speed, 1, 256, 0, 100);
    }

    public void setSpeed(int speed) {
        int realSpeed = map(speed, 0, 100, 0, 256);
        this.speed = speed;
        try {
            this.speedController.setSpeed(this.wiper, realSpeed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
