package com.marcosavard.webapp.service;

import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.domain.purchasing.model.PurchaseOrderModel;
import com.marcosavard.webapp.model.PojoModel;

import java.io.*;
import java.util.List;

public class PojoGenServiceTest {

    public static void main(String[] args) {
        try {
            File sourceFile = getSourceFile(PurchaseOrderModel.class);
            PojoModel pojoModel = new PojoModel(sourceFile.getName(), sourceFile.length());
            Reader reader = new FileReader(sourceFile);
            BufferedReader br = new BufferedReader(reader);
            String line = null;

            do {
                line = br.readLine();
                pojoModel.printLine(line);
            } while (line != null);

           br.close();
           PojoGenService service = new PojoGenService();
           service.store(pojoModel);

           service.process(pojoModel);

            File outputFolder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File getSourceFile(Class claz) {
        List<File> sourceFiles = getSourceFiles(claz);
        String filename = claz.getSimpleName() + ".java";
        File sourceFile =
                sourceFiles.stream().filter(f -> f.getName().equals(filename)).findFirst().orElse(null);
        return sourceFile;
    }

    private static List<File> getSourceFiles(Class claz) {
        File sourceFolder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");
        Package pack = claz.getPackage();
        List<File> sourceFiles = FileSystem.getSourceFiles(sourceFolder, pack);
        sourceFiles.addAll(sourceFiles);
        return sourceFiles;
    }

}
