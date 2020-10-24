package com.marcosavard.commons.geog.ca.qc.educ;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.geog.ca.qc.RegionAdministrative;
import com.marcosavard.commons.util.ToStringBuilder;

public abstract class Organization {
  protected EducationalNetwork network;
  protected RegionAdministrative region;
  protected String code;
  protected String parentCode;
  private String name;
  private String codePostal;
  private GeoLocation coordinate;
  private List<Organization> components = null;

  protected Organization(EducationalNetwork network, RegionAdministrative region, String code,
      String parentCode, String name, String codePostal, double latitude, double longitude) {
    this.network = network;
    this.region = region;
    this.code = code;
    this.name = name;
    this.parentCode = parentCode;
    this.codePostal = codePostal;
    this.coordinate = GeoLocation.of(latitude, longitude);
  }

  public RegionAdministrative getRegion() {
    return region;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getPostalCode() {
    return codePostal;
  }

  public GeoLocation getCoordinate() {
    return coordinate;
  }

  public List<Organization> getComponents() {
    if (components == null) {
      components = new ArrayList<>();
      List<Organization> organizations = network.getAllOrganizations().stream()
          .filter(o -> o.getParentCode().equals(this.code)).collect(Collectors.toList());
      components.addAll(organizations);
    }


    return components;
  }

  @Override
  public String toString() {
    return ToStringBuilder.build(this);
  }

  public String toDisplayString() {
    return name;
  }

  public String getParentCode() {
    return parentCode;
  }



}
