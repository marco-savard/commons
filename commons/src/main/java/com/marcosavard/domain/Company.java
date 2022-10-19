package com.marcosavard.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Company represents a company
 * Generated on 2022/10/19 15:17
 */
public class Company {
  private final String name;
  private final Address headquarter;
  private List<Phone> phoneNumbers = new ArrayList<>();
  private List<Branch> divisions = new ArrayList<>();
  
  /**
   * @param headquarter Address
   * @param name String
   */
  public Company(String name, Address headquarter) {
    if (headquarter == null) {
      throw new IllegalArgumentException ("Parameter 'headquarter' cannot be null");
    }
    
    if (name == null) {
      throw new IllegalArgumentException ("Parameter 'name' cannot be null");
    }
    
    this.headquarter = headquarter;
    this.name = name;
    
  }
  
  /**
   * @return name String
   */
  public String getName() {
    return name;
  }
  
  /**
   * @return headquarter Address
   */
  public Address getHeadquarter() {
    return headquarter;
  }
  
  /**
   * @return phoneNumbers List
   */
  public List<Phone> getPhoneNumbers() {
    return phoneNumbers;
  }
  
  public void addToPhoneNumbers(Phone phone) {
    this.phoneNumbers.add(phone);
  }
  
  public void removeFromPhoneNumbers(Phone phone) {
    this.phoneNumbers.remove(phone);
  }
  
  /**
   * @return divisions List
   */
  public List<Branch> getDivisions() {
    return divisions;
  }
  
  public Branch createBranch(String name, Address location) {
    Branch branch = new Branch(name, location);
    this.divisions.add(branch);
    return branch;
  }
  
  public void removeFromDivisions(Branch branch) {
    this.divisions.remove(branch);
  }
  
  
  @Override
  public boolean equals(Object other) {
    boolean equal = false;
    
    if (other instanceof Company) {
      Company that = (Company)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getName(), getHeadquarter(), getPhoneNumbers(), getDivisions());
  }
  
  protected boolean isEqualTo(Company that) {
    boolean equal = true;
    equal = equal && getName() == null ? that.getName() == null : getName().equals(that.getName());
    equal = equal && getHeadquarter() == null ? that.getHeadquarter() == null : getHeadquarter().equals(that.getHeadquarter());
    equal = equal && getPhoneNumbers() == null ? that.getPhoneNumbers() == null : getPhoneNumbers().equals(that.getPhoneNumbers());
    equal = equal && getDivisions() == null ? that.getDivisions() == null : getDivisions().equals(that.getDivisions());
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("name = ").append(getName()).append(", ");
    sb.append("headquarter = ").append(getHeadquarter()).append(", ");
    sb.append("phoneNumbers = ").append(getPhoneNumbers()).append(", ");
    sb.append("divisions = ").append(getDivisions()).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
