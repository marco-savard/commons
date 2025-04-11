package com.marcosavard.commons.realtime.altitude;

import com.marcosavard.commons.realtime.RealTimeApplication;

public class AltitudeCalculatorTask extends RealTimeApplication.Task {
    private AltitudeCalculatorService calculator = new AltitudeCalculatorServiceImpl();

    public AltitudeCalculatorTask(RealTimeApplication.Context context) {
        super(context);
    }

    @Override
    public void run() {
        // compute h
        start();
        double elapsedTimeSec = getContext().getElapsedTime() / 1000.0;
        long h = calculator.calculate(elapsedTimeSec);

        // print message
        getContext().getFormatWriter().indent();
        getContext().getFormatWriter().println("calculating altitude = {0} m", h);
        getContext().getFormatWriter().unindent();
        getContext().getFormatWriter().flush();
    }
}
