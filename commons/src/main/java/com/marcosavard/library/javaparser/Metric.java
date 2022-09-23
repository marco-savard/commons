package com.marcosavard.library.javaparser;

import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.util.List;

public abstract class Metric {

    public abstract List<Stat> compute(File file, CompilationUnit compilationUnit);

    public abstract Stat getTotal();

    public static class Stat {
        final String name;
        final String[] results;

        public Stat(String name, String[] results) {
            this.name = name;
            this.results = results;
        }
    }
}
