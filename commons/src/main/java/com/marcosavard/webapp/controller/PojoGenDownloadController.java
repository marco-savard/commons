package com.marcosavard.webapp.controller;

import com.marcosavard.commons.io.FileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class PojoGenDownloadController {
    @GetMapping("/pojogen/download")
    public void download(HttpServletResponse response) {
        try {
            File tmpFolder = FileSystem.getTemporaryFolder();
            File pojogen = new File(tmpFolder, "pojogen");
           // File textFile = new File(pojogen, "text.txt");
            downloadFile(response, pojogen);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void downloadFile(HttpServletResponse response, File pojogen) throws IOException {
        String fileName = "classes.zip";
        response.setContentType("test/plain");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);

        OutputStream output = response.getOutputStream();
        writeContent(output);
        output.close();

        //  response.getWriter().close();

        /*
        File[] files = pojogen.listFiles();
        List<File> javaFiles = Arrays.stream(files).filter(f -> getExtention(f).equals(".java")).collect(Collectors.toList());
        File file = javaFiles.get(0);

        String fileName = file.getName();


        Reader r = new FileReader(file);
        BufferedReader br = new BufferedReader(r);
        String line;


        do {
            line = br.readLine();
            if (line != null) {
                response.getWriter().println(line);
            }
        } while (line != null);

        br.close();
        r.close();


         */
    }

    private void writeContent(OutputStream output) throws IOException {
        ZipOutputStream zos =  new ZipOutputStream(new BufferedOutputStream(output));

        writeEntry(zos, "Class.java");

        zos.close();
    }

    private void writeEntry(ZipOutputStream zos, String entryName) throws IOException {
        zos.putNextEntry(new ZipEntry(entryName));
        zos.write("Hello World!".getBytes());
        zos.closeEntry();
    }

    private String getExtention(File file) {
        String filename = file.getName();
        int idx = filename.lastIndexOf('.');
        String ext = (idx == -1) ? "" : filename.substring(idx);
        return ext;
    }
}
