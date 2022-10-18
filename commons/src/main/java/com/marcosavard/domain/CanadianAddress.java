package com.marcosavard.domain;

import java.lang.NoSuchFieldException;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * A Canadian address
 */
public class CanadianAddress extends Address {
  private final String provinceCode;
  private final String postalCode;
  
  public static final Field PROVINCE_CODE;
  public static final Field POSTAL_CODE;
  
  static {
    try {
      PROVINCE_CODE = CanadianAddress.class.getDeclaredField("provinceCode");
      POSTAL_CODE = CanadianAddress.class.getDeclaredField("postalCode");
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
    return new Field[] {PROVINCE_CODE, POSTAL_CODE, };
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
    sb.append("provinceCode = " + provinceCode + ", ");
    sb.append("postalCode = " + postalCode + ", ");
    sb.append("}");
    return sb.toString();
  }
  
}
