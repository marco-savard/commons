package com.marcosavard.test;

import com.marcosavard.commons.realtime.RealTime;

public class Ariane5Test {

    public static void main(String[] args) {
        Runnable initializer = new Initializer();
        SystemRefentialInertial sri1 = new SystemRefentialInertial();
        SystemRefentialInertial sri2 = new SystemRefentialInertial();
        Runnable ac = new AltitudeCalculator(sri1, sri2);
        Runnable terminator = new Terminator();

        Runnable sequencer = RealTime.Sequencer.ofFrequency(2)
                .withTimeOut(5)
                .withInitializer(initializer)
                .withMainLoop(sri1)
                .withMainLoop(sri2)
                .withMainLoop(ac)
                .withTerminator(terminator)
                .build();

        sequencer.run();
    }

    private static class Initializer implements Runnable {

        @Override
        public void run() {
            System.out.println("==== Initiate");
        }
    }

    private static class SystemRefentialInertial implements Runnable {
        private static final double ACCELERATION = 2 * (9.8);
        private long startTime = -1;
        private double x,y,z;

        @Override
        public void run() {
            startTime = (startTime == -1) ? System.currentTimeMillis() : startTime;
            long elapsedTime = System.currentTimeMillis() - startTime;
            z = 0.5 * ACCELERATION * elapsedTime * elapsedTime / 500_000;
        }

        public double getZ() {
            return z;
        }
    }

    private static class AltitudeCalculator implements Runnable {
        private SystemRefentialInertial sri1, sri2;

        public AltitudeCalculator(SystemRefentialInertial sri1, SystemRefentialInertial sri2) {
            this.sri1 = sri1;
            this.sri2 = sri2;
        }

        @Override
        public void run() {
            double z = sri1.getZ();
            System.out.println("  z = " + z);
        }
    }

    private static class Terminator implements Runnable {

        @Override
        public void run() {
            System.out.println("==== Terminate");
        }
    }
}
