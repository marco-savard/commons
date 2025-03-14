package com.marcosavard.commons.security;

import java.time.LocalDateTime;

/**
 * A class that computes password entropy.
 * 
 * @author Marco
 *
 */
public class Password {
  private static final String MASKED_PASSWORD = "*****";
  private char[] charArray;
  private boolean temporary;
  private LocalDateTime creationTime;

  public enum Strenght {
    VERY_WEAK, WEAK, MEDIUM, STRONG, VERY_STRONG
  };

  public static Password of(char[] charArray) {
    return Password.of(charArray, false);
  }

  public static Password of(char[] charArray, boolean temporary) {
    LocalDateTime now = LocalDateTime.now();
    return Password.of(charArray, temporary, now);
  }

  public static Password of(char[] charArray, boolean temporary, LocalDateTime creationTime) {
    return new Password(charArray, temporary, creationTime);
  }

  // TODO allow inner blanks
  private Password(char[] original, boolean temporary, LocalDateTime creationTime) {
    int i = 0, len = 0;
    this.temporary = temporary;
    this.creationTime = creationTime;

    // count non-space characters
    for (char c : original) {
      len += (c != ' ') ? 1 : 0;
    }

    // allocate space for non-space characters
    this.charArray = new char[len];
    for (char c : original) {
      if (c != ' ') {
        this.charArray[i++] = c;
      }
    }
  }

  @Override
  public String toString() {
    return MASKED_PASSWORD;
  }

  public boolean isTemporary() {
    return temporary;
  }

  public LocalDateTime getCreationTime() {
    return creationTime;
  }

  /**
   * Compute entropy based on number of bits. The entropy formula is purely mathematically and words
   * found in a dictionary are not taken in amount by the method.
   * 
   * See
   * https://softwareengineering.stackexchange.com/questions/167235/how-can-i-estimate-the-entropy-of-a-password
   * 
   * @param password as array of characters, for security reasons.
   * @return the number of bits of entropy, usually between 5 and 50 bits.
   */
  public int getEntropy() {
    int len = charArray.length;
    double nbBits = 0;

    for (int i = 0; i < len; i++) {
      if (i == 0) {
        nbBits += 4; // bit 0 gives 4 bits of entropy
      } else if (i < 8) {
        nbBits += 2; // bits 1..7 give 2 bits of entropy
      } else if (i < 20) {
        nbBits += 1.5; // bits 8..19 give 1.5 bits of entropy
      } else {
        nbBits += 1; // extra bits give 1 bit of entropy
      }
    }

    boolean hasLowercase = false;
    boolean hasUppercase = false;
    boolean hasDigit = false;
    boolean hasSpecial = false;

    for (char ch : charArray) {
      hasLowercase = hasLowercase |= Character.isLowerCase(ch);
      hasDigit = hasUppercase |= Character.isUpperCase(ch);
      hasLowercase = hasDigit |= Character.isDigit(ch);
      hasSpecial = hasSpecial |= !Character.isLetterOrDigit(ch);
    }

    // 6 extra bits of entropy if combination of lower, upper and digit
    if (hasLowercase && hasUppercase && hasDigit && hasSpecial) {
      nbBits += 6;
    }

    return (int) nbBits;
  }

  /**
   * Returns the password's strength, based on its entropy.
   * 
   * @param entropy the number of bits of entropy
   * @return its strength, form VERY_WEAK to VERY_STRONG
   */
  public Strenght getStrenght() {
    int entropy = getEntropy();
    Strenght strenght;

    if (entropy <= 10) {
      strenght = Strenght.VERY_WEAK;
    } else if (entropy <= 20) {
      strenght = Strenght.WEAK;
    } else if (entropy <= 30) {
      strenght = Strenght.MEDIUM;
    } else if (entropy <= 40) {
      strenght = Strenght.STRONG;
    } else {
      strenght = Strenght.VERY_STRONG;
    }

    return strenght;
  }

  public void clear() {
    for (int i = 0; i < charArray.length; i++) {
      charArray[i] = '\0';
    }
  }

  public char[] getCharacters() {
    return charArray;
  }



}
