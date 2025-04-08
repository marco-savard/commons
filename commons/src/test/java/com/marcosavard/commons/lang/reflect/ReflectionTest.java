package com.marcosavard.commons.lang.reflect;

import com.marcosavard.commons.debug.Console;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class ReflectionTest {

  @Test
  public void testInstantiationByReflection() {
    // java.awt
    instantiate(Color.class);
    instantiate(Font.class);

    // java.io and java.nio
    instantiate(File.class);

    // java.lang
    instantiate(Boolean.class);
    instantiate(Byte.class);
    instantiate(Character.class);
    instantiate(Integer.class);
    instantiate(Integer[].class);
    instantiate(Long.class);
    instantiate(Float.class);
    instantiate(Double.class);
    instantiate(Short.class);
    instantiate(String.class);
    instantiate(String[].class);

    // java.math
    instantiate(BigDecimal.class);
    instantiate(BigInteger.class);
    instantiate(Random.class);

    // java.net
    instantiate(InetAddress.class);
    instantiate(URI.class);

    // java.time
    instantiate(Date.class);
    instantiate(LocalDate.class);
    instantiate(LocalDateTime.class);
    instantiate(LocalTime.class);
    instantiate(ZonedDateTime.class);
    instantiate(ZoneOffset.class);

    // java.util
    instantiate(ArrayList.class);
    instantiate(HashMap.class);
    instantiate(LinkedList.class);
    instantiate(Locale.class);
    instantiate(Pattern.class);
  }

  private static void instantiate(Class claz) {
    String pattern = "{0} : {1}";
    Object instance = Reflection.instantiate(claz);
    String value =
        (instance instanceof CharSequence) ? "\"" + instance + "\"" : instance.toString();
    Console.println(pattern, claz, value);
    Assert.assertEquals(true, instance != null);
  }
}
