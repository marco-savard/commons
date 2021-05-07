package com.marcosavard.commons.res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderImpl implements CsvReader { 
	private static char DEFAULT_SEPARATOR = ';'; 
	private static char DEFAULT_HEADER_SEPARATOR = ';'; 
	private static char DEFAULT_QUOTE_CHAR = '\"'; 
	private static String DEFAULT_COMMENT_PREFIX = "#"; 
	
	private BufferedReader bufferedReader;
	boolean hasNext = true;
	private long linesRead = 0; 
	private char separator; 
	private char quoteChar; 
	private String commentPrefix; 
	private String[] header;
	
	public CsvReaderImpl(Reader reader) {
		this(reader, DEFAULT_SEPARATOR);
	}
	
	public CsvReaderImpl(Reader reader, char separator) {
		this(reader, separator, DEFAULT_QUOTE_CHAR);
	}
	
	public CsvReaderImpl(Reader reader, char separator, char quoteChar) {
		this(reader, separator, quoteChar, DEFAULT_HEADER_SEPARATOR);
	}
	
	public CsvReaderImpl(Reader reader, char separator, char quoteChar, char headerSeparator) {
		this(reader, separator, quoteChar, headerSeparator, DEFAULT_COMMENT_PREFIX);
	}
	
	public CsvReaderImpl(Reader reader, char separator, char quoteChar, char headerSeparator, String commentPrefix) {
		bufferedReader = new BufferedReader(reader); 
		this.separator = separator;
		this.quoteChar = quoteChar;
		this.commentPrefix = commentPrefix;
		
		//read header
		try {
			header = readNext(headerSeparator);
		} catch (IOException ex) { 
			header = null;
		}
	}
	
	@Override
	public long getLinesRead() {
		return linesRead;
	}
	
	@Override
	public String[] readNext() throws IOException {
		return readNext(separator); 
	}
	
	private String[] readNext(char separator) throws IOException {
		String[] line;
		
	    do {
	      line = readNotEmptyLine(separator);
	    } while (line.length == 0 && hasNext);

	    linesRead++;
	    return line;
	}

	@Override
	public List<String[]> readAll() throws IOException {
		List<String[]> lines = new ArrayList<>(); 
		
		do { 
		  String[] line = readNext(); 
		  
		  if (line.length > 0) { 
			  lines.add(line); 
		  }
		  
		} while(hasNext); 
		
		return lines;
	}
	
	@Override
	public void skip(int numberOfLinesToSkip) throws IOException {
		for (int i=0; i<numberOfLinesToSkip; i++) { 
			readNext(); 
		}
	}

	@Override
	public void close() throws IOException {
		bufferedReader.close();
	}
	
	public String[] getHeader() {
		return header;
	}
	
	private String[] readNotEmptyLine(char separtor) throws IOException {
		String line = bufferedReader.readLine();
		List<String> values = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		
		if (line == null) {
			hasNext = false;
		} else { 
			boolean isComment = line.startsWith(commentPrefix);
			boolean inQuotes = false;
			
			if (! isComment) {
		        char[] chars = line.toCharArray();
		        
		        for (char ch : chars) {
		            if (ch == quoteChar) {
		              inQuotes = !inQuotes;
		            } else if ((ch == separator) && !inQuotes) {
		              values.add(sb.toString().trim());
		              sb.setLength(0);
		            } else {
		              sb.append(ch);
		            }
		          } //end for
		        
		          if (!sb.toString().isEmpty()) {
		            values.add(sb.toString().trim());
		          }
			} //end if
		} //end if
		
		String[] row = new String[values.size()];
	    row = values.toArray(row);
	    return row;
	}


}
