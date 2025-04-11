package com.marcosavard.commons.realtime.stat;

import com.marcosavard.commons.math.arithmetic.Percent;
import com.marcosavard.commons.realtime.RealTimeApplication;

public class StatisticsPrintingTask extends RealTimeApplication.Task {
    public StatisticsPrintingTask(RealTimeApplication.Context context) {
        super(context);
    }

    @Override
    public void run() {
        super.start();

        String patt = "==== END OF ITERATION {0} simulationTime:{1} ms; {2} processing, {3} idle ===";
        long iteration = getContext().getIteration();
        double period = getContext().getPeriod();
        long elapsedTime = getContext().getElapsedTime();
        long totalIdleTime = getContext().getTotalIdleTime();

        long expectedElapsedTime = (long) ((iteration + 1) * period);
        String expectedElapsedTimeStr = Long.toString(expectedElapsedTime);
        String processingPct = Percent.of(expectedElapsedTime - totalIdleTime, expectedElapsedTime).withPrecision(1).toString();
        String idlePct = Percent.of(totalIdleTime, expectedElapsedTime).withPrecision(1).toString();

        getContext().getFormatWriter().println("==== Real-time loop completed ===");
        getContext().getFormatWriter().indent();
        getContext().getFormatWriter().println("simulation time: {0} ms", expectedElapsedTimeStr);
        getContext().getFormatWriter().println("processing time :{0}", processingPct);
        getContext().getFormatWriter().println("idle time : {0}", idlePct);
        getContext().getFormatWriter().unindent();
        getContext().getFormatWriter().flush();
    }
}
