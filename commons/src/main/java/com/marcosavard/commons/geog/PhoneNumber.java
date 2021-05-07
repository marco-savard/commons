package com.marcosavard.commons.geog;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Random;
import com.marcosavard.commons.geog.ca.PostalCode;
import com.marcosavard.commons.lang.CharString;

/**
 * A class to store a North American phone number, with formatting and validating methods
 * 
 * @author Marco
 *
 */
@SuppressWarnings("serial")
public class PhoneNumber implements Serializable {
  public enum Format {
    BLANK_AND_DASH
  };

  private String _areaCode, _number, _extension;


  // required by GWT
  @SuppressWarnings("unused")
  private PhoneNumber() {}

  /**
   * Create a phone number with no extension.
   * 
   * @param fullNumber number with area code
   */
  public static PhoneNumber of(String fullNumber) {
    PhoneNumber phoneNumber = new PhoneNumber(fullNumber, null);
    return phoneNumber;
  }

  /**
   * Create a phone number with an extension.
   * 
   * @param fullNumber number with area code
   * @param extension optional
   */
  private PhoneNumber(String fullNumber, String extension) {
    String digits = CharString.of(fullNumber).stripNonDigit();

    _areaCode = digits.substring(0, 3);
    _number = digits.substring(3);
    _extension = extension;
  }

  public String getAreaCode() {
    return _areaCode;
  }

  public String getNumber() {
    return _number;
  }

  public String getExtension() {
    return _extension;
  }

  public String getNumberStart() {
    return _number.substring(0, 3);
  }

  public String getNumberEnd() {
    return _number.substring(3);
  }

  public static PhoneNumber parse(String text) {
    StringBuilder parsed = new StringBuilder();

    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      if (Character.isDigit(ch)) {
        parsed.append(ch);
      }
    }
    text = parsed.toString();

    int len = Math.min(text.length(), 3);
    String rc = text.substring(0, len);
    String number = text.substring(len);
    PhoneNumber phone = new PhoneNumber(rc, number);
    return phone;
  }

  @Override
  public String toString() {
    String s = _areaCode + _number;
    return s;
  }

  public String toDisplayString() {
    String s = toDisplayString("{0} {1}-{2}");
    return s;
  }

  public String toDisplayString(String pattern) {
    String s = MessageFormat.format(pattern,
        new Object[] {_areaCode, _number.substring(0, 3), _number.substring(3)});
    return s;
  }

  /**
   * Compare this phone number with that phone number.
   * 
   * @param thatPhoneNumber that phone number
   * @return comparison
   */
  public int compareTo(PhoneNumber thatPhoneNumber) {
    String s1 = this.toString();
    String s2 = thatPhoneNumber.toString();
    int comparison = s1.compareTo(s2);
    return comparison;
  }

  /**
   * Get a probable area code for a given postal code. Useful for error detection or to create
   * realistic test cases.
   * 
   * @param postalCode postal code
   * @return area code
   */
  public static String getProbableAreaCodeFor(PostalCode postalCode) {
    char c = postalCode.toString().charAt(0);
    int idx = c - 'A';
    String areaCode = AREA_CODES[idx];
    return areaCode;
  }

  private static final String[] AREA_CODES = new String[] {//
      "709", // A
      "782", // B
      "782", // C
      null, "506", // E
      null, "418", // G
      "514", // H
      null, "819", // J
      "613", // K
      "613", // L
      "416", // M
      "416", // N
      null, "807", // P
      null, "431", // R
      "636", // S
      "587", // T
      null, "236", // V
      null, "867", // X
      null, null};

  public static PhoneNumber random(String areaCode) {
    StringBuffer sf = new StringBuffer();
    Random r = new Random();
    sf.append(2 + r.nextInt(8));
    sf.append(r.nextInt(10));
    sf.append(r.nextInt(10));

    sf.append(r.nextInt(10));
    sf.append(r.nextInt(10));
    sf.append(r.nextInt(10));
    sf.append(r.nextInt(10));

    PhoneNumber phoneNumber = new PhoneNumber(areaCode, sf.toString());
    return phoneNumber;
  }



}
