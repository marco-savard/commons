package com.marcosavard.library.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMetric {

    public void iterateFiles(JavaParser parser, List<File> files) throws FileNotFoundException {
        for (File file : files) {
            iterateFile(parser, file);
        }
    }

    private void iterateFile(JavaParser parser, File file) throws FileNotFoundException {
        ParseResult<CompilationUnit> result = parser.parse(file);
        iterateCompilationUnit(result.getResult().orElse(null));
    }

    private void iterateCompilationUnit(CompilationUnit compilationUnit) {
        onCompilationUnit(compilationUnit);
        TypeDeclaration type = compilationUnit.getPrimaryType().orElse(null);

        if (type instanceof ClassOrInterfaceDeclaration claz) {
            iterateClass(compilationUnit, claz);
        }
    }

    private void iterateClass(CompilationUnit compilationUnit, ClassOrInterfaceDeclaration claz) {
        onClassDeclaration(compilationUnit, claz);
        List<MethodDeclaration> methods = claz.getMethods();

        for (MethodDeclaration method : methods) {
            iterateMethod(method);
        }
    }

    private void iterateMethod(MethodDeclaration method) {
        onMethodDeclaration(method);
    }

    protected void onCompilationUnit(CompilationUnit compilationUnit) {
    }

    protected void onClassDeclaration(CompilationUnit compilationUnit, ClassOrInterfaceDeclaration claz) {
    }

    protected void onMethodDeclaration(MethodDeclaration method) {
    }

    public abstract List<ResultSet> getResults() ;

    public static class ResultSet {
        private Node node;
        private List<Result> results = new ArrayList<>();
        private List<Annotation> annotations = new ArrayList<>();

        public ResultSet(Node node) {
            this.node = node;
        }

        public void addResult(Result result) {
            results.add(result);
        }

        public void addAnnotation(Annotation annotation) {
            if (! annotation.values.isEmpty()) {
                annotations.add(annotation);
            }
        }

        @Override
        public String toString() {
            String annotationList = annotations.isEmpty() ? "" : annotations.toString();

            if (node instanceof PackageDeclaration pack) {
                return MessageFormat.format("{0} : {1} {2}", pack.getName().asString(), results, annotationList);
            } else if (node instanceof TypeDeclaration<?> type) {
                return MessageFormat.format("{0} : {1} {2}", type.getNameAsString(), results, annotationList);
            } else if (node instanceof MethodDeclaration method) {
                TypeDeclaration parent = (TypeDeclaration)method.getParentNode().orElse(null);
                String name = method.getNameAsString();
                return MessageFormat.format("{0}.{1}() : {2} {3}", parent.getNameAsString(), name, results, annotationList);
            } else {
                return MessageFormat.format("{0} : {1} {2}", node.toString(), results, annotationList);
            }
        }
    }

    public static record Result(String name, Number value) {
        @Override
        public String toString() {
            return MessageFormat.format("{0}={1}", name, value);
        }
    }

    public static record Annotation(String name, List<String> values) {
        @Override
        public String toString() {
            String joined = String.join(", ", values);
            return values.isEmpty() ? "" : MessageFormat.format("{0}={1}", name, joined);
        }
    }
}
