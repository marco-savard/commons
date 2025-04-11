package com.marcosavard.commons.realtime;

import com.marcosavard.commons.realtime.altitude.AltitudeCalculatorTask;
import com.marcosavard.commons.realtime.referential.ReferencialSystemTask;
import com.marcosavard.commons.realtime.stat.StatisticsPrintingTask;

import java.time.temporal.ChronoUnit;

public class Ariane5Test {

  public static void main(String[] args) {
      //settings
      int frequency = 2; //hertz
      RealTimeApplication.Context context = RealTimeApplication.ofFrequency(frequency)
              .withTimeOut(40, ChronoUnit.SECONDS)
              .withOutput(System.out)
              .build();

      //define tasks
      Runnable locationCalculator = new AltitudeCalculatorTask(context);
      Runnable referencialSystemTask = new ReferencialSystemTask(context);
      Runnable statPrinter = new StatisticsPrintingTask(context);

      Runnable realTimeTask = RealTimeApplication.of(context)
              .addMainLoop(locationCalculator)
              .addMainLoop(referencialSystemTask)
              .addTerminator(statPrinter)
              .build();

      realTimeTask.run();
  }
}
