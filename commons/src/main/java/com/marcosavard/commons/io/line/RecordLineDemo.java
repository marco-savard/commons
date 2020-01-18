package com.marcosavard.commons.io.line;

import java.text.MessageFormat;
import java.time.LocalDate;
import com.marcosavard.commons.lang.reflect.Introspection;

public class RecordLineDemo {
  private static final String PAYROLL_LINE =
      "5200DEMO COMPANY 2  PAYROLL             1999999999PPDPAYROLL   031231031231   1065000090000001";

  private static final String EMPLOYEE_PAY_LINE =
      "622065000090123456789        00000102001112223336     MARY JONES              0065000090000002";

  public static void main(String[] args) {
    RecordLine payLoad = new PayrollLine(PAYROLL_LINE);
    RecordLine employeePay = new EmployeePayLine(EMPLOYEE_PAY_LINE);
    System.out.println(payLoad);
    System.out.println(employeePay);
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

    public PayrollLine(String line) {
      super.parseLine(line);
    }

    @Override
    public String toString() {
      String msg = Introspection.toString(this);
      return msg;
    }
  }

  private static class EmployeePayLine extends RecordLine {
    String transactionCode = ofString(1, 3);
    String routingNumber = ofString(3, 11);
    int checkDigit = ofInteger(11, 12);
    String accountNumber = ofString(12, 21);
    MoneyAmount moneyAmount = (MoneyAmount) ofType(29, 39, MoneyAmount.class);
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
