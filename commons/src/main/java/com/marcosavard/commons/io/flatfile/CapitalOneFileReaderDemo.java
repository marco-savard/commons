package com.marcosavard.commons.io.flatfile;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.marcosavard.commons.io.line.RecordLine;
import com.marcosavard.commons.lang.reflect.Introspection;

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
    private PayrollLine currentPayroll;

    @Override
    public void onEntryEvent(List<String> entry) {

      for (int i = 0; i < entry.size(); i++) {
        if (i == 0) {
          // start
          currentPayroll = new PayrollLine(entry.get(i));
        } else if (i < entry.size() - 1) {
          // middle
          EmployeePayLine employeePay = new EmployeePayLine(entry.get(i));
          currentPayroll.addPay(employeePay);
        } else {
          // end
          System.out.println(currentPayroll);
          currentPayroll = null;
        }
      }
    }
  }

  private static class PayrollLine extends RecordLine {
    String serviceClassCode = ofString(1, 4);
    String companyName = ofString(4, 20).trim();
    String discretionaryData = ofString(20, 40).trim();
    String companyIdentification = ofString(40, 50);
    String standardEntryClassCode = ofString(50, 53);
    String companyEntryDescription = ofString(53, 63).trim();
    LocalDate companyDescriptiveDate = ofLocalDate(63, 69, "yyMMdd");
    LocalDate effectiveEntryDate = ofLocalDate(69, 75, "yyMMdd");
    String originalStatusCode = ofString(78, 79);
    String originatingDFIIdentification = ofString(79, 87);
    List<EmployeePayLine> employeePays = new ArrayList<>();

    public PayrollLine(String line) {
      super.parseLine(line);
    }

    @Override
    public String toString() {
      String msg = Introspection.toString(this);
      return msg;
    }

    public void addPay(EmployeePayLine employeePay) {
      employeePays.add(employeePay);
    }
  }

  private static class EmployeePayLine extends RecordLine {
    String transactionCode = ofString(1, 3);
    String routingNumber = ofString(3, 11);
    int checkDigit = ofInteger(11, 12);
    String accountNumber = ofString(12, 21);
    double amountValue = ofDouble(29, 39) / 100.0;
    MoneyAmount amount = (MoneyAmount) ofType(29, 39, MoneyAmount.class);
    String partyName = ofString(54, 78).trim();

    public EmployeePayLine(String line) {
      super.parseLine(line);
    }

    @Override
    public String toString() {
      String msg = Introspection.toString(this);
      return msg;
    }
  }

  private static class MoneyAmount {
    private double moneyAmout = 0;

    public MoneyAmount(String line) {
      moneyAmout = Long.parseLong(line) / 100.0;
    }

    @Override
    public String toString() {
      String msg = MessageFormat.format("$ {0}", String.format("%.2f", moneyAmout));
      return msg;
    }
  }
}
