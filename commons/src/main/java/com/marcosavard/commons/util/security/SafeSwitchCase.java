package com.marcosavard.commons.util.security;

import java.util.HashMap;
import java.util.Map;

public class SafeSwitchCase {

  public static SafeSwitchCaseBuilder switchOn(int value) {
    return new SafeSwitchCaseBuilder(value);
  }

  public static class SafeSwitchCaseBuilder {
    private int value;

    private Map<Integer, Runnable> runnables = new HashMap<>();

    private SafeSwitchCaseBuilder(int value) {
      this.value = value;
    }

    public SafeSwitchCaseBuilder ifCase(int value, Runnable runnable) {
      runnables.put(value, runnable);
      return this;
    }

    public Runnable elseDefault(Runnable defaultRunnable) {
      return new SafeSwitchCaseRunner(this, defaultRunnable);
    }
  }

  private static class SafeSwitchCaseRunner implements Runnable {
    private Runnable runnable;

    public SafeSwitchCaseRunner(SafeSwitchCaseBuilder builder, Runnable defaultRunnable) {
      Runnable runnable = builder.runnables.get(builder.value);
      this.runnable = (runnable == null) ? defaultRunnable : runnable;
    }

    @Override
    public void run() {
      runnable.run();
    }
  }
}
