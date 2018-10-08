package com.marcosavard.commons.geog.ca.qc;

import java.text.Normalizer;

import com.marcosavard.commons.geog.ca.PostalCode;

/**
 * A municipalite, represented by a name, a designation, a postal code and its
 * administrative region. 
 * 
 * @author Marco
 *
 */
public class Municipality {
	public enum Designation {CANTON, MUNICIPALITE, PAROISSSE, VILLAGE, VILLE};
	
	private String name;
	private Designation designation; 
	private int regionCode; 
	private PostalCode postalCode;

	public Municipality(String name, Designation designation, RegionAdministrative region, PostalCode postalCode) {
		this.name = name;
		this.designation = designation;
		this.regionCode = region.getCode();
		this.postalCode = postalCode;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public boolean hasName(String thatName) {
		return equalsIgnoreAccents(this.name, thatName);
	}

	private boolean equalsIgnoreAccents(String s1, String s2) {
		s1 = Normalizer.normalize(s1, Normalizer.Form.NFD);
		s2 = Normalizer.normalize(s2, Normalizer.Form.NFD);
		s1 = s1.replaceAll("[^\\p{ASCII}]", "");
		s2 = s2.replaceAll("[^\\p{ASCII}]", "");
		return s1.equalsIgnoreCase(s2); 
	}

	public int getRegion() {
		return regionCode;
	}

	public Designation getDesignation() {
		return designation;
	}
	
	public PostalCode getPostalCode() {
		return postalCode;
	}


}
