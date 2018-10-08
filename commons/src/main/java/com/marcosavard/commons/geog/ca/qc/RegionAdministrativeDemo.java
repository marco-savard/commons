package com.marcosavard.commons.geog.ca.qc;

public class RegionAdministrativeDemo {

	public static void main(String[] args) {
		System.out.println("Regions du Quebec:");
		
		for (RegionAdministrative region : RegionAdministrative.values()) {
			System.out.println("  " + region);
		}
	}

}
