package com.marcosavard.library.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.marcosavard.commons.util.collection.UniqueList;

import java.util.*;

public class CouplingMetric extends AbstractMetric {
    private Map<CompilationUnit, List<String>> importsByCompilationUnits = new LinkedHashMap<>();

    @Override
    protected void onCompilationUnit(CompilationUnit compilationUnit) {
        NodeList<ImportDeclaration> imports = compilationUnit.getImports();
        List<String> importedClasses = new UniqueList<>();

        for (int i = 0; i < imports.size(); i++) {
            ImportDeclaration importDeclaration = imports.get(i);
            onImport(importedClasses, importDeclaration);
        }

        importsByCompilationUnits.put(compilationUnit, importedClasses);
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
            Set<String> usedExternalLibs = new TreeSet<>();
            PackageDeclaration pack = unit.getPackageDeclaration().orElse(null);
            String[] parts = pack.getName().asString().split("\\.");
            String domain = parts[0] + "." + parts[1];
            int systemCount = 0, internalCount = 0;

            for (String classname : importedClasses) {
                boolean isSystem = classname.startsWith("java.") || classname.startsWith("javax.");
                boolean isInternal =  classname.startsWith(domain);
                systemCount += isSystem ? 1 : 0;
                internalCount += isInternal ? 1 : 0;

                if (!isSystem && !isInternal) {
                    parts = classname.split("\\.");
                    usedExternalLibs.add(parts[0] + "." + parts[1]);
                }
            }

            int totalCount = importedClasses.size();
            int externalCount = totalCount - systemCount - internalCount;
            TypeDeclaration type = unit.getPrimaryType().orElse(null);
            ResultSet resultSet = new ResultSet(type);
            resultSet.addResult(new Result("System", systemCount));
            resultSet.addResult(new Result("Internal", internalCount));
            resultSet.addResult(new Result("External", externalCount));

            resultSet.addAnnotation(new Annotation("UsedExternalLibs", usedExternalLibs.stream().toList()));
            resultSets.add(resultSet);
        }

        return resultSets;
    }
}
