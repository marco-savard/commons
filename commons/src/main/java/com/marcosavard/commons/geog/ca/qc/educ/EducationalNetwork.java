package com.marcosavard.commons.geog.ca.qc.educ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.geog.ca.PostalCode;
import com.marcosavard.commons.geog.ca.qc.RegionAdministrative;
import com.marcosavard.commons.text.WordDistance;

public class EducationalNetwork {
  private Map<String, Organization> organizationsByCode = new HashMap<>();

  public void add(Organization organization) {
    organizationsByCode.put(organization.getCode(), organization);
  }

  public List<Organization> getAllOrganizations() {
    List<Organization> organizations = new ArrayList<>();
    organizations.addAll(organizationsByCode.values());
    return organizations;
  }

  public List<UniversitySystem> getUniversitySystems() {
    List<Organization> organizations = getAllOrganizations();
    Predicate<Organization> p = o -> (o instanceof UniversitySystem);
    List<UniversitySystem> universitySystems = organizations.stream().filter(p)
        .map(o -> (UniversitySystem) o).collect(Collectors.toList());
    return universitySystems;
  }


  public List<University> getUniversities() {
    List<Organization> organizations = getAllOrganizations();
    Predicate<Organization> p = o -> (o instanceof University);
    List<University> universities =
        organizations.stream().filter(p).map(o -> (University) o).collect(Collectors.toList());
    return universities;
  }

  public List<University> getUniversitiesByRegion(RegionAdministrative region) {
    List<University> allUniversities = getUniversities();
    List<University> universities = allUniversities.stream()
        .filter(u -> u.getRegion().equals(region)).collect(Collectors.toList());
    return universities;
  }

  public List<College> getColleges() {
    List<Organization> organizations = getAllOrganizations();
    Predicate<Organization> p = o -> (o instanceof College);
    List<College> colleges =
        organizations.stream().filter(p).map(o -> (College) o).collect(Collectors.toList());
    return colleges;
  }

  public List<College> getCollegesByRegion(RegionAdministrative region) {
    List<College> allColleges = getColleges();
    List<College> colleges =
        allColleges.stream().filter(c -> c.getRegion().equals(region)).collect(Collectors.toList());
    return colleges;
  }

  public List<SchoolBoard> getSchoolBoards() {
    List<Organization> organizations = getAllOrganizations();
    Predicate<Organization> p = o -> (o instanceof SchoolBoard);
    List<SchoolBoard> schoolBoards =
        organizations.stream().filter(p).map(o -> (SchoolBoard) o).collect(Collectors.toList());
    return schoolBoards;
  }

  public List<SchoolBoard> getSchoolBoardsByRegion(RegionAdministrative region) {
    List<SchoolBoard> allSchoolBoards = getSchoolBoards();
    List<SchoolBoard> schoolBoards = allSchoolBoards.stream()
        .filter(sb -> sb.getRegion().equals(region)).collect(Collectors.toList());
    return schoolBoards;
  }

  public List<School> getSchoolPrivateSchoolByRegion(RegionAdministrative region) {
    List<Organization> organizations = getAllOrganizations();
    Predicate<Organization> p =
        o -> (o instanceof School) && o.getRegion().equals(region) && "".equals(o.parentCode);
    List<School> schools =
        organizations.stream().filter(p).map(o -> (School) o).collect(Collectors.toList());
    return schools;
  }

  public Organization findNearestSchoolFrom(PostalCode postalCode) {
    List<Organization> organizations = getAllOrganizations();
    List<String> postalCodes =
        organizations.stream().map(s -> s.getPostalCode()).collect(Collectors.toList());
    String schoolPostalCode = WordDistance.findNearestString(postalCode.toString(), postalCodes);
    Organization school = findSchoolByPostalCode(organizations, schoolPostalCode);
    return school;
  }

  public PostalCode findNearestPostalCode(GeoLocation location) {
    List<Organization> organizations = getAllOrganizations();
    double nearestDistanbce = Double.MAX_VALUE;
    String neareatPostalCode = null;

    for (Organization organization : organizations) {
      GeoLocation loc = organization.getCoordinate();
      double distance = loc.findDistanceFrom(location);

      if (distance < nearestDistanbce) {
        nearestDistanbce = distance;
        neareatPostalCode = organization.getPostalCode();
      }
    }

    return PostalCode.of(neareatPostalCode);
  }


  private Organization findSchoolByPostalCode(List<Organization> quebecSchools, String postalCode) {
    Organization school =
        quebecSchools.stream().filter(s -> s.getPostalCode().equals(postalCode)).findFirst().get();
    return school;
  }



}
