package com.marcosavard.commons.lang;

import com.marcosavard.commons.text.encoding.AccentCoding;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {
  private static final char eacute = '\u00e9';

  @Test
  public void testCamelToUnderscore() {
    Assert.assertEquals("PHONE_QUALIFIER", StringUtil.camelToUnderscore("phoneQualifier"));
  }

  @Test
  public void testUnderscoreToCamel() {
    Assert.assertEquals("phoneQualifier", StringUtil.underscoreToCamel("PHONE_QUALIFIER"));
  }

  @Test
  public void testCapitalize() {
    Assert.assertEquals("", StringUtil.capitalize((String) null));
    Assert.assertEquals("", StringUtil.capitalize(""));
    Assert.assertEquals("B", StringUtil.capitalize("b"));
    Assert.assertEquals("Be", StringUtil.capitalize("be"));
  }

  @Test
  public void testEqualsIgnoreAccents() {
    boolean result = StringUtil.equalsIgnoreAccents("montreal", "montr\u00e9al");
    Assert.assertEquals(true, result);

    result = StringUtil.equalsIgnoreAccents("montreal", "Montr" + eacute + "al");
    Assert.assertEquals(false, result);

    result = StringUtil.equalsIgnoreAccents("montreal", AccentCoding.of("Montre'al"));
    Assert.assertEquals(false, result);

    result = StringUtil.equalsIgnoreCaseAndAccents("montreal", AccentCoding.of("Montre'al"));
    Assert.assertEquals(true, result);
  }

  @Test
  public void testEqualsIgnoreAccentsNullsafe() {
    boolean result = StringUtil.equalsIgnoreCaseAndAccents("montreal", null);
    Assert.assertEquals(false, result);

    result = StringUtil.equalsIgnoreCaseAndAccents(null, "Montr�al");
    Assert.assertEquals(false, result);

    result = StringUtil.equalsIgnoreCaseAndAccents("", null);
    Assert.assertEquals(false, result);

    result = StringUtil.equalsIgnoreCaseAndAccents(null, null);
    Assert.assertEquals(true, result);
  }

  @Test
  public void testUncapitalize() {
    Assert.assertEquals("", StringUtil.uncapitalize((String) null));
    Assert.assertEquals("", StringUtil.uncapitalize(""));
    Assert.assertEquals("b", StringUtil.uncapitalize("B"));
    Assert.assertEquals("be", StringUtil.uncapitalize("Be"));
  }
}
