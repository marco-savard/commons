package com.marcosavard.webapp.controller;

import com.marcosavard.webapp.model.FileData;
import com.marcosavard.webapp.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

//@Controller
public class MetricResultController {
    @Autowired
    private FileInfoService fileInfoService;

    @GetMapping("/metric/result")
    public String fileinfo(HttpServletRequest request, Model model) {
        FileData fileData = fileInfoService.getFileData();
        model.addAttribute("fileData", fileData);

        return "metric-result";
    }
}
