package com.marcosavard.webapp.controller;

import com.marcosavard.commons.lang.StringUtil;
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
import java.util.HashMap;
import java.util.Map;

@Controller
public class PojoGenController {
    private static final long MAX_FILE_SIZE = 50 * 1024;

    @Autowired
    private PojoGenService pojoGenService;

    @GetMapping("/pojogen")
    public String upload() {
        return "pojogen";
    }

    @PostMapping("/pojogen/upload")
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
        return "pojogen";
    }

    private void processFile(MultipartFile file) {
        try {
            String sourceFile = file.getOriginalFilename();
            PojoModel pojoModel = new PojoModel(sourceFile, file.getSize());
            String generatedFile = pojoModel.getGeneratedFile();
            String model = pojoModel.getModelAsString();
            long sourceLoc = StringUtil.countCharacters(model, '\n');

            /*
            Map<String, String> fileProperties = new HashMap<>();
            fileProperties.put("sourceLoc", Long.toString(sourceLoc));
            fileProperties.put("sourceFile", sourceFile);
            fileProperties.put("generatedFile", generatedFile);
            fileProperties.put("fileSize", file.getSize() + " B");*/

            InputStream input = file.getInputStream();
            Reader reader = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(reader);
            String line;

            do {
                line = br.readLine();

                if (line != null) {
                    pojoModel.printLine(line);
                }
            } while (line != null);

            pojoModel.close();
            br.close();
            reader.close();

            pojoGenService.store(pojoModel);
            pojoGenService.process(pojoModel);
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
