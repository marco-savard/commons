package com.marcosavard.commons.math;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CorrelationDemo {
	private static final GunStat[] GunStats = new GunStat[] {
	    //country, gun homicide, overall homicide, gun ownership 
		new GunStat("NL", 2.7, 11.8, 1.9), 
		new GunStat("UK", 0.8, 6.7,  4.7), 	
		new GunStat("DE", 2.0, 12.1, 8.9),
		new GunStat("ES", 3.8, 13.7, 13.1),
		new GunStat("BE", 8.7, 18.5, 16.6),
		new GunStat("AU", 6.6, 19.5, 19.6), 
		new GunStat("FR", 5.5, 12.5, 22.6),
		new GunStat("CA", 8.4, 26.0, 29.1), 
		new GunStat("US", 44.6, 75.9, 48.0)
	}; 

	public static void main(String[] args) {
		List<Double> homicicesGun = Arrays.asList(GunStats).stream().map(s -> s.getHomicideGun()).collect(Collectors.toList()); 
		List<Double> homicidesOverall = Arrays.asList(GunStats).stream().map(s -> s.getHomicideOverall()).collect(Collectors.toList()); 
		List<Double> pctGunOwnership = Arrays.asList(GunStats).stream().map(s -> s.getPctGunOwnership()).collect(Collectors.toList()); 
		
		double r1 = 100 * Correlation.computeCoefficient(homicicesGun, pctGunOwnership); 
		System.out.println("  Correlation between homicides per gun and gun ownership : " + String.format("%.2f", r1));
		
		double r2 = 100 * Correlation.computeCoefficient(homicidesOverall, pctGunOwnership); 
		System.out.println("  Correlation between overall homicides and gun ownership : " + String.format("%.2f", r2));
	
		System.out.println("Correlation > 60% is considered meaningful"); 
	}

	private static class GunStat {
		@SuppressWarnings("unused")
		private String country;
		private double homicideGun, homicideOverall; //n per million 
		private double pctGunOwnership; //% households with guns
		
		public GunStat(String country, double homicideGun, double homicideOverall, double pctGunOwnership) {
			this.country = country; 
			this.homicideGun = homicideGun; 
			this.homicideOverall = homicideOverall;
			this.pctGunOwnership = pctGunOwnership; 
		}
		
		public double getHomicideGun() {
			return homicideGun; 
		}
		
		public double getHomicideOverall() {
			return homicideOverall; 
		}
		
		public double getPctGunOwnership() {
			return pctGunOwnership; 
		}
		
	}
}
