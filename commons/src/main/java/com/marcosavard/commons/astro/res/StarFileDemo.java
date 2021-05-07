package com.marcosavard.commons.astro.res;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.marcosavard.commons.res.CsvReader;
import com.marcosavard.commons.res.CsvReaderImpl;
import com.marcosavard.commons.res.CsvResourceFile;

public class StarFileDemo {

	public static void main(String[] args) {
		printAllLines();
		//findNeareastStar();
		//printAllInstances(); 
	}

	private static void printAllLines() {
		// get data
		Class<?> claz = StarFile.class; 
		InputStream input = claz.getResourceAsStream("StarFile.csv"); 
		Charset charset = StandardCharsets.UTF_8; 
		char separator = ';';
		char quoteChar = '\"'; 
		char headerSeparator = ';';
		String commentPrefix = "#"; 
		List<String[]> allLines; 
		 
		try {
		  Reader reader = new InputStreamReader(input, charset.name());
		  CsvReader csvReader = new CsvReaderImpl(reader, separator, quoteChar, headerSeparator, commentPrefix);
		  allLines = csvReader.readAll(); 
		  csvReader.close();		  
		} catch (IOException e) { 
			allLines = new ArrayList<>(); 
			e.printStackTrace();
		}
		
		//print all
		for (String[] line : allLines) { 
			String str = "  [" + String.join("; ", line) + "]"; 
			System.out.println(str);
		}
		
		System.out.println(); 
	}

	private static void printAllInstances() {
		// get data
		CsvResourceFile<Star> starsFile = StarFile.ofType(Star.class); 		
		List<Star> stars = starsFile.readAll(); 
		starsFile.close(); 

		for (Star star : stars) { 
			System.out.println(star); 
		}

		System.out.println(); 
	}
	
	private static void findNeareastStar() {
		// get data
		CsvResourceFile<Star> starsFile = StarFile.ofType(Star.class); 
		List<Star> stars = starsFile.readAll();
		starsFile.close(); 
		
		findNearestStar(stars, 0, 0); //Sun at vernal 
		findNearestStar(stars, 0, 90); //above North Pole
		findNearestStar(stars, 0, -90); //above South Pole
		findBrightestStar(stars); //above South Pole
		System.out.println(); 
	}
	
	private static void findBrightestStar(List<Star> stars) {
		// sort by magnitude
		Comparator<Star> comparator = new MagnitureComparator(); 
		Star brightestStar = stars.stream().sorted(comparator).findFirst().orElse(null); 
		System.out.println("Brightest star : " + brightestStar); 
	}
	
	private static void findNorthestStar(List<Star> stars) {
		// sort by declination
		Comparator<Star> comparator = new DeclinationComparator();
		Star northestStar = stars.stream().sorted(comparator).findFirst().orElse(null); 
		System.out.println("Northest star : " + northestStar); 
	}
	
	private static void findNearestStar(List<Star> stars, int ra, int dec) {
		Star nearestStar = stars.get(0); 
		double neareastDistance2 = Double.MAX_VALUE; 
		
		for (Star star : stars) { 
			double distance2 = star.findDistance2From(ra, dec); 
			
			if (distance2 < neareastDistance2) { 
				neareastDistance2 = distance2; 
				nearestStar = star; 
			}
		}
		
		System.out.println("Nearest star : " + nearestStar); 
	}
	
	private static class MagnitureComparator implements Comparator<Star> {

		@Override
		public int compare(Star s1, Star s2) {
			int comparison = (int)(s1.getMagnitude() - s2.getMagnitude());
			return comparison;
		}
		
	}

	private static class DeclinationComparator implements Comparator<Star> {

		@Override
		public int compare(Star s1, Star s2) {
			int comparison = (int)(s1.getDeclination() - s2.getDeclination());
			return comparison;
		}
		
	}

}
