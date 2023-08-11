package com.marcosavard.commons.realtime;

import com.marcosavard.commons.math.Maths;
import com.marcosavard.commons.math.arithmetic.Percent;

import java.text.MessageFormat;

public class Ariane4Test {

  public static void main(String[] args) {
    RealTime.Loop realTimeLoop = RealTime.Loop.of(2).withMaxDuration(7);
    realTimeLoop.addTask(new LocationCalculatorTask(realTimeLoop));
    realTimeLoop.addTask(new ReferencialSystemTask(realTimeLoop));
    realTimeLoop.addSummaryTask(new PrintStatTask(realTimeLoop));
    realTimeLoop.run();
  }

  private static class LocationCalculatorTask extends RealTime.Task {

    public LocationCalculatorTask(RealTime.Loop loop) {
      super(loop);
    }

    @Override
    public void run() {
      // compute h
      double elapsedTimeSec = loop.getElapsedTime() / 1000.0;
      double a = 9.8;
      double h = 0.5 * a * elapsedTimeSec * elapsedTimeSec;

      // print message
      String pat = "  ..altitude={0} m;";
      String msg = MessageFormat.format(pat, h);
      System.out.println(msg);
    }
  }

  private static class ReferencialSystemTask extends RealTime.Task {
    private ReferencialSystemCalculator1 calculator1 = new ReferencialSystemCalculator1();
    private ReferencialSystemCalculator2 calculator2 = new ReferencialSystemCalculator2();

    public ReferencialSystemTask(RealTime.Loop loop) {
      super(loop);
    }

    @Override
    public void run() {
      double value = (5.5 * loop.getElapsedTime());
      short[] bh = new short[4];
      String sr;

      try {
        sr = calculator1.calculate(value, bh);
      } catch (RuntimeException ex) {
        System.out.println("SR1 Failed, use SR2");
        sr = calculator2.calculate(value, bh);
      }

      String pat = "  ..[{0}] value={1} bh={2}; a={3}, b={4} c={5}";
      String msg = MessageFormat.format(pat, sr, value, bh[0], bh[1], bh[2], bh[3]);
      System.out.println(msg);
    }
  }

  private static class ReferencialSystemCalculator1 {
    private static final String SR = "SR1";

    public String calculate(double value, short[] bho) {
      short bh = (short) value;
      double a = (bh / 10_000.0);
      double b = Math.exp(a);
      double c = (int) (100 / (int) b) / 100.0;

      bho[0] = bh;
      bho[1] = (short) (a * 100);
      bho[2] = (short) (b * 100);
      bho[3] = (short) (c * 100);
      return SR;
    }
  }

  private static class ReferencialSystemCalculator2 {
    private static final String SR = "SR2";

    public String calculate(double value, short[] bho) {
      short bh = Maths.toShort(value);
      double a = (bh / 10_000.0);
      double b = Math.exp(a);
      double c = (int) (100 / (int) b) / 100.0;

      bho[0] = bh;
      bho[1] = (short) (a * 100);
      bho[2] = (short) (b * 100);
      bho[3] = (short) (c * 100);
      return SR;
    }
  }

  private static class PrintStatTask extends RealTime.Task {
    public PrintStatTask(RealTime.Loop loop) {
      super(loop);
    }

    @Override
    public void run() {
      String patt = "=== END OF ITERATION {0} simulationTime:{1} ms; {2} processing, {3} idle ===";
      int i = loop.getIteration();
      double period = loop.getPeriod();
      long startIteration = (long) ((i - 1) * period);
      long expectedElapsedTime = (long) (i * period);
      long elapsedTime = loop.getElapsedTime();
      String expectedElapsedTimeStr = Long.toString(expectedElapsedTime);

      long processTime = elapsedTime - startIteration;
      String processingPct = Percent.of(processTime, period).withPrecision(1).toString();
      String idlePct = Percent.of(period - processTime, period).withPrecision(1).toString();
      String msg = MessageFormat.format(patt, i, expectedElapsedTimeStr, processingPct, idlePct);
      System.out.println(msg);
    }
  }
}
