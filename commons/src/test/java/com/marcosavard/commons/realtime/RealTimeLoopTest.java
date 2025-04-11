package com.marcosavard.commons.realtime;

import java.text.MessageFormat;

public class RealTimeLoopTest {

  /*
  public static void main(String[] args) {
    RealTime.Loop realTimeLoop = RealTime.Loop.of(2).withMaxDuration(5);
    realTimeLoop.addSummaryTask(new PrintStatTask(realTimeLoop));
    realTimeLoop.run();
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
  }*/
}
