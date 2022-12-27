package com.marcosavard.webapp.service;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.marcosavard.commons.util.collection.UniqueList;
import com.marcosavard.webapp.model.FileData;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;

@Service
public class JavaMetricService {
    private JavaParser parser;

    public JavaMetricService() {
       parser = new JavaParser();
    }

    public void process(Reader reader, FileData fileData) {
        CompilationUnit compilationUnit = parser.parse(reader);
        computeImportMetrics(compilationUnit, fileData);
    }

    private void computeImportMetrics(CompilationUnit compilationUnit, FileData fileData) {
        NodeList<ImportDeclaration> imports = compilationUnit.getImports();
        int nbImports = imports.size();
        fileData.setNbImports(nbImports);

        List<String> importedClasses = new UniqueList<>();
        int genericCount = 0;

        for (int i = 0; i < nbImports; i++) {
            ImportDeclaration importDeclaration = imports.get(i);
            computeImport(importedClasses, importDeclaration);
        }

        for (String classname : importedClasses) {
            boolean generic = classname.startsWith("java.") || classname.startsWith("javax.");
            genericCount += generic ? 1 : 0;
        }

        int count = importedClasses.size();
        double percent = (count == 0) ? 100 : (genericCount * 100) / count;
        fileData.setGenericity(percent);
    }

    private void computeImport(List<String> importedClasses, ImportDeclaration importDeclaration) {
        String name = importDeclaration.getNameAsString();

        if (importDeclaration.isStatic()) {
            int idx = name.lastIndexOf('.');
            String classname = name.substring(0, idx);
            importedClasses.add(classname);
        } else {
            importedClasses.add(name);
        }
    }

}
