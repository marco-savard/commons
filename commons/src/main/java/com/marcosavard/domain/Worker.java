package com.marcosavard.domain;

import java.util.Objects;

/**
 * Worker represents a person who can be hired by a company
 * Generated on 2022/10/19 15:04
 */
public class Worker extends Person {
  private final long workerId;
  private Company employer;
  private Address officeAddress;
  
  /**
   * @param workerId long
   */
  public Worker(long workerId) {
    this.workerId = workerId;
    
  }
  
  /**
   * @return workerId long
   */
  public long getWorkerId() {
    return workerId;
  }
  
  /**
   * @return employer Company
   */
  public Company getEmployer() {
    return employer;
  }
  
  /**
   * @param employer Company
   */
  public void setEmployer(Company employer) {
    this.employer = employer;
  }
  
  /**
   * @return officeAddress Address
   */
  public Address getOfficeAddress() {
    return officeAddress;
  }
  
  /**
   * @param officeAddress Address
   */
  public void setOfficeAddress(Address officeAddress) {
    this.officeAddress = officeAddress;
  }
  
  
  @Override
  public boolean equals(Object other) {
    boolean equal = false;
    
    if (other instanceof Worker) {
      Worker that = (Worker)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getWorkerId(), getEmployer(), getOfficeAddress(), super.hashCode());
  }
  
  protected boolean isEqualTo(Worker that) {
    boolean equal = true;
    equal = equal && getWorkerId() == that.getWorkerId();
    equal = equal && getEmployer() == null ? that.getEmployer() == null : getEmployer().equals(that.getEmployer());
    equal = equal && getOfficeAddress() == null ? that.getOfficeAddress() == null : getOfficeAddress().equals(that.getOfficeAddress());
    equal = equal && super.isEqualTo(that);
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("workerId = ").append(workerId).append(", ");
    sb.append("employer = ").append(employer).append(", ");
    sb.append("officeAddress = ").append(officeAddress).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
