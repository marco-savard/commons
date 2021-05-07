package com.marcosavard.commons.res;

import java.io.IOException;
import java.util.List;

/* 
 * define methods compliant to OpenCSV.ICsvReader
 */ 
public interface CsvReader {
	
	public long getLinesRead();
	
	public String[] readNext() throws IOException;
	
	public List<String[]> readAll() throws IOException;
	
	public void	skip(int numberOfLinesToSkip) throws IOException;

	public void close() throws IOException;
	
}
