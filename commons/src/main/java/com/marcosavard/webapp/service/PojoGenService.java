package com.marcosavard.webapp.service;

import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.lang.reflect.meta.PojoGenerator;
import com.marcosavard.library.javaparser.generate.SourceBasedPojoGenerator;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class PojoGenService {

    public PojoGenService() {
    }

    public void process(Reader reader) {
        try {
            File tmpFolder = FileSystem.getTemporaryFolder();
            File pojogen = new File(tmpFolder, "pojogen");
            pojogen.mkdir();

            //generate Java files
            PojoGenerator pojoGenerator = new SourceBasedPojoGenerator(pojogen, reader);
            List<File> generatedFiles = pojoGenerator.generate();

            //TODO generate text files
            //TODO zip them
            //TODO put the zip in temp

            File textFile = new File(pojogen, "text.txt");
            Writer w = new FileWriter(textFile);
            PrintWriter pw = new PrintWriter(w);
            pw.println("Hello");
            pw.close();
            w.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //create a file in /temp
    }

}
