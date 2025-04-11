package com.marcosavard.commons.realtime.referential;

//import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.realtime.RealTimeApplication;

public class ReferencialSystemTask extends RealTimeApplication.Task {
    private ReferencialSystemService calculator1 = new ReferencialSystemServiceImpl();
    private ReferencialSystemService calculator2 = new ReferencialSystemServiceImpl();

    public ReferencialSystemTask(RealTimeApplication.Context context) {
        super(context);
    }

    @Override
    public void run() {
        start();
        double value = (0.9 *  getContext().getElapsedTime());
        short[] bh = new short[4];
        double result;

        try {
            result = calculator1.calculate(value, bh);
        } catch (RuntimeException ex) {
            try {
                getContext().getFormatWriter().println("calculator1 Failed, fall back to calculator2");
                getContext().getFormatWriter().flush();
                result = calculator2.calculate(value, bh);
            } catch (RuntimeException ex2) {
                InterruptedException ie = new InterruptedException("referential system S1 and S2 failed");
                throw new RuntimeException(ie);
            }
        }

        getContext().getFormatWriter().indent();
        getContext().getFormatWriter().println("computing referential system : value={0} bh={1}", value, bh);
        getContext().getFormatWriter().unindent();
        getContext().getFormatWriter().flush();
    }
}
