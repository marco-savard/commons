package com.marcosavard.commons.geog.ca.qc;

import java.util.List;

import com.marcosavard.commons.geog.ca.PostalCode;
import com.marcosavard.commons.geog.ca.qc.Municipality.Designation;

public class MunicipalityDemo {

	public static void main(String[] args) {
		Municipality mtl = MunicipalityRepository.findByName("montreal");
		PostalCode postalCode = mtl.getPostalCode(); 
		System.out.println(mtl + " " + postalCode.toDisplayString());
		
		List<Municipality> municipalites = MunicipalityRepository.getMunicipalitesByRegion(6); 
		printMinicipalities(municipalites); 
		
		municipalites = MunicipalityRepository.getMunicipalitesByDesignation(Designation.CANTON); 
		printMinicipalities(municipalites); 
	}

	private static void printMinicipalities(List<Municipality> municipalites) {
		for (Municipality municipality : municipalites) {
			System.out.println("  .." + municipality);
		}
		System.out.println();
	}

}
