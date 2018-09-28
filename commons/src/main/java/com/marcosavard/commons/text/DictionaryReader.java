package com.marcosavard.commons.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class DictionaryReader {
	private BufferedReader br; 
	
	public DictionaryReader(String resource, String encoding) throws UnsupportedEncodingException {
		InputStream input = DictionaryDemo.class.getResourceAsStream(resource); 
		br = new BufferedReader(new InputStreamReader(input, encoding));
	}
	
	public DictionaryReader(Reader reader) {
		br = new BufferedReader(reader);
	}

	public Dictionary readAll() throws IOException {
		Dictionary dictionary = new Dictionary(); 
		
		do {
			String word = br.readLine();
			
			if (word != null) {
				dictionary.addWord(word);
			} else {
				break; 
			}
		} while (true); 
		
		return dictionary;
	}
}
