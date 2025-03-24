package com.marcosavard.library.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.*;

public class EncapsulationMetric extends AbstractMetric {
    private Map<PackageDeclaration, List<ClassOrInterfaceDeclaration>> classesByPackages = new LinkedHashMap<>();

    @Override
    protected void onClassDeclaration(CompilationUnit compilationUnit, ClassOrInterfaceDeclaration claz) {
        claz.isPublic();

        PackageDeclaration pack = compilationUnit.getPackageDeclaration().orElse(null);
        List<ClassOrInterfaceDeclaration> classes = classesByPackages.get(pack);

        if (classes == null) {
            classes = new ArrayList<>();
            classesByPackages.put(pack, classes);
        }

        classes.add(claz);
    }

    public List<ResultSet> getResults() {
        List<ResultSet> resultSets = new ArrayList<>();

        for (PackageDeclaration pack : classesByPackages.keySet()) {
            List<ClassOrInterfaceDeclaration> classes = classesByPackages.get(pack);
            int publicCount = 0;

            for (ClassOrInterfaceDeclaration claz : classes) {
                publicCount += claz.isPublic() ? 1 : 0;
            }

            ResultSet resultSet = new ResultSet(pack.getName().asString());
            resultSet.add(new Result("Public", publicCount));
            resultSet.add(new Result("Total", classes.size()));
            resultSets.add(resultSet);
        }

        return resultSets;
    }


}
