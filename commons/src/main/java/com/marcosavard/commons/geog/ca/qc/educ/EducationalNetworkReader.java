package com.marcosavard.commons.geog.ca.qc.educ;

import java.util.List;
import com.marcosavard.commons.geog.ca.qc.RegionAdministrative;
import com.marcosavard.commons.geog.ca.qc.educ.res.QuebecSchools;

public class EducationalNetworkReader {

  static EducationalNetwork read() {
    // get data
    QuebecSchools quebecSchools = new QuebecSchools();
    quebecSchools.loadAll();
    List<String> columns = quebecSchools.getColumns();
    List<String[]> rows = quebecSchools.getRows();

    // build objects from data
    EducationalNetwork network = readOrganizations(columns, rows);
    return network;
  }

  //
  // private methods
  //
  private static EducationalNetwork readOrganizations(List<String> columns, List<String[]> rows) {
    EducationalNetwork network = new EducationalNetwork();

    for (String[] row : rows) {
      Organization organization = readOrganization(network, columns, row);

      if (organization != null) {
        network.add(organization);
      }
    }

    return network;
  }

  private static Organization readOrganization(EducationalNetwork network, List<String> columns,
      String[] line) {
    String code = readValue(columns, line, "Code d'organisme");
    String type = readValue(columns, line, "Type d'organisme");
    String nom = readValue(columns, line, "Nom d'organisme officiel");
    String codeParent = readValue(columns, line, "Code organisme responsable");
    RegionAdministrative region = getRegion(readValue(columns, line, "Région administrative"));

    String codePostal = readValue(columns, line, "Code postal de l'adresse géographique");
    double latitude = Double.parseDouble(readValue(columns, line, "Latitude"));
    double longitude = Double.parseDouble(readValue(columns, line, "Longitude"));
    Organization organization = null;

    if ("Regroupement légal(uni)".equals(type)) {
      organization =
          new UniversitySystem(network, region, code, nom, codePostal, latitude, longitude);
    } else if ("Constituante".equals(type)) {
      organization =
          new University(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Université".equals(type)) {
      organization =
          new University(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Org. décernant grade univ.".equals(type)) {
      organization =
          new University(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Cégep".equals(type)) {
      organization = new College(network, region, code, nom, codePostal, latitude, longitude);
    } else if ("Collège régional".equals(type)) {
      organization = new College(network, region, code, nom, codePostal, latitude, longitude);
    } else if ("Entité juridique".equals(type)) {
      organization = new College(network, region, code, nom, codePostal, latitude, longitude);
    } else if ("Centre coll de transfert tech.".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Campus collégial".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Collège constituant".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Collège privé".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("École gouvernementale".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Commission scolaire".equals(type)) {
      organization = new SchoolBoard(network, region, code, nom, codePostal, latitude, longitude);
    } else if ("École".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Centre de formation prof.".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Centre �ducation des adultes".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Établissement d'enseignement".equals(type)) {
      organization =
          new School(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Centre d'enseignement".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Installation".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Installation du collège privé".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else if ("Étab. d'enseig. collège privé".equals(type)) {
      organization =
          new Installation(network, region, code, codeParent, nom, codePostal, latitude, longitude);
    } else {
      // /System.out.println(" NOt found" + type);
    }


    return organization;
  }


  private static RegionAdministrative getRegion(String regionText) {
    int regionCode = Integer.parseInt(regionText);
    RegionAdministrative region = RegionAdministrative.values()[regionCode - 1];
    return region;
  }

  private static String readValue(List<String> columns, String[] line, String fieldName) {
    int idx = columns.indexOf(fieldName);
    String value = line[idx].replaceAll("\"", "");
    return value;
  }



}
