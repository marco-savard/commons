package com.marcosavard.library.javaparser;

import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MethodLengthComplexity extends Metric {

  @Override
  public List<Stat> compute(File file, CompilationUnit compilationUnit) {
    List<Stat> stats = new ArrayList<>();
    NodeList<TypeDeclaration<?>> types = compilationUnit.getTypes();

    for (TypeDeclaration type : types) {
      computeType(stats, type);
    }

    return stats;
  }

  private void computeType(List<Stat> stats, TypeDeclaration type) {
    List<MethodDeclaration> methods = type.getMethods();

    for (MethodDeclaration method : methods) {
      computeMethod(stats, type, method);
    }
  }

  private void computeMethod(List<Stat> stats, TypeDeclaration type, MethodDeclaration method) {
    String visibility = getMethodVisibility(method);
    int methodArity = computeMethodArity(method);
    int bodyLength = computeMethodLength(method);

    String name = type.getNameAsString() + "." + method.getNameAsString() + "()";
    String[] results =
        new String[] { //
          visibility, //
          Integer.toString(methodArity), //
          Integer.toString(bodyLength)
        };
    Stat stat = new Stat(name, results);
    stats.add(stat);
  }

  private String getMethodVisibility(MethodDeclaration method) {
    String visibility = "+";

    if (method.isPrivate()) {
      visibility = "-";
    } else if (method.isProtected()) {
      visibility = "#";
    }

    return visibility;
  }

  private int computeMethodArity(MethodDeclaration method) {
    return method.getParameters().size();
  }

  private int computeMethodLength(MethodDeclaration method) {
    BlockStmt blockStmt = method.getBody().orElse(null);
    int bodyLength = 0;

    if (blockStmt != null) {
      Position begin = blockStmt.getBegin().orElse(null);
      Position end = blockStmt.getEnd().orElse(null);
      int beginLine = (begin == null) ? 0 : begin.line;
      int endLine = (end == null) ? 0 : end.line;
      bodyLength = endLine - beginLine;
    }

    return bodyLength;
  }

  @Override
  public Stat getTotal() {
    Stat stat = new Stat("Total", new String[] {"", "", "", ""});
    return stat;
  }
}
