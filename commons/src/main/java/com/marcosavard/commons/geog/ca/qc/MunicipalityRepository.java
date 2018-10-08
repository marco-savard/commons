package com.marcosavard.commons.geog.ca.qc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.marcosavard.commons.geog.ca.PostalCode;
import com.marcosavard.commons.geog.ca.qc.Municipality.Designation;
import com.marcosavard.commons.io.CsvReader;

public class MunicipalityRepository {
	private static List<Municipality> municipalites = null; 
	
	private static final String[] DESIGNATIONS = new String[] {
		"Canton",
		"Municipalit�",
		"Paroisse",
		"Village",
		"Ville"
	}; 

	public static List<Municipality> getMunicipalites() {
		if (municipalites == null) {
			municipalites = loadMunicipalities();
		}
		
		return municipalites;
	}
	

	public static List<Municipality> getMunicipalitesByRegion(int regionCode) {
		List<Municipality> municipalities = getMunicipalites().stream().filter(m -> m.getRegion() == regionCode).collect(Collectors.toList()); 
		return municipalities;
	}
	
	public static List<Municipality> getMunicipalitesByDesignation(Designation designation) {
		List<Municipality> municipalities = getMunicipalites().stream().filter(m -> m.getDesignation() == designation).collect(Collectors.toList()); 
		return municipalities;
	}
	
	public static Municipality findByName(String name) {
		List<Municipality> municipalites = MunicipalityRepository.getMunicipalites();
		Municipality municipalite = municipalites.stream().filter(m -> m.hasName(name)).findFirst().get(); 
		return municipalite;
	}
	
	//
	// private methods
	//
	
	private static List<Municipality> loadMunicipalities() {
		InputStream input = MunicipalityRepository.class.getResourceAsStream("Municipalites.csv"); 

		try {
			Reader r = new InputStreamReader(input, "UTF-8");
			CsvReader cr = new CsvReader(r, 1, ','); 
			municipalites = readMunicipalities(cr);
		} catch (IOException ex) {
			throw new RuntimeException(ex); 
		}

		return municipalites;
	}

	private static List<Municipality> readMunicipalities(CsvReader cr) throws IOException {
		List<Municipality> municipalites = new ArrayList<>();
		List<String> columns = cr.readHeaderColumns();
		
		while (cr.hasNext()) {
			List<String> line = cr.readLine(); 
			
			if (! line.isEmpty()) {
				try {
					Municipality municipalite = readMunicipalite(columns, line);
					municipalites.add(municipalite);
				} catch(IllegalArgumentException ex) {
					//ignore, but log it
					//System.out.println(ex);
				}
			}
		}

		return municipalites;
	}

	private static Municipality readMunicipalite(List<String> columns, List<String> row) {
		String name = readValue(columns, row, "munnom");
		String codePostalText =  readValue(columns, row, "mcodpos");  
		String designationText =  readValue(columns, row, "mdes"); 
		String regionText =  readValue(columns, row, "regadm"); 
		
		Designation designation = findDesignation(designationText); 
		PostalCode postalCode = new PostalCode(codePostalText); 
				
		if (designation == null) {
			String msg = MessageFormat.format("{0} is unknown", designationText); 
			throw new IllegalArgumentException(msg);
		}
		
		RegionAdministrative region = findRegion(regionText);
		Municipality municipalite = new Municipality(name, designation, region, postalCode);
		return municipalite;
	}

	private static Designation findDesignation(String designationText) {
		int idx = Arrays.asList(DESIGNATIONS).indexOf(designationText);
		Designation designation = (idx == -1) ? null : Designation.values()[idx]; 
		return designation; 
	}

	private static RegionAdministrative findRegion(String regionText) {
		int i1 = regionText.indexOf('('); 
		int i2 = regionText.indexOf(')');
		String number = regionText.substring(i1+1, i2);
		int regionCode = Integer.parseInt(number); 
		RegionAdministrative region = RegionAdministrative.values()[regionCode - 1];
		return region;
	}

	private static String readValue(List<String> columns, List<String> row, String fieldName) {
		int idx = columns.indexOf(fieldName);
		String value = row.get(idx);
		value = value.replaceAll("\"", "");
		return value;
	}







}
