package com.marcosavard.webapp.controller;

import com.marcosavard.webapp.model.PojoModel;
import com.marcosavard.webapp.service.PojoGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.MessageFormat;

@Controller
public class PojoGenController {

    @Autowired
    private PojoGenService pojoGenService;

    @GetMapping("/pojogen")
    public String upload() {
        System.out.println("User get /pojogen");
        return "pojogen";
    }

}
