package com.marcosavard.commons.realtime.altitude;

public class AltitudeCalculatorServiceImpl implements AltitudeCalculatorService {
    private static final double G = 9.81; // meters per second^2

    @Override
    public long calculate(double elapsedTimeSec) {
        double a = 0.7 * G;
        long h = (long)Math.floor(0.5 * a * elapsedTimeSec * elapsedTimeSec);
        return h;
    }
}
