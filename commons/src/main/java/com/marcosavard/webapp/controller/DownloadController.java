package com.marcosavard.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class DownloadController {
    @GetMapping("/download")
    public void download(HttpServletResponse response) {
        try {
            String fileName = "result.txt";
            response.setContentType("test/plain");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", fileName);
            response.setHeader(headerKey, headerValue);
            response.getWriter().write("Result");
            response.getWriter().close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
