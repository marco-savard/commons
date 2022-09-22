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
        List<String[]> lines = new ArrayList<>();

        for (File file : parsedFiles.keySet()) {
            double[] stats = metric.compute(file, parsedFiles.get(file));
            String[] line = new String[] {file.getName(), stats[0]+" %", Double.toString(stats[1]), Double.toString(stats[2]), Double.toString(stats[3])};
            lines.add(line);

            String msg = MessageFormat.format("  {0} is {1} % generic ({2} of {3}).", file.getName(), stats[0], stats[1], stats[2], stats[3]);
          //  System.out.println(msg);
        }

        double[] total = metric.getTotal();
        String[] line = new String[] {"Total", total[0]+" %", Double.toString(total[1]), Double.toString(total[2]), Double.toString(total[3])};
        lines.add(line);

        List<String> rows = formatLines(lines);

        for (String row : rows) {
            System.out.println(row);
        }

        String msg = MessageFormat.format("Total : {0} % generic ({1} of {2}).", total[0], total[1], total[2]);
      //  System.out.println(msg);
        System.out.println(lines);
    }

    private static List<String> formatLines(List<String[]> lines) {
        List<Integer> widths = new SafeArrayList<>(0);
        List<String> formatted = new ArrayList<>();

        //compute widths
        for (String[] line : lines) {
            for (int i=0; i<line.length; i++) {
                int width = widths.get(i);
                widths.set(i, Math.max(width, 1 + line[i].length()));
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
