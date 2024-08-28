package com.marcosavard.webapp.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// @Controller
public class TestController {
  private static final String TIME_PATTERN = "yyyy/MMM/dd hh:mm:ss";
  private String startTime;

  public TestController() {
    LocalDateTime now = LocalDateTime.now();
    startTime = DateTimeFormatter.ofPattern(TIME_PATTERN).format(now);
  }

  /*
  @GetMapping("/test")
  public String test(Model model) {
      System.out.println("User get /test");
      LocalDateTime now = LocalDateTime.now();
      String responseTime = DateTimeFormatter.ofPattern(TIME_PATTERN).format(now);
      model.addAttribute("startTime", startTime + " v01");
      model.addAttribute("responseTime", responseTime);
      return "test";
  }

   */
}
