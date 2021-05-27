package com.marcosavard.commons.astro.res;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.marcosavard.commons.astro.Constellation;
import com.marcosavard.commons.astro.SpaceLocation;
import com.marcosavard.commons.res.CsvReader;
import com.marcosavard.commons.res.CsvReaderImpl;

public class StarFileDemo {

	public static void main(String[] args) {
		// get data
		StarFile starFile = StarFile.ofType(Star.class); 
		List<Star> allStars = starFile.readAll(); 
		
		// stats
		printAll(allStars); 
		printZodiac(allStars);
		findBrightestStar(allStars);
		findMostNorthernStar(allStars);
		findStarNearestFromVernalPoint(allStars);
		printUrsaMajor(allStars);
	}

	private static void printAll(List<Star> allStars) {
		System.out.println(allStars.size() + " stars read in file StarFile.csv"); 
	}
	
	private static void printUrsaMajor(List<Star> allStars) {
		List<Star> umaStars = allStars.stream().filter(s -> s.getConstellation().equals("UMa")).collect(Collectors.toList());
		System.out.println(); 
		System.out.println("Stars in Ursa Major :"); 
		
		for (Star star : umaStars) { 
			System.out.println("  " + star); 
		}
		
		System.out.println(); 
	}
	
	private static void printZodiac(List<Star> allStars) {
		List<Star> zodiacStars = allStars.stream().filter(s -> Constellation.of(s.getConstellation()).isZodiac()).collect(Collectors.toList());
		
		System.out.println(zodiacStars.size() + " stars in Zodiac constellations"); 
	}
	
	private static void findBrightestStar(List<Star> stars) {
		// sort by magnitude
		Comparator<Star> comparator = new MagnitureComparator(); 
		Star brightestStar = stars.stream().sorted(comparator).findFirst().orElse(null); 
		System.out.println("Brightest star : " + brightestStar); 
	}
	
	private static void findMostNorthernStar(List<Star> stars) {
		// sort by declination
		Comparator<Star> comparator = new DeclinationComparator();
		Star northestStar = stars.stream().sorted(comparator).findFirst().orElse(null); 
		System.out.println("Most Northern star : " + northestStar); 
	}
	
	private static void findStarNearestFromVernalPoint(List<Star> stars) {
		SpaceLocation location = SpaceLocation.VERNAL_POINT; 
		Star nearestStar = findStarNearestFromLocation(stars, location.getRightAscensionHour(), location.getDeclination()); 
		System.out.println("Nearest star from vernal point : " + nearestStar); 
	}
	
	private static Star findStarNearestFromLocation(List<Star> stars, double ra, double dec) {
		Star nearestStar = stars.get(0); 
		double neareastDistance2 = Double.MAX_VALUE; 
		
		for (Star star : stars) { 
			double distance2 = star.findDistance2From(ra, dec); 
			
			if (distance2 < neareastDistance2) { 
				neareastDistance2 = distance2; 
				nearestStar = star; 
			}
		}
		
		return nearestStar;
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
			int comparison = (int)(s2.getDeclination() - s1.getDeclination());
			return comparison;
		}
		
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

}
