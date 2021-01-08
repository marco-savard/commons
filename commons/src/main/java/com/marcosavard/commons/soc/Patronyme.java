package com.marcosavard.commons.soc;

import com.marcosavard.commons.lang.CharString;

/**
 * Create a patronyme (family name). Useful for sanitize untrusted input.
 * 
 * @author Marco
 *
 */
public class Patronyme {
  private String name;

  /**
   * Create a patronyme. Accepts any international letter \p{L}, hyphens -, apostrophes, spaces and
   * dots; remove any other characters,
   * 
   * @param name not sanitized
   */
  public Patronyme(String name) {
    name = (name == null) ? "" : name;
    int invalidCharAt = -1;

    for (int i = 0; i < name.length(); i++) {
      char c = name.charAt(i);
      boolean valid = Character.isLetter(c);
      valid = valid || Character.isWhitespace(c);
      valid = valid || (c == '-');
      valid = valid || (c == '\'');
      valid = valid || (c == '.');

      if (!valid) {
        invalidCharAt = i;
        break;
      }
    }
    name = (invalidCharAt == -1) ? name : name.substring(0, invalidCharAt);
    name = name.trim();
    name = CharString.of(name).capitalize();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object other) {
    boolean isEqual = false;

    if (other instanceof Patronyme) {
      Patronyme that = (Patronyme) other;
      isEqual = this.name.equals(that.name);
    }

    return isEqual;
  }

  @Override
  public String toString() {
    return name;
  }
}
