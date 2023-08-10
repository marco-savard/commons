package com.marcosavard.commons.realtime;

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

    public ReferencialSystemTask(RealTime.Loop loop) {
      super(loop);
    }

    @Override
    public void run() {
      // make an exception
      double value = (6.5 * loop.getElapsedTime());
      short bh = toShort2(value);
      double bhe = 10 / (short) Math.exp(bh / 1000L);

      // print message
      String pat = "  ..bh={0}; bhe={1}";
      String msg = MessageFormat.format(pat, bh, bhe);
      System.out.println(msg);
    }

    private short toShort1(double value) {
      return (short) value;
    }

    private short toShort2(double value) {
      short safeValue = (short) Math.min(value, Short.MAX_VALUE);
      safeValue = (short) Math.max(safeValue, Short.MIN_VALUE);
      return safeValue;
    }
  }

  private static class PrintStatTask extends RealTime.Task {
    public PrintStatTask(RealTime.Loop loop) {
      super(loop);
    }

    @Override
    public void run() {
      String patt = "=== END OF ITERATION {0} simulationTime:{1} ms; waitTime={2} ms ===";
      int i = loop.getIteration();
      long elapsedTime = loop.getElapsedTime();
      long expectedElapsedTime = (long) (i * loop.getPeriod());
      String elapsedTimeStr = Long.toString(elapsedTime);
      String expectedElapsedTimeStr = Long.toString(expectedElapsedTime);
      long waitTime = expectedElapsedTime - elapsedTime;
      String msg = MessageFormat.format(patt, i, elapsedTimeStr, waitTime);
      System.out.println(msg);
    }
  }
}
