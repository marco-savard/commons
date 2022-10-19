package com.marcosavard.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Branch 
 * Generated on 2022/10/19 15:04
 */
public class Branch {
  private final String name;
  private final Address location;
  private List<Branch> subdivisions = new ArrayList<>();
  private List<Team> teams = new ArrayList<>();
  
  /**
   * @param name String
   * @param location Address
   */
  public Branch(String name, Address location) {
    if (name == null) {
      throw new IllegalArgumentException ("Parameter 'name' cannot be null");
    }
    
    if (location == null) {
      throw new IllegalArgumentException ("Parameter 'location' cannot be null");
    }
    
    this.name = name;
    this.location = location;
    
  }
  
  /**
   * @return name String
   */
  public String getName() {
    return name;
  }
  
  /**
   * @return location Address
   */
  public Address getLocation() {
    return location;
  }
  
  /**
   * @return subdivisions List
   */
  public List<Branch> getSubdivisions() {
    return subdivisions;
  }
  
  public Branch createBranch(String name, Address location) {
    Branch branch = new Branch(name, location);
    this.subdivisions.add(branch);
    return branch;
  }
  
  public void removeFromSubdivisions(Branch branch) {
    this.subdivisions.remove(branch);
  }
  
  /**
   * @return teams List
   */
  public List<Team> getTeams() {
    return teams;
  }
  
  public Team createTeam(String name) {
    Team team = new Team(name);
    this.teams.add(team);
    return team;
  }
  
  public void removeFromTeams(Team team) {
    this.teams.remove(team);
  }
  
  
  @Override
  public boolean equals(Object other) {
    boolean equal = false;
    
    if (other instanceof Branch) {
      Branch that = (Branch)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getName(), getLocation(), getSubdivisions(), getTeams());
  }
  
  protected boolean isEqualTo(Branch that) {
    boolean equal = true;
    equal = equal && getName() == null ? that.getName() == null : getName().equals(that.getName());
    equal = equal && getLocation() == null ? that.getLocation() == null : getLocation().equals(that.getLocation());
    equal = equal && getSubdivisions() == null ? that.getSubdivisions() == null : getSubdivisions().equals(that.getSubdivisions());
    equal = equal && getTeams() == null ? that.getTeams() == null : getTeams().equals(that.getTeams());
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("name = ").append(name).append(", ");
    sb.append("location = ").append(location).append(", ");
    sb.append("subdivisions = ").append(subdivisions).append(", ");
    sb.append("teams = ").append(teams).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
