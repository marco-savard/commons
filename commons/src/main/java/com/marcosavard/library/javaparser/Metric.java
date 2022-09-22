package com.marcosavard.library.javaparser;

import com.github.javaparser.ast.CompilationUnit;

import java.io.File;

public abstract class Metric {
    public abstract double[] compute(File file, CompilationUnit compilationUnit);

    public abstract double[] getTotal();
}
