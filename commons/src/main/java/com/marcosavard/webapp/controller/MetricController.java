package com.marcosavard.webapp.controller;

import com.marcosavard.webapp.model.FileData;
import com.marcosavard.webapp.service.FileInfoService;
import com.marcosavard.webapp.service.JavaMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MetricController {
    private static final long MAX_FILE_SIZE = 50 * 1024;
    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private JavaMetricService javaMetricService;

    @GetMapping("/metric")
    public String upload() {
        return "metric";
    }

    @PostMapping("/metric/upload")
    public String uploadFile(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file) {

        long fileSize = file.getSize();
        if (fileSize > MAX_FILE_SIZE) {
            throw new FileTooLargeException(file);
        }

        String filename = file.getOriginalFilename();
        int idx = filename.lastIndexOf('.');
        String ext = filename.substring(idx);

        if (!".java".equals(ext)) {
            throw new NotJavaFileException(file);
        }

        processFile(file);
        return "/metric";
    }

    private void processFile(MultipartFile file) {
        try {
            Map<String, String> fileProperties = new HashMap<>();
            fileProperties.put("fileName", file.getOriginalFilename());
            fileProperties.put("fileSize", file.getSize() + " B");

            InputStream input = file.getInputStream();
            Reader reader = new InputStreamReader(input);
            FileData fileData = new FileData(file.getOriginalFilename(), file.getSize());
            javaMetricService.process(reader, fileData);
            reader.close();

            fileInfoService.process(fileData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class FileTooLargeException extends RuntimeException {
        public FileTooLargeException(MultipartFile file) {
            super(buildMessage(file));
        }

        private static String buildMessage(MultipartFile file) {
            String filename = file.getOriginalFilename();
            long fileSize = file.getSize() / 1024;
            String msg = MessageFormat.format("File {0} has {1} KB and is too large to be processed ({2} KB maximum)",
                    filename,
                    fileSize,
                    (MAX_FILE_SIZE / 1024));
            return msg;
        }
    }

    private static class NotJavaFileException extends RuntimeException {
        public NotJavaFileException(MultipartFile file) {
            super(buildMessage(file));
        }

        private static String buildMessage(MultipartFile file) {
            String filename = file.getOriginalFilename();
            String msg = MessageFormat.format("File {0} is not a Java file", filename);
            return msg;
        }
    }


}
