package com.marcosavard.library.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;

import java.io.File;

public class GenericityMetric extends Metric {
    double[] total = new double[4];

    public double[] compute(File file, CompilationUnit compilationUnit) {
        NodeList<ImportDeclaration> list = compilationUnit.getImports();
        int count = list.size();
        int genericCount = 0;

        for (int i = 0; i < count; i++) {
            ImportDeclaration importDeclaration = list.get(i);
            String s = importDeclaration.getNameAsString();
            boolean generic = s.startsWith("java.") || s.startsWith("javax.");
            genericCount += generic ? 1 : 0;
        }

        double percent = (count == 0) ? 100 : (genericCount * 100) / count;
        total[1] += genericCount;
        total[2] += (count - genericCount);
        total[3] += count;
        double[] metrics = new double[] {percent, genericCount, (count - genericCount), count};
        return metrics;
    }

    public double[] getTotal() {
        total[0] = (total[3] == 0) ? 100 : (total[1] * 100) / total[3];
        return total;
    }
}
