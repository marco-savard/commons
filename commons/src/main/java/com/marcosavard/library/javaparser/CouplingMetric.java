package com.marcosavard.library.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.marcosavard.commons.util.collection.UniqueList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CouplingMetric extends AbstractMetric {
    private Map<CompilationUnit, List<String>> importsByCompilationUnits = new LinkedHashMap<>();

    @Override
    protected void onCompilationUnit(CompilationUnit compilationUnit) {
        NodeList<ImportDeclaration> imports = compilationUnit.getImports();
        List<String> importedClasses = new UniqueList<>();
        PackageDeclaration pack = compilationUnit.getPackageDeclaration().orElse(null);
        String[] parts = pack.getName().asString().split("\\.");
        String internalName = parts[0] + "." + parts[1];
        int systemCount = 0, internalCount = 0;

        for (int i = 0; i < imports.size(); i++) {
            ImportDeclaration importDeclaration = imports.get(i);
            onImport(importedClasses, importDeclaration);
        }

        importsByCompilationUnits.put(compilationUnit, importedClasses);

        for (String classname : importedClasses) {
            boolean isSystem = classname.startsWith("java.") || classname.startsWith("javax.");
            boolean isInternal =  classname.startsWith(internalName);
            systemCount += isSystem ? 1 : 0;
            internalCount += isInternal ? 1 : 0;
        }
    }

    private void onImport(List<String> importedClasses, ImportDeclaration importDeclaration) {
        String name = importDeclaration.getNameAsString();

        if (importDeclaration.isStatic()) {
            int idx = name.lastIndexOf('.');
            String classname = name.substring(0, idx);
            importedClasses.add(classname);
        } else {
            importedClasses.add(name);
        }
    }

    @Override
    public List<ResultSet> getResults() {
        List<ResultSet> resultSets = new ArrayList<>();

        for (CompilationUnit unit : importsByCompilationUnits.keySet()) {
            List<String> importedClasses = importsByCompilationUnits.get(unit);
            PackageDeclaration pack = unit.getPackageDeclaration().orElse(null);
            String[] parts = pack.getName().asString().split("\\.");
            String internalName = parts[0] + "." + parts[1];
            int systemCount = 0, internalCount = 0;

            for (String classname : importedClasses) {
                boolean isSystem = classname.startsWith("java.") || classname.startsWith("javax.");
                boolean isInternal =  classname.startsWith(internalName);
                systemCount += isSystem ? 1 : 0;
                internalCount += isInternal ? 1 : 0;
            }

            int totalCount = importedClasses.size();
            int externalCount = totalCount - systemCount - internalCount;
            String classname = unit.getPrimaryTypeName().orElse(null);
            ResultSet resultSet = new ResultSet(classname);
            resultSet.add(new Result("System", systemCount));
            resultSet.add(new Result("Internal", internalCount));
            resultSet.add(new Result("External", externalCount));
            resultSets.add(resultSet);
        }

        return resultSets;
    }
}
