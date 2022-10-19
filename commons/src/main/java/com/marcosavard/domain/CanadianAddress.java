package com.marcosavard.domain;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * CanadianAddress represents a Canadian address
 * Generated on 2022/10/19 11:17
 */
public class CanadianAddress extends Address {
  private final String provinceCode;
  private final String postalCode;
  
  public static final Field PROVINCE_CODE_FIELD;
  public static final Field POSTAL_CODE_FIELD;
  
  static {
    try {
      PROVINCE_CODE_FIELD = CanadianAddress.class.getDeclaredField("provinceCode");
      POSTAL_CODE_FIELD = CanadianAddress.class.getDeclaredField("postalCode");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @param country Country
   * @param streetName String
   * @param provinceCode String
   * @param postalCode String
   * @param noApartment String
   * @param civicNumber String
   */
  public CanadianAddress(String civicNumber, String streetName, String noApartment, Country country, String provinceCode, String postalCode) {
    super(civicNumber, streetName, noApartment, country);
    this.provinceCode = provinceCode;
    this.postalCode = postalCode;
    
  }
  
  /**
   * @return provinceCode String
   */
  public String getProvinceCode() {
    return provinceCode;
  }
  
  /**
   * @return postalCode String
   */
  public String getPostalCode() {
    return postalCode;
  }
  
  
  public static Field[] getFields() {
    return new Field[] {PROVINCE_CODE_FIELD, POSTAL_CODE_FIELD};
  }
  
  /**
   * @param field
   * @return the value for this field
   */
  public Object get(Field field) throws IllegalAccessException {
    return field.get(this);
  }
  
  /**
   * @param field
   * @param value to be assigned
   */
  public void set(Field field, Object value) throws IllegalAccessException {
    field.set(this, value);
  }
  
  @Override
  public boolean equals(Object other) {
    boolean equal = false;
    
    if (other instanceof CanadianAddress) {
      CanadianAddress that = (CanadianAddress)other;
      equal = (hashCode() == that.hashCode()) && isEqualTo(that);
    }
    
    return equal;
    
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getProvinceCode(), getPostalCode(), super.hashCode());
  }
  
  protected boolean isEqualTo(CanadianAddress that) {
    boolean equal = true;
    equal = equal && getProvinceCode() == null ? that.getProvinceCode() == null : getProvinceCode().equals(that.getProvinceCode());
    equal = equal && getPostalCode() == null ? that.getPostalCode() == null : getPostalCode().equals(that.getPostalCode());
    equal = equal && super.isEqualTo(that);
    return equal;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("provinceCode = ").append(provinceCode).append(", ");
    sb.append("postalCode = ").append(postalCode).append(", ");
    sb.append("}");
    return sb.toString();
  }
  
}
