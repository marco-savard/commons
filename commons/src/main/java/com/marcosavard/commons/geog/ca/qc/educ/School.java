package com.marcosavard.commons.geog.ca.qc.educ;

import com.marcosavard.commons.geog.ca.qc.RegionAdministrative;

public class School extends Organization {

	public School(EducationalNetwork network, RegionAdministrative region, String code, String codeParent, String nom, String codePostal, double latitude, double longitude) {
		super(network, region, code, codeParent, nom, codePostal, latitude, longitude);
	}

}
