package com.marcosavard.commons.geog.ca.qc.educ;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.marcosavard.commons.geog.ca.qc.RegionAdministrative;

public class UniversitySystem extends Organization  {
	
	protected UniversitySystem(EducationalNetwork network, RegionAdministrative region, String code, String name,
			String codePostal, double latitude, double longitude) {
		super(network, region, code, "", name, codePostal, latitude, longitude);
	}



}
