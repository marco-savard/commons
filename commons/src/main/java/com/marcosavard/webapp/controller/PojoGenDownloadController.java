package com.marcosavard.webapp.controller;

import com.marcosavard.webapp.model.PojoModel;
import com.marcosavard.webapp.service.PojoGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class PojoGenDownloadController {
    @Autowired
    private PojoGenService pojoGenService;

    @GetMapping("/pojogen/download")
    public void download(HttpServletResponse response) {
        try {
            downloadFile(response);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void downloadFile(HttpServletResponse response) throws IOException {
        PojoModel pojoModel = pojoGenService.getPojoModel();
        String fileName = pojoModel.getGeneratedFile();
        response.setContentType("application/zip");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);

        OutputStream output = response.getOutputStream();
        writeContent(pojoModel, output);
        output.close();
    }

    private void writeContent(PojoModel pojoModel, OutputStream output) throws IOException {
        ZipOutputStream zos =  new ZipOutputStream(new BufferedOutputStream(output));
        Map<String, String> codeByClassName = pojoModel.getPojos();

        for (String className : codeByClassName.keySet()) {
            String filename = className + ".java";
            String code = codeByClassName.get(className);
            writeEntry(zos, filename, code);
        }

        zos.close();
    }

    private void writeEntry(ZipOutputStream zos, String entryName, String code) throws IOException {
        zos.putNextEntry(new ZipEntry(entryName));
        zos.write(code.getBytes());
        zos.closeEntry();
    }

    private String getExtention(File file) {
        String filename = file.getName();
        int idx = filename.lastIndexOf('.');
        String ext = (idx == -1) ? "" : filename.substring(idx);
        return ext;
    }
}
