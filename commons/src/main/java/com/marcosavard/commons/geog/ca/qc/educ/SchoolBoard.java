package com.marcosavard.commons.geog.ca.qc.educ;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.marcosavard.commons.geog.ca.qc.RegionAdministrative;

public class SchoolBoard extends Organization {
	private List<Organization> components = null;

	public SchoolBoard(EducationalNetwork network, RegionAdministrative region, String code, String nom, String codePostal, double latitude, double longitude) {
		super(network, region, code, "", nom, codePostal, latitude, longitude);
	}
	
	
	

	
	

}
