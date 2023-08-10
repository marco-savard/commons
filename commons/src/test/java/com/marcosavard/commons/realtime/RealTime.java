package com.marcosavard.commons.realtime;

import java.util.ArrayList;
import java.util.List;

public class RealTime {

  public static class Loop implements Runnable {
    private double period;
    private long maxDurationMillis;

    private int iteration = 1;

    private long startTime;

    private long elapsedTime;

    private List<Runnable> tasks = new ArrayList<>();
    private List<Runnable> summaryTasks = new ArrayList<>();

    private Loop(int frequency) {
      this.period = 1000L / frequency;
    }

    public static Loop of(int frequency) {
      Loop loop = new Loop(frequency);
      return loop;
    }

    public Loop withMaxDuration(int maxDuration) {
      this.maxDurationMillis = maxDuration * 1000L;
      return this;
    }

    public void addTask(Task task) {
      tasks.add(task);
    }

    public void addSummaryTask(Task task) {
      summaryTasks.add(task);
    }

    @Override
    public void run() {
      startTime = System.currentTimeMillis();
      boolean done = false;

      do {
        try {
          runIteration();
          done = elapsedTime >= maxDurationMillis;
          iteration++;
        } catch (InterruptedException e) {
          done = true;
        }
      } while (!done);
    }

    private void runIteration() throws InterruptedException {
      // run tasks
      for (Runnable task : tasks) {
        try {
          task.run();
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
      }

      // compute wait time
      elapsedTime = System.currentTimeMillis() - startTime;
      long expectedElapsedTime = (long) (iteration * period);
      long waitTime = expectedElapsedTime - elapsedTime;
      Thread.sleep(waitTime);

      // run tasks
      for (Runnable task : summaryTasks) {
        try {
          task.run();
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
      }
    }

    public int getIteration() {
      return iteration;
    }

    public long getElapsedTime() {
      return elapsedTime;
    }

    public double getPeriod() {
      return period;
    }
  }

  public abstract static class Task implements Runnable {
    protected RealTime.Loop loop;

    protected Task(RealTime.Loop loop) {
      this.loop = loop;
    }
  }
}
