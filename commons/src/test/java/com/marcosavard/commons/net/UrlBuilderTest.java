package com.marcosavard.commons.net;

import junit.framework.Assert;
import org.junit.Test;

public class UrlBuilderTest {

  @Test
  public void buildUrlWithNoParameters() {
    String url = UrlBuilder.of("domain.com").toString();
    Assert.assertEquals("domain.com", url);
    System.out.println("url = " + url);
  }

  @Test
  public void buildUrlWithOneParameter() {
    String url = UrlBuilder.of("domain.com").with("param1", Boolean.TRUE).toString();
    Assert.assertEquals("domain.com?param1=true", url);
    System.out.println("url = " + url);
  }

  @Test
  public void buildUrlWithTwoParameters() {
    String url =
        UrlBuilder.of("domain.com")
            .with("param1", Boolean.TRUE)
            .with("param2", Boolean.TRUE)
            .toString();
    Assert.assertEquals("domain.com?param1=true&param2=true", url);
    System.out.println("url = " + url);
  }

  @Test
  public void buildUrlWithNoDomain() {
    String url = UrlBuilder.of("").with("param1", Boolean.TRUE).toString();
    Assert.assertEquals("param1=true", url);
    System.out.println("url = " + url);
  }

  @Test
  public void buildUrlWithNullValue() {
    String url = UrlBuilder.of("domain.com").with("param1", null).toString();
    Assert.assertEquals("domain.com?param1=null", url);
    System.out.println("url = " + url);
  }
}
