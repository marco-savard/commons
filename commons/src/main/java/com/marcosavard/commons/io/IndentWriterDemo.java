package com.marcosavard.commons.io;

import com.marcosavard.commons.lang.reflect.Reflection;

import javax.swing.tree.TreeNode;
import java.io.PrintWriter;
import java.io.Writer;

public class IndentWriterDemo {

  public static void main(String[] args) {
    TreeNode root = Reflection.getJavaPackageTree();
    int indentation = 2;
    printIndentedList1(root, indentation);
    printIndentedList2(root, indentation);
  }

  //
  // IMPLEMENTATION 1 with PrintWriter
  //
  private static void printIndentedList1(TreeNode root, int indentation) {
    PrintWriter pw = new PrintWriter(System.out);
    int currentIndentation = 0;
    printChildren(pw, root, indentation, currentIndentation);
  }

  private static void printChildren(
      PrintWriter pw, TreeNode node, int indentation, int currentIndentation) {
    String indent = buildIndent(currentIndentation);
    pw.println(indent + node.toString());
    int count = node.getChildCount();

    if (count > 0) {
      currentIndentation += indentation;

      for (int i = 0; i < count; i++) {
        TreeNode child = node.getChildAt(i);
        printChildren(pw, child, indentation, currentIndentation);
      }

      pw.println();
    }

    pw.flush();
  }

  private static String buildIndent(int indentation) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < indentation; i++) {
      sb.append(" ");
    }

    return sb.toString();
  }

  //
  // IMPLEMENTATION 2 with IndentWriter
  //
  private static void printIndentedList2(TreeNode root, int indentation) {
    Writer w = new PrintWriter(System.out);
    IndentWriter iw = new IndentWriter(w, indentation);
    printChildren(iw, root);
  }

  private static void printChildren(IndentWriter iw, TreeNode node) {
    iw.println(node.toString());
    int count = node.getChildCount();

    if (count > 0) {
      iw.indent();

      for (int i = 0; i < count; i++) {
        TreeNode child = node.getChildAt(i);
        printChildren(iw, child);
      }

      iw.println();
      iw.unindent();
    }

    iw.flush();
  }
}
