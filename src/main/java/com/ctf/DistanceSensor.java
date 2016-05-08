package com.ctf;

import com.pi4j.gpio.extension.ads.ADS1015GpioProvider;
import com.pi4j.gpio.extension.ads.ADS1015Pin;
import com.pi4j.gpio.extension.ads.ADS1x15GpioProvider;
import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CBus;

public class DistanceSensor {

    private ADS1015GpioProvider reader;
    private final GpioPinAnalogInput sensorOne;
    private final GpioPinAnalogInput sensorTwo;
    private final GpioPinDigitalOutput switchingPin;
    private volatile SensorData sensorOneData;
    private volatile SensorData sensorTwoData;
    private volatile boolean running = true;
    private long nextTime;

    private static final long SENSOR_SWITCH_TIME = 1000; //nano

    public DistanceSensor(int bus, int address) {
        try {
            this.reader = new ADS1015GpioProvider(bus, address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final GpioController gpio = GpioFactory.getInstance();
        this.switchingPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_18);
        switchingPin.setState(PinState.LOW);
        this.sensorOne = gpio.provisionAnalogInputPin(reader, ADS1015Pin.INPUT_A0);
        this.sensorTwo = gpio.provisionAnalogInputPin(reader, ADS1015Pin.INPUT_A2);
        reader.setProgrammableGainAmplifier(ADS1x15GpioProvider.ProgrammableGainAmplifierValue.PGA_4_096V, ADS1015Pin.INPUT_A0);
        reader.setProgrammableGainAmplifier(ADS1x15GpioProvider.ProgrammableGainAmplifierValue.PGA_4_096V, ADS1015Pin.INPUT_A2);
        reader.setMonitorInterval(50);
        this.sensorOneData = new SensorData(0, 0, 0, 0);
        this.sensorTwoData = new SensorData(0, 0, 0, 0);

        Thread thread = new Thread() {
            @Override
            public void run() {
                nextTime = System.nanoTime() + SENSOR_SWITCH_TIME;
                SensorData temp;
                while (running) {
                    while (System.nanoTime() < nextTime) {
                    }
                    if(switchingPin.isHigh()) {
                        double value = sensorOne.getValue();
                        double percent = ((value * 100) / ADS1015GpioProvider.ADS1015_RANGE_MAX_VALUE);
                        double voltage = reader.getProgrammableGainAmplifier(sensorOne).getVoltage() * (percent/100);
                        double distance = ((6787/(value - 3)) - 4) / 5;
                        temp = new SensorData(value, percent, voltage, distance);
                        sensorOneData = temp;
                    } else {
                        double value = sensorTwo.getValue();
                        double percent = ((value * 100) / ADS1015GpioProvider.ADS1015_RANGE_MAX_VALUE);
                        double voltage = reader.getProgrammableGainAmplifier(sensorTwo).getVoltage() * (percent/100);
                        double distance = ((6787/(value - 3)) - 4) / 5;
                        temp = new SensorData(value, percent, voltage, distance);
                        sensorTwoData = temp;
                    }
                    switchingPin.toggle();
                    nextTime = System.nanoTime() + SENSOR_SWITCH_TIME;
                }
            }
        };
        thread.start();
    }

    public void shutdown() {
        this.running = false;
    }

    public SensorData readSensorOne() {
        return this.sensorOneData;
    }

    public SensorData readSensorTwo() {
        return this.sensorTwoData;
    }

    public class SensorData {

        private double rawValue;
        private double percent;
        private double voltage;
        private double distance;

        private SensorData(double rawValue, double percent, double voltage, double distance) {
            this.rawValue = rawValue;
            this.percent = percent;
            this.voltage = voltage;
            this.distance = distance;
        }

        public double getRawValue() {
            return rawValue;
        }

        public double getPercent() {
            return percent;
        }

        public double getVoltage() {
            return voltage;
        }

        public double getDistance() {
            return distance;
        }

        private void setData(double rawValue, double percent, double voltage, double distance) {
            this.rawValue = rawValue;
            this.percent = percent;
            this.voltage = voltage;
            this.distance = distance;
        }

    }

}
