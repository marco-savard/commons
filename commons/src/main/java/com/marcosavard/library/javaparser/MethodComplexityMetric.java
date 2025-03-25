package com.marcosavard.library.javaparser;

import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.util.ArrayList;
import java.util.List;

public class MethodComplexityMetric extends AbstractMetric {
    private List<ClassOrInterfaceDeclaration> classes = new ArrayList<>();

    protected void onClassDeclaration(CompilationUnit compilationUnit, ClassOrInterfaceDeclaration claz) {
        classes.add(claz);
    }

    @Override
    public List<ResultSet> getResults() {
        List<ResultSet> resultSets = new ArrayList<>();

        for (ClassOrInterfaceDeclaration claz : classes) {
            List<MethodDeclaration> methods = claz.getMethods();

            for (MethodDeclaration method : methods) {
                String name = claz.getNameAsString() + "." + method.getNameAsString() + "()";
                int methodArity = computeMethodArity(method);
                int bodyLength = computeMethodLength(method);

                ResultSet resultSet = new ResultSet(method);
                resultSet.addResult(new Result("arity", methodArity));
                resultSet.addResult(new Result("bodyLength", bodyLength));
                resultSets.add(resultSet);
            }
        }

        return resultSets;
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

    private int computeMethodArity(MethodDeclaration method) {
        return method.getParameters().size();
    }
}
