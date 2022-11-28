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
            File generatedFile = generatedFiles.get(0);

            /*
            Reader r = new FileReader(generatedFile);
            BufferedReader br = new BufferedReader(r);
            String filename = generatedFile.getName();

            File textFile = new File(pojogen, filename);
            Writer w = new FileWriter(textFile);
            PrintWriter pw = new PrintWriter(w);

            String line;

            do {
                line = br.readLine();
                if (line != null) {
                    pw.println(line);
                }

            } while (line != null);

            //TODO generate text files
            //TODO zip them
            //TODO put the zip in temp

            br.close();
            pw.close();
            w.close();*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //create a file in /temp
    }

}
