package com.marcosavard.library.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.marcosavard.commons.util.collection.UniqueList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GenericityMetric extends Metric {
    double[] total = new double[4];

    public double[] compute(File file, CompilationUnit compilationUnit) {
        NodeList<ImportDeclaration> imports = compilationUnit.getImports();
        List<String> importedClasses = new UniqueList<>();
        int genericCount = 0;

        for (int i = 0; i < imports.size(); i++) {
            ImportDeclaration importDeclaration = imports.get(i);
            compute(importedClasses, importDeclaration);
        }

        for (String classname : importedClasses) {
            boolean generic = classname.startsWith("java.") || classname.startsWith("javax.");
            genericCount += generic ? 1 : 0;
        }

        int count = importedClasses.size();
        double percent = (count == 0) ? 100 : (genericCount * 100) / count;
        total[1] += genericCount;
        total[2] += (count - genericCount);
        total[3] += count;
        double[] metrics = new double[] {percent, genericCount, (count - genericCount), count};
        return metrics;
    }

    private void compute(List<String> importedClasses, ImportDeclaration importDeclaration) {
        String name = importDeclaration.getNameAsString();

        if (importDeclaration.isStatic()) {
            int idx = name.lastIndexOf('.');
            String classname = name.substring(0, idx);
            importedClasses.add(classname);
        } else {
            importedClasses.add(name);
        }
    }

    public double[] getTotal() {
        total[0] = (total[3] == 0) ? 100 : (total[1] * 100) / total[3];
        return total;
    }
}
