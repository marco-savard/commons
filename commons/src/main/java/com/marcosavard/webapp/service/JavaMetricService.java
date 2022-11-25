package com.marcosavard.webapp.service;

import com.github.javaparser.JavaParser;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;
import java.util.Map;

@Service
public class JavaMetricService {
    private JavaParser parser;

    public JavaMetricService() {
       parser = new JavaParser();
    }

    public void process(Reader reader, Map<String, String> fileProperties) {

    }

}
