package com.marcosavard.commons.realtime;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RealTime {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss SSSS");
    public static class Sequencer implements Runnable {
        private int iterationDuration;
        private int timeOut;
        private long startTime;

        private List<Runnable> initializers = new ArrayList<>();
        private List<Runnable> realTimeTasks = new ArrayList<>();
        private List<Runnable> terminators = new ArrayList<>();

        public static SequencerBuilder ofFrequency(int frequency) {
            SequencerBuilder builder = new SequencerBuilder(frequency);
            return builder;
        }

        Sequencer(int frequency, int timeOut, List<Runnable> initializers, List<Runnable> realTimeTasks, List<Runnable> terminators) {
            iterationDuration = (int)((1.0 / frequency) * 1000);
            this.timeOut = timeOut;
            this.initializers.addAll(initializers);
            this.realTimeTasks.addAll(realTimeTasks);
            this.terminators.addAll(terminators);
        }

        @Override
        public void run() {
            initiate();
            doMainLoop();
            terminate();
        }

        private void initiate() {
            for (Runnable initializer : initializers) {
                initializer.run();
            }
        }

        private void doMainLoop() {
            startTime = System.currentTimeMillis();
            long iteration = 0L;

            try {
                do {
                    doIteration(iteration++);
                } while (! isCompleted());
            } catch (InterruptedException ex) {
                //just exit
            }
        }

        private void doIteration(long iteration) throws InterruptedException {
            long startAt = System.currentTimeMillis() - startTime;

            for (Runnable realTimeTask : realTimeTasks) {
                realTimeTask.run();
            }

            long endAt = System.currentTimeMillis() - startTime;
            long timeBeforeDeadline = (iteration+1) * this.iterationDuration - endAt;
            Thread.sleep(timeBeforeDeadline);

            String pattern = "==== Iteration {0}; starts at {1} ms; ends at {2} ms, idle {3} ms";
            String msg = MessageFormat.format(pattern, iteration, startAt, endAt, timeBeforeDeadline);
            System.out.println(msg);
        }

        private boolean isCompleted() {
            long duration = System.currentTimeMillis() - startTime;
            boolean completed = (duration / 1000) >= timeOut;
            return completed;
        }

        private void terminate() {
            for (Runnable terminator : terminators) {
                terminator.run();
            }
        }
    }

    public static class SequencerBuilder {
        private int frequency;
        private int timeOut;

        private List<Runnable> initializers = new ArrayList<>();
        private List<Runnable> realTimeTasks = new ArrayList<>();
        private List<Runnable> terminators = new ArrayList<>();

        public SequencerBuilder(int frequency) {
            this.frequency = frequency;
        }

        public SequencerBuilder withTimeOut(int timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        public SequencerBuilder withInitializer(Runnable initializer) {
            initializers.add(initializer);
            return this;
        }

        public SequencerBuilder withMainLoop(Runnable runnable) {
            realTimeTasks.add(runnable);
            return this;
        }

        public SequencerBuilder withTerminator(Runnable terminator) {
            terminators.add(terminator);
            return this;
        }

        public Runnable build() {
            Sequencer sequencer = new Sequencer(frequency, timeOut, initializers, realTimeTasks, terminators);
            return sequencer;
        }
    }
}
