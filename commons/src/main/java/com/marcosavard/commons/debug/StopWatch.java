package com.marcosavard.commons.debug;

import java.io.PrintStream;
import java.text.MessageFormat;

/**
 * A utility class to measure long-running operation and identify application's bottlenecks.
 * 
 * @author Marco
 *
 */
public class StopWatch {
  private PrintStream output;
  private long cumulativeTime = 0;
  private long startedAt, pausedAt;

  public StopWatch() {
    this(System.out);
  }

  public StopWatch(PrintStream out) {
    this.output = out;
  }

  /**
   * Start the stopwatch
   */
  public void start() {
    reset();
    resume();
    println("  ..started");
  }

  public void step() {
    println("  ..step");
  }

  /**
   * Pause the stopwatch
   */
  public void pause() {
    pausedAt = System.currentTimeMillis();
    cumulativeTime += (pausedAt - startedAt);
  }

  /**
   * Resume the stopwatch
   */
  public void resume() {
    startedAt = System.currentTimeMillis();
  }

  public void end() {
    pause();
    println("  ..ended");
  }

  /**
   * Return the cumulative time
   * 
   * @return cumulative time
   */
  public long getTime() {
    return cumulativeTime;
  }

  /**
   * Reset the stopwatch
   */
  public void reset() {
    cumulativeTime = 0L;
  }

  @Override
  public String toString() {
    pause();
    String text = "[" + getTime() + "ms]";
    resume();
    return text;
  }

  public void println(String text) {
    String msg = MessageFormat.format("{0} {1}", this, text);
    output.println(msg);
  }



}
