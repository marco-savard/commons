package com.marcosavard.commons.geog.ca.qc.educ;

import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.marcosavard.commons.geog.ca.qc.RegionAdministrative;
import com.marcosavard.commons.io.IndentWriter;
import com.marcosavard.commons.text.WordDistance;

public class EducationalNetworkDemo {

  public static void main(String[] args) {
    findNearingSchool("G3E2C7");
    printNetwork();
  }

  public static void findNearingSchool(String postalCode) {
    String code = "G3E2C7";
    EducationalNetwork network = EducationalNetworkReader.read();
    List<Organization> quebecSchools = network.getAllOrganizations();

    List<String> postalCodes = getListPostalCodes(quebecSchools);
    String schoolPostalCode = WordDistance.findNearestString(code, postalCodes);

    Organization school = findSchoolByPostalCode(quebecSchools, schoolPostalCode);
    String msg = MessageFormat.format("L''ecole la plus proche de {0} est : {1}", code, school);
    System.out.println(msg);
  }

  private static List<String> getListPostalCodes(List<Organization> quebecSchools) {
    List<String> postalCodes =
        quebecSchools.stream().map(s -> s.getPostalCode()).collect(Collectors.toList());
    Collections.sort(postalCodes);
    return postalCodes;
  }

  private static Organization findSchoolByPostalCode(List<Organization> quebecSchools,
      String postalCode) {
    Organization school =
        quebecSchools.stream().filter(s -> s.getPostalCode().equals(postalCode)).findFirst().get();
    return school;
  }

  private static void printNetwork() {
    PrintWriter pw = new PrintWriter(System.out);
    IndentWriter iw = new IndentWriter(pw);

    EducationalNetwork network = EducationalNetworkReader.read();
    printUniversityNetwork(iw, network);
    printCollegialNetwork(iw, network);
    printGeneralNetwork(iw, network);
    iw.flush();
  }

  private static void printUniversityNetwork(IndentWriter iw, EducationalNetwork network) {
    iw.println("Réseau universitaire:");
    iw.indent();

    List<UniversitySystem> systems = network.getUniversitySystems();
    for (UniversitySystem system : systems) {
      printOrganization(iw, system);
    }

    List<University> universities = network.getUniversities();
    for (University university : universities) {
      if ("".equals(university.getParentCode())) {
        printOrganization(iw, university);
      }
    }

    iw.unindent();
  }

  private static void printCollegialNetwork(IndentWriter iw, EducationalNetwork network) {
    iw.println("Réseau collégiale:");
    iw.indent();

    List<College> colleges = network.getColleges();
    for (College college : colleges) {
      printOrganization(iw, college);
    }

    iw.unindent();
  }

  private static void printGeneralNetwork(IndentWriter iw, EducationalNetwork network) {
    iw.println("Réseau scolaire:");
    iw.indent();

    for (RegionAdministrative region : RegionAdministrative.values()) {
      iw.println(region);
      iw.indent();

      List<School> schools = network.getSchoolPrivateSchoolByRegion(region);
      List<SchoolBoard> schoolBoards = network.getSchoolBoardsByRegion(region);

      if (!schools.isEmpty()) {
        iw.println("Ecoles privées et gouvernementales");
        iw.indent();

        for (School school : schools) {
          printOrganization(iw, school);
        }

        iw.unindent();
      }

      for (SchoolBoard schoolBoard : schoolBoards) {
        printOrganization(iw, schoolBoard);
      }

      iw.unindent();
    }

    iw.unindent();
  }

  private static void printOrganization(IndentWriter iw, Organization org) {
    iw.println(org.toDisplayString());

    List<Organization> components = org.getComponents();
    if (!components.isEmpty()) {
      iw.indent();

      for (Organization component : components) {
        printOrganization(iw, component);
      }

      iw.unindent();
    }
  }

}
