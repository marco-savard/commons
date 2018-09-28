package com.marcosavard.commons.io;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes values in CSV format. 
 * 
 * @author Marco
 *
 */
public class CsvWriter {
	private Writer writer; 
	private String separator; 

	/**
	 * Create an instance of CsvWriter. 
	 * 
	 * @param writer usually a FileWriter. 
	 */
	public CsvWriter(Writer writer, String separator) {
		this.writer = writer;
		this.separator = separator;
	}

	/**
	 * Writes CSV headers, and then rows, using the separator.
	 * 
	 * 
	 * @param headers
	 * @param rows
	 */
	public void write(List<String[]> headers, List<String[]> rows) {
		PrintWriter pw = new PrintWriter(writer); 
		
		for (String[] header : headers) {
			writer(pw, header, separator); 
		}
		
		for (String[] row : rows) {
			writer(pw, row, separator); 
		}
		
		pw.close();
	}
	
	/**
	 * Writes CSV headers, and then rows. Format in a way that 
	 * all columns have the same width.
	 * 
	 * @param headers
	 * @param rows
	 */
	public void writeTable(List<String[]> headers, List<String[]> rows) {
		PrintWriter pw = new PrintWriter(writer); 
		List<Integer> columnLengths = computeColumnLength(headers, rows); 
		
		for (String[] header : headers) {
			writerRow(pw, columnLengths, header); 
		}
		
		for (String[] row : rows) {
			writerRow(pw, columnLengths, row); 
		}
		
		pw.close();
	}
	
	//
	// private methods
    //
	
	private void writerRow(PrintWriter pw, List<Integer> columnLengths, String[] values) {
		for (int i=0; i<values.length; i++) {
			String format = "%-" + columnLengths.get(i) + "s"; 
			String value = String.format(format, values[i]); 
			pw.print(value);
		}
		pw.println();
	}
	
	private List<Integer> computeColumnLength(List<String[]> headers, List<String[]> rows) {
		List<Integer> columnLengths = new ArrayList<>();
		for (int i=0; i<headers.get(0).length; i++) {
			columnLengths.add(1);
		}
		
		for (String[] header : headers) {
			computeColumnLength(columnLengths, header);
		}
		
		for (String[] row : rows) {
			computeColumnLength(columnLengths, row);
		}
		
		return columnLengths;
	}

	private void computeColumnLength(List<Integer> columnLengths, String[] values) {
		for (int i=0; i<values.length; i++) {
			int len = columnLengths.get(i); 
			len = (values[i].length() >= len) ? values[i].length()+1 : len; 
			columnLengths.set(i, len); 
		}
	}
	
	private void writer(PrintWriter pw, String[] values, String separator) {
		for (String value : values) {
			pw.print(value + separator);
		}
		pw.println();
		
	}

}
