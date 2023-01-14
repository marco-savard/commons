package com.marcosavard.commons.util.security;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class SafeSwitchCaseDemo {
  private static final int PROCESSOR_COUNT = 4;
  private static final int ADDRESS_COUNT = 1000;

  public static void main(String[] args) {
    String[] addresses = createAddresses(ADDRESS_COUNT);
    Processor[] processors = createProcessors(PROCESSOR_COUNT + 1);

    AddressProcessor[] addressProcessors =
        new AddressProcessor[] {
          new CorrectButUnsafeAddressProcessor(),
          new BuggyATTAddressProcessor(),
          new BuggyATTVariantAddressProcessor(),
          new BuggyCloudFlareAddressProcessor(),
          new SafeAddressProcessor(),
        };

    addressProcessors[0].processAddresses(processors, addresses); // correct but unsafe
    addressProcessors[1].processAddresses(processors, addresses); // ATT bug
    addressProcessors[2].processAddresses(processors, addresses); // ATT bug (variant)
    addressProcessors[3].processAddresses(processors, addresses); // Cloudflare bug
    addressProcessors[4].processAddresses(processors, addresses); // safe processor
  }

  private static Processor[] createProcessors(int count) {
    List<Processor> processorList = new ArrayList();

    for (int i = 0; i < count; i++) {
      processorList.add(new Processor(i));
    }

    Processor[] processors = processorList.toArray(new Processor[0]);
    return processors;
  }

  private static String[] createAddresses(int count) {
    List<String> addressList = new ArrayList();

    for (int i = 0; i < count; i++) {
      addressList.add((new RandomIpv4Address().toString()));
    }

    String[] addresses = addressList.toArray(new String[0]);
    return addresses;
  }

  private static void printStats(String name, Processor[] processors) {
    Map<String, Long> processorByFrequency = new TreeMap<>();

    for (Processor processor : processors) {
      long count = processor.getCallCount();
      processor.clearCallCount();
      processorByFrequency.put(processor.toString(), count);
    }

    System.out.println(MessageFormat.format("Stats ({0}) : {1}", name, processorByFrequency));
  }

  //
  // inner classes
  //
  private abstract static class AddressProcessor {
    protected String name;

    public void processAddresses(Processor[] processors, String[] addresses) {
      for (String address : addresses) {
        processAddress(processors, address);
      }

      printStats(name, processors);
    }

    public abstract void processAddress(Processor[] processors, String address);
  }

  private static class CorrectButUnsafeAddressProcessor extends AddressProcessor {

    CorrectButUnsafeAddressProcessor() {
      name = "CorrectButUnsafe";
    }

    @Override
    public void processAddress(Processor[] processors, String address) {
      int value = Math.abs(address.hashCode()) % (processors.length - 1);

      switch (value) {
        case 0:
          processors[0].process(address);
          break;
        case 1:
          processors[1].process(address);
          break;
        case 2:
          processors[2].process(address);
          break;
        case 3:
          processors[3].process(address);
          break; // this break is important
        default:
          processors[4].process(address);
          break;
      }
    }
  }

  private static class BuggyATTAddressProcessor extends AddressProcessor {

    BuggyATTAddressProcessor() {
      name = "BuggyAT&T";
    }

    @Override
    public void processAddress(Processor[] processors, String address) {
      int value = Math.abs(address.hashCode()) % (processors.length - 1);

      switch (value) {
        case 0:
          processors[0].process(address);
          break;
        case 1:
          processors[1].process(address);
          break;
        case 2:
          processors[2].process(address);
          break;
        case 3:
          processors[3].process(address);
          // break; -- OOPS removed accidently ;
        default:
          processors[4].process(address);
          break;
      }
    }
  }

  private static class BuggyATTVariantAddressProcessor extends AddressProcessor {

    BuggyATTVariantAddressProcessor() {
      name = "BuggyAT&T (Variant)";
    }

    @Override
    public void processAddress(Processor[] processors, String address) {
      int value = Math.abs(address.hashCode()) % (processors.length - 1);

      switch (value) {
        case 0:
          processors[0].process(address);
          break;
        case 1:
          processors[1].process(address);
          break;
        case 2:
          processors[2].process(address);
          // break;
          // case 3 :
          processors[3].process(address);
          break;
          // default :
          // processors[4].process(address);
          // break;
      }
    }
  }

  private static class BuggyCloudFlareAddressProcessor extends AddressProcessor {
    BuggyCloudFlareAddressProcessor() {
      name = "Buggy CloudFlare";
    }

    @Override
    public void processAddress(Processor[] processors, String address) {
      int value = Math.abs(address.hashCode()) % (processors.length - 1);

      if (value == 0) {
        processors[0].process(address);
      } else if (value == 1) {
        processors[1].process(address);
      } else if (true) { // misplaced
        processors[4].process(address);
      } else if (value == 2) {
        processors[2].process(address);
      }
    }
  }

  private static class SafeAddressProcessor extends AddressProcessor {
    SafeAddressProcessor() {
      name = "Safe implementation";
    }

    @Override
    public void processAddress(Processor[] processors, String address) {
      int value = Math.abs(address.hashCode()) % (processors.length - 1);

      Runnable switchCase =
          SafeSwitchCase.switchOn(value)
              .ifCase(0, () -> processors[0].process(address))
              .ifCase(1, () -> processors[1].process(address))
              .ifCase(2, () -> processors[2].process(address))
              .ifCase(3, () -> processors[3].process(address))
              .elseDefault(() -> processors[4].process(address));

      switchCase.run();
    }
  }

  private static class Processor {
    private int parameter;
    private long callCount = 0;

    public Processor(int parameter) {
      this.parameter = parameter;
    }

    public void process(String address) {
      callCount++;
    }

    @Override
    public String toString() {
      String msg = MessageFormat.format("processor{0}", parameter);
      return msg;
    }

    public long getCallCount() {
      return callCount;
    }

    public void clearCallCount() {
      callCount = 0;
    }
  }

  private static class RandomIpv4Address {
    private short[] parts = new short[4];

    public RandomIpv4Address() {
      Random r = new Random();
      parts[0] = (short) r.nextInt(256);
      parts[1] = (short) r.nextInt(256);
      parts[2] = (short) r.nextInt(256);
      parts[3] = (short) r.nextInt(256);
    }

    @Override
    public String toString() {
      String str = MessageFormat.format("{0}.{1}.{2}.{3}", parts[0], parts[1], parts[2], parts[3]);
      return str;
    }
  }
}
