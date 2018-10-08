package com.marcosavard.commons.geog.ca.qc.educ;

import com.marcosavard.commons.geog.ca.qc.RegionAdministrative;

public class College extends Organization {

	public College(EducationalNetwork network, RegionAdministrative region, String code, String nom, String codePostal, double latitude, double longitude) {
		super(network, region, code, "", nom, codePostal, latitude, longitude);
	}

}
