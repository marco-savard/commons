package com.marcosavard.awsmodule.about;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AboutController {
    @GetMapping("/about")
    public String about() {
        StringBuilder builder = new StringBuilder();
        builder.append("Application : awsmodule ");
        builder.append("Version : 1.0.18 ");
        builder.append("Date modified : 2025-05-23");
        return builder.toString();
    }
}
