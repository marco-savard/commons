package com.marcosavard.library.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.marcosavard.commons.App;
import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.util.collection.SafeArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MetricDemo {
    private static final File sourceFolder = new File("C:/Users/User/IdeaProjects/commons/commons/src/main/java");

    public static void main(String[] args) {
        List<File> sourceFiles = getSourceFiles();
        List<File> selection = sourceFiles.stream().filter(f -> ! f.getName().endsWith("Demo.java")).toList();
        selection = selection.stream().filter(f -> ! f.getPath().contains("meta")).toList();
        Map<File, CompilationUnit> parsedFiles = parseFiles(selection);
        Metric metric = new GenericityMetric();
        List<String[]> rows = new ArrayList<>();

        for (File file : parsedFiles.keySet()) {
            double[] stats = metric.compute(file, parsedFiles.get(file));
            String[] row = new String[] {file.getName(), stats[0]+" %", Integer.toString((int)stats[1]), Integer.toString((int)stats[2]), Integer.toString((int)stats[3])};
            rows.add(row);
        }

        double[] total = metric.getTotal();
        String percent = (int)(total[0] * 100)/100.0 + " %";
        String[] row = new String[] {"Total", percent, Integer.toString((int)total[1]), Integer.toString((int)total[2]), Integer.toString((int)total[3])};
        rows.add(row);

        List<String> lines = formatLines(rows);

        for (String line : lines) {
            System.out.println(line);
        }
    }

    private static List<String> formatLines(List<String[]> lines) {
        List<Integer> widths = new SafeArrayList<>(0);
        List<String> formatted = new ArrayList<>();

        //compute widths
        for (String[] line : lines) {
            for (int i=0; i<line.length; i++) {
                int width = widths.get(i);
                widths.set(i, Math.max(width, 2 + line[i].length()));
            }
        }

        for (String[] line : lines) {
            List<String> padded = new ArrayList<>();
            for (int i=0; i<line.length; i++) {
                padded.add(StringUtil.padRight(line[i], widths.get(i)));
            }
            String joined = String.join("", padded);
            formatted.add(joined);
        }


        return formatted;
    }

    private static List<File> getSourceFiles() {
        Package pack = App.class.getPackage();
        List<File> sourceFiles = FileSystem.getSourceFiles(sourceFolder, pack);
        sourceFiles.addAll(sourceFiles);

        return sourceFiles;
    }

    private static Map<File, CompilationUnit> parseFiles(List<File> sourceFiles) {
        Map<File, CompilationUnit> parsedFiles = new TreeMap<>();
        JavaParser parser = new JavaParser();

        for (File file : sourceFiles) {
            try {
                ParseResult<CompilationUnit> result = parser.parse(file);
                CompilationUnit unit = result.getResult().orElse(null);
                parsedFiles.put(file, unit);
            } catch (FileNotFoundException e) {
                //ignore
            }
        }

        return parsedFiles;
    }


}
