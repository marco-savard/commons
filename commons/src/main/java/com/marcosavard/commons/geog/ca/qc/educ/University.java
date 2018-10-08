package com.marcosavard.commons.geog.ca.qc.educ;

import com.marcosavard.commons.geog.ca.qc.RegionAdministrative;

public class University extends Organization {

	public University(EducationalNetwork network, RegionAdministrative region, String code, String parentCode, String nom, String codePostal, double latitude, double longitude) {
		super(network, region, code, parentCode, nom, codePostal, latitude, longitude);
	}

}
