package com.marcosavard.commons.realtime;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.writer.FormatWriter;
import com.marcosavard.commons.io.writer.IndentWriter;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RealTimeApplication implements Runnable {
    private final Context context;
    private final int iterationDuration;
    private final List<Runnable> initializingTasks;
    private final List<Runnable> mainLoopTasks;
    private final List<Runnable> terminatingTasks;

    private RealTimeApplication(RealTimeApplication.Context context, List<Runnable> initializingTasks, List<Runnable> mainLoopTasks, List<Runnable> terminatingTasks) {
        this.iterationDuration = (int)((1.0 / context.frequency) * 1000);
        this.initializingTasks = initializingTasks;
        this.mainLoopTasks = mainLoopTasks;
        this.terminatingTasks = terminatingTasks;
        this.context = context;
    }

    public static ContextBuilder ofFrequency(int frequency) {
        return new ContextBuilder(frequency);
    }

    public static RealTimeApplicationBuilder of(Context context) {
        return new RealTimeApplicationBuilder(context);
    }

    @Override
    public void run() {
        initialize();
        runMainLoop();
        terminate();
    }

    private void initialize() {
        for (Runnable task : initializingTasks) {
            task.run();
        }
    }

    private void runMainLoop() {
        context.startMainLoop();
        long iteration = 0L;

        try {
            do {
                doIteration(iteration++);
            } while (! isCompleted());
        } catch (InterruptedException ex) {
            handleException(ex);
        } catch (RuntimeException ex) {
            handleException(ex);
        }
    }

    private void handleException(Exception ex) {
        context.formatWriter.println("---- Interrupted Exception : {0}", ex);
        context.formatWriter.flush();
        ex.printStackTrace(System.err);
    }

    private void doIteration(long iterationCount) throws InterruptedException {
        context.setIteration(iterationCount);
        long startAt = System.currentTimeMillis() - context.getStartTime();
        context.formatWriter.println("---- Iteration {0} starts at {1} ms", iterationCount, startAt);

        for (Runnable task : mainLoopTasks) {
            task.run();
        }

        long endAt = System.currentTimeMillis() - context.getStartTime();
        long timeBeforeDeadline = (iterationCount+1) * this.iterationDuration - endAt;
        context.addIdleTime(timeBeforeDeadline);
        context.formatWriter.println("---- Iteration {0} ends at {1} ms, idle {2} ms", iterationCount, endAt, timeBeforeDeadline);
        context.formatWriter.println();
        context.formatWriter.flush();

        Thread.sleep(timeBeforeDeadline);
    }

    private boolean isCompleted() {
        long duration = System.currentTimeMillis() - context.getStartTime();
        boolean completed = (duration / 1000) >= context.getTimeOut();
        return completed;
    }

    private void terminate() {
        for (Runnable task : terminatingTasks) {
            task.run();
        }
    }

    public static class ContextBuilder {
        private final int frequency;
        private long timeOutInSeconds;
        private PrintStream output = System.out; //by default

        public ContextBuilder(int frequency) {
            this.frequency = frequency;
        }

        public ContextBuilder withTimeOut(int timeout, ChronoUnit unit) {
            this.timeOutInSeconds = timeout * unit.getDuration().getSeconds();
            return this;
        }

        public ContextBuilder withOutput(PrintStream output) {
            this.output = output;
            return this;
        }

        public Context build() {
            return new Context(frequency, timeOutInSeconds, output);
        }
    }

    public static class Context {
        private final int frequency;
        private final long timeOutInSeconds;
        private final FormatWriter formatWriter;
        private long startTime;
        private long iterationCount;
        private long totalIdleTime = 0L;

        public Context(int frequency, long timeOutInSeconds, PrintStream output) {
            this.frequency = frequency;
            this.timeOutInSeconds = timeOutInSeconds;
            this.formatWriter = new FormatWriter(new PrintWriter(output));
        }

        public void startMainLoop() {
            startTime = System.currentTimeMillis();
        }

        public void setIteration(long iterationCount) {
            this.iterationCount = iterationCount;
        }

        public long getStartTime() {
            return startTime;
        }

        public long getIteration() {
            return iterationCount;
        }

        public double getPeriod() {
            return 1000L / frequency;
        }

        public long getElapsedTime() {
            return System.currentTimeMillis() - startTime;
        }

        public void addIdleTime(long idleTime) {
            totalIdleTime += idleTime;
        }

        public long getTotalIdleTime() {
            return totalIdleTime;
        }

        public long getTimeOut() {
            return timeOutInSeconds;
        }

        public FormatWriter getFormatWriter() {
            return formatWriter;
        }
    }

    public static class RealTimeApplicationBuilder {
        private Context context;
        private List<Runnable> initializingTasks = new ArrayList<>();
        private List<Runnable> mainLoopTasks = new ArrayList<>();
        private List<Runnable> terminatingTasks = new ArrayList<>();

        private RealTimeApplicationBuilder(Context context) {
            this.context = context;
        }

        public RealTimeApplicationBuilder addMainLoop(Runnable task) {
            mainLoopTasks.add(task);
            return this;
        }

        public RealTimeApplicationBuilder addTerminator(Runnable task) {
            terminatingTasks.add(task);
            return this;
        }

        public Runnable build() {
            return new RealTimeApplication(context, initializingTasks, mainLoopTasks, terminatingTasks);
        }
    }

    public static abstract class Task implements Runnable {
        private final Context context;
        private boolean started = false;
        private long startTime;

        protected Task(Context context) {
            this.context = context;
        }

        protected void start() {
            if (! started) {
                startTime = System.currentTimeMillis();
                started = true;
            }
        }

        protected double getElapsedTime() {
            return System.currentTimeMillis() - startTime;
        }

        protected Context getContext() {
            return context;
        }
    }


}
