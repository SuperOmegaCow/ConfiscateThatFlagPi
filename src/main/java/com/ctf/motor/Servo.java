package com.ctf.motor;

import com.pi4j.component.servo.ServoDriver;
import com.pi4j.component.servo.ServoProvider;
import com.pi4j.component.servo.impl.GenericServo;
import com.pi4j.component.servo.impl.RPIServoBlasterProvider;
import com.pi4j.io.gpio.RaspiPin;

public class Servo {

    public static GenericServo clawGripper;
    public static GenericServo clawRaise;
    public static GenericServo sensorRotator;
    public static GenericServo extra;

    static {
        try {
            ServoProvider servoProvider = new RPIServoBlasterProvider();
            clawGripper = new GenericServo(servoProvider.getServoDriver(RaspiPin.GPIO_04), "Claw Gripper");
            clawRaise = new GenericServo(servoProvider.getServoDriver(RaspiPin.GPIO_23), "Claw Raiser");
            sensorRotator = new GenericServo(servoProvider.getServoDriver(RaspiPin.GPIO_25), "Sensor Rotator");
            extra = new GenericServo(servoProvider.getServoDriver(RaspiPin.GPIO_24), "Extra");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPositionPercent(GenericServo servo, float percent) {
        servo.setPosition(map(percent, 0f, 100f, -100f, 100f));
    }

    public static void setPositionDegrees(GenericServo servo, float degrees) {
        servo.setPosition(map(degrees, 0, 180, -100f, 100f));
    }

    private static float map(float x, float in_min, float in_max, float out_min, float out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

}
