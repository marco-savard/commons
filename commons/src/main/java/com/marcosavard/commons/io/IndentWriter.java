package com.marcosavard.commons.io;

import java.io.PrintWriter;

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
	
	/**
	 * Create an IndentWriter. 
	 * 
	 * @param pw the parent writer
	 * @param indentation number of spaces of indentation (default value of 2 spaces)
	 */
	public IndentWriter(PrintWriter pw, int indentation) {
		super(pw); 
		this.indentation = indentation; 
	}
	
	public IndentWriter(PrintWriter pw) {
		this(pw, DEFAULT_INDENTATION); 
	}
	
	@Override
	public void println(Object line) {
		println(String.valueOf(line));
	}

	@Override
	public void println(String line) {
		for (int i=0; i<level*indentation; i++) {
			print(" ");
		}
		
		super.println(line);
	}

	/**
	 * Increase the indentation. 
	 */
	public void indent() {
		level++; 
	}

	/**
	 * Decrease the indentation and write an empty line. 
	 */
	public void unindent() {
		level--;
		println();
		flush();
	}

}
