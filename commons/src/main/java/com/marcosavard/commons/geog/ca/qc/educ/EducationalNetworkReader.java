package com.marcosavard.commons.geog.ca.qc.educ;

import java.util.List;
import com.marcosavard.commons.geog.ca.qc.RegionAdministrative;
import com.marcosavard.commons.geog.ca.qc.educ.res.QuebecSchool;
import com.marcosavard.commons.geog.ca.qc.educ.res.QuebecSchools;

public class EducationalNetworkReader {

  static EducationalNetwork read() {
	// read data
	QuebecSchools quebecSchools = QuebecSchools.ofType(QuebecSchool.class);
	List<QuebecSchool> schools = quebecSchools.read(1500); 
	quebecSchools.close();
	    
    // get data
    List<String> columns = quebecSchools.getColumnNames();

    // build objects from data
    EducationalNetwork network = readOrganizations(columns, schools);
    return network;
  }

  //
  // private methods
  //
  private static EducationalNetwork readOrganizations(List<String> columns, List<QuebecSchool> schools) {
    EducationalNetwork network = new EducationalNetwork();

    for (QuebecSchool school : schools) {
      Organization organization = readOrganization(network, columns, school);

      if (organization != null) {
        network.add(organization);
      }
    }

    return network;
  }

  private static Organization readOrganization(EducationalNetwork network, List<String> columns,
		QuebecSchool school) {
	  
    String code = school.getCodeDOrganisme();  
    String type = school.getTypeDOrganisme(); 
    String nom = school.getNomDOrganismeOfficiel(); 
    String codeParent = school.getCodeOrganismeResponsable(); 
    RegionAdministrative region = getRegion(school.getRegionAdministrative()); 
    String codePostal = school.getCodePostalDeLAdresseGeographique(); 
    double latitude = school.getLatitude(); 
    double longitude = school.getLongitude(); 

    Organization organization = null;

    if ("Regroupement l�gal(uni)".equals(type)) {
      organization =
          new UniversitySystem(network, region, code, nom, codePostal, latitude, longitude);
    } else if ("Constituante".equals(type)) {
      organization =
          new University(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Universit�".equals(type)) {
      organization =
          new University(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Org. d�cernant grade univ.".equals(type)) {
      organization =
          new University(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("C�gep".equals(type)) {
      organization = new College(network, region, code, nom, codePostal, latitude, longitude);
    } else if ("Coll�ge r�gional".equals(type)) {
      organization = new College(network, region, code, nom, codePostal, latitude, longitude);
    } else if ("Entit� juridique".equals(type)) {
      organization = new College(network, region, code, nom, codePostal, latitude, longitude);
    } else if ("Centre coll de transfert tech.".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Campus coll�gial".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Coll�ge constituant".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Coll�ge priv�".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("�cole gouvernementale".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Commission scolaire".equals(type)) {
      organization = new SchoolBoard(network, region, code, nom, codePostal, latitude, longitude);
    } else if ("�cole".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Centre de formation prof.".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Centre �ducation des adultes".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("�tablissement d'enseignement".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Centre d'enseignement".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Installation".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Installation du coll�ge priv�".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("�tab. d'enseig. coll�ge priv�".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else {
      System.out.println(" NOt found" + type);
    }

    return organization;
  }


  private static RegionAdministrative getRegion(int regionCode) {
    RegionAdministrative region = RegionAdministrative.values()[regionCode - 1];
    return region;
  }

  private static String readValue(List<String> columns, String[] line, String fieldName) {
    int idx = columns.indexOf(fieldName);
    String value = line[idx].replaceAll("\"", "");
    return value;
  }



}
