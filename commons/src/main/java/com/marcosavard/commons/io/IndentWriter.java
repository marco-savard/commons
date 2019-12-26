package com.marcosavard.commons.io;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * An extension of PrintWriter that support indent() and unindent() methods.
 * 
 * @author Marco
 *
 */
public class IndentWriter extends PrintWriter {
  private static final int DEFAULT_INDENTATION = 2;

  private int level = 0;
  private int indentation;
  private boolean newLine = true;

  /**
   * Create an IndentWriter.
   * 
   * @param pw the parent writer
   * @param indentation number of spaces of indentation (default value of 2 spaces)
   */
  public IndentWriter(Writer w, int indentation) {
    super(w);
    this.indentation = indentation;
  }

  public IndentWriter(Writer w) {
    this(w, DEFAULT_INDENTATION);
  }

  @Override
  public void println() {
    println("");
  }

  @Override
  public void println(Object line) {
    println(String.valueOf(line));
  }

  @Override
  public void println(String line) {
    if (newLine) {
      for (int i = 0; i < level * indentation; i++) {
        super.print(" ");
      }
    }

    super.print(line);
    super.println();
    newLine = true;
  }

  @Override
  public void print(String text) {
    if (newLine) {
      for (int i = 0; i < level * indentation; i++) {
        super.print(" ");
      }
    }

    super.print(String.valueOf(text));
    newLine = false;
  }

  /**
   * Increase the indentation.
   */
  public void indent() {
    level++;
  }

  /**
   * Decrease the indentation and write an empyy line.
   */
  public void unindent() {
    level--;
  }

}
