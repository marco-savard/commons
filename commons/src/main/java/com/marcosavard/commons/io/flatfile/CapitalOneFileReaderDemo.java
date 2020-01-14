package com.marcosavard.commons.io.flatfile;

import java.text.MessageFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import com.marcosavard.commons.lang.reflect.Introspection;
import com.marcosavard.commons.money.MoneyAmount;

public class CapitalOneFileReaderDemo {

  public static void main(String[] args) {
    // create reader
    Class<?> claz = CapitalOneFileReader.class;
    CapitalOneFileReader reader = CapitalOneFileReader.of(claz, "capitalOneFile.txt");

    // start the reader
    reader.addBlockHandler(new StepDisplayHandler());
    reader.addBlockHandler(new StepProcessHandler());
    reader.readAll();
  }

  private static class StepDisplayHandler extends CapitalOneBlockHandler {
    private long startTime;

    @Override
    public void onStart() {
      startTime = System.currentTimeMillis();
    }

    @Override
    public void onHeaderEvent(String header) {
      System.out.println("..reading header");
    }

    @Override
    public void onEntryEvent(List<String> entry) {
      String msg = MessageFormat.format("  ..reading entry of {0} items", entry.size() - 2);
      System.out.println(msg);
    }

    @Override
    public void onTrailerEvent(String header) {
      System.out.println("..reading trailer");
    }

    @Override
    public void onFinish() {
      long duration = System.currentTimeMillis() - startTime;
      String msg = MessageFormat.format("File processed in {0} ms", duration);
      System.out.println(msg);
    }
  }

  private static class StepProcessHandler extends CapitalOneBlockHandler {
    @Override
    public void onEntryEvent(List<String> entry) {
      for (int i = 0; i < entry.size(); i++) {
        if (i == 0) {

        } else if (i == entry.size() - 1) {

        } else {
          CapitalOneFileEmployee salaried = new CapitalOneFileEmployee(entry.get(i));
          System.out.println(salaried);
        }
      }
    }
  }

  private static class CapitalOneFileEmployee {
    private static final Currency CAD = Currency.getInstance(Locale.CANADA);
    private String transactionCode;
    private String routingNumber;
    private String checkDigit;
    private String accountNumber;
    private MoneyAmount amount;
    private String partyName;

    public CapitalOneFileEmployee(String line) {
      transactionCode = line.substring(1, 3);
      routingNumber = line.substring(3, 11);
      checkDigit = line.substring(11, 12);
      accountNumber = line.substring(12, 21);
      String amountText = line.substring(29, 39);
      amount = MoneyAmount.of(Long.valueOf(amountText) / 100.0, CAD);
      partyName = line.substring(54, 78).trim();
    }

    public String toString() {
      String msg = "    " + Introspection.toString(this);
      return msg;
    }
  }


}
