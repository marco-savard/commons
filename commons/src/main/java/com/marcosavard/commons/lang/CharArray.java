package com.marcosavard.commons.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CharArray implements CharSequence {
  private char[] characters;


  public static CharArray of(char... characters) {
    int nb = characters.length;
    char[] array = new char[nb];
    for (int i = 0; i < nb; i++) {
      array[i] = characters[i];
    }

    return new CharArray(array);
  }

  public static CharArray of(CharSequence... charSequences) {
    int nb = charSequences.length;
    char[][] arrays = new char[nb][];
    for (int i = 0; i < nb; i++) {
      arrays[i] = toCharArray(charSequences[i]);
    }

    return of(arrays);
  }

  public static CharArray of(char[]... charArrays) {
    char[] concatenation = concat(charArrays);
    CharArray array = new CharArray(concatenation);
    return array;
  }

  // private constructor
  private CharArray(char[] characters) {
    this.characters = characters;
  }

  @Override
  public int length() {
    return this.characters.length;
  }

  @Override
  public char charAt(int index) {
    return this.characters[index];
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof CharSequence) {
      char[] otherArray = toCharArray((CharSequence) other);
      equal = Arrays.equals(this.characters, otherArray);
    }

    return equal;
  }

  public boolean equalsIgnoreCase(Object other) {
    boolean equal = false;

    if (other instanceof CharSequence) {
      char[] thisArray = toCharArray(toLowerCase(this));

      CharSequence otherSequence = (CharSequence) other;
      otherSequence = toLowerCase(otherSequence);
      char[] otherArray = toCharArray((CharSequence) other);

      equal = Arrays.equals(thisArray, otherArray);
    }

    return equal;
  }

  @Override
  public int hashCode() {
    return this.characters.hashCode();
  }

  public CharSequence subSequence(int start) {
    return subSequence(start, length());
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    int len = end - start;
    char[] characters = new char[len];
    for (int i = 0; i < len; i++) {
      characters[i] = this.charAt(start + i);
    }

    return CharArray.of(characters);
  }

  public int indexOf(CharSequence charSequence) {
    return indexOf(toCharArray(charSequence));
  }

  public int indexOf(char[] subArray) {
    int len = length() - subArray.length;
    int k = 0;
    int sL = subArray.length;

    for (int i = 0; i <= len; i++) {
      if (charAt(i) == subArray[k]) {
        for (int j = 0; j < subArray.length; j++) {
          if (charAt(i + j) == subArray[j]) {
            sL--;
            if (sL == 0) {
              return i;
            }
          }
        }
      }
    }

    return -1;
  }

  public int indexOfOld(CharSequence charSequence) {
    return indexOfOld(toCharArray(charSequence));
  }

  public int indexOfOld(char[] target) {
    List<Character> src = new ArrayList<Character>();
    List<Character> dest = new ArrayList<Character>();

    for (char ch : characters) {
      src.add(ch);
    }

    for (char ch : target) {
      dest.add(ch);
    }

    int idx = Collections.indexOfSubList(src, dest);
    return idx;
  }

  public CharArray concat(CharSequence suffix) {
    char[] concatenation = concat(characters, toCharArray(suffix));
    return CharArray.of(concatenation);
  }

  public CharArray concat(char ch) {
    char[] concatenation = concat(characters, new char[] {ch});
    return CharArray.of(concatenation);
  }

  public CharArray copy() {
    CharArray copied = CharArray.of(Arrays.copyOf(characters, length()));
    return copied;
  }

  private static char[] concat(char[]... charArrays) {
    int nb = charArrays.length;
    int count = 0, total = 0;

    for (int i = 0; i < nb; i++) {
      total += charArrays[i].length;
    }

    char[] characters = new char[total];

    for (int i = 0; i < nb; i++) {
      for (int j = 0; j < charArrays[i].length; j++) {
        characters[count++] = charArrays[i][j];
      }
    }

    return characters;
  }

  public boolean isEmpty() {
    return (this.length() == 0);
  }

  public CharArray replace(char oldChar, char newChar) {
    int len = length();
    char[] copied = Arrays.copyOf(characters, len);

    for (int i = 0; i < len; i++) {
      if (charAt(i) == oldChar) {
        copied[i] = newChar;
      }
    }

    CharArray replaced = CharArray.of(copied);
    return replaced;
  }

  public boolean startsWith(CharSequence prefix) {
    int nb = prefix.length();
    boolean startsWith = true;

    for (int i = 0; i < nb; i++) {
      if (this.charAt(i) != prefix.charAt(i)) {
        startsWith = false;
        break;
      }
    }

    return startsWith;
  }

  public boolean endsWith(CharSequence suffix) {
    int nb = suffix.length();
    boolean endsWith = true;

    for (int i = 0; i < nb; i++) {
      int idx = this.length() - nb + i;
      if (this.charAt(idx) != suffix.charAt(i)) {
        endsWith = false;
        break;
      }
    }

    return endsWith;
  }

  public CharArray removeRepeatingCharacters() {
    CharArray dest = CharArray.of("");
    int j;

    for (int i = 0; i < length(); i++) {
      j = i + 1;

      if (i == length() - 1) { // if last
        dest = dest.concat(charAt(i));
      } else if (charAt(i) != charAt(j)) {
        dest = dest.concat(charAt(i));
      }
    }

    return dest;
  }

  public CharArray removeSequantialCharacters() {
    CharArray dest = CharArray.of("");
    int j;

    for (int i = 0; i < length(); i++) {
      j = i + 1;

      if (i == length() - 1) { // if last
        dest = dest.concat(charAt(i));
      } else if (charAt(i) != charAt(j) - 1) {
        dest = dest.concat(charAt(i));
      }
    }

    return dest;
  }


  @Override
  public String toString() {
    return String.valueOf(characters);
  }

  public char[] toCharArray() {
    return characters;
  }

  public CharArray toLowerCase() {
    int len = length();
    char[] dest = new char[len];

    for (int i = 0; i < len; i++) {
      dest[i] = Character.toLowerCase(charAt(i));
    }

    return CharArray.of(dest);
  }

  public CharArray toUpperCase() {
    int len = length();
    char[] dest = new char[len];

    for (int i = 0; i < len; i++) {
      dest[i] = Character.toUpperCase(charAt(i));
    }

    return CharArray.of(dest);
  }

  private static char[] toCharArray(CharSequence charSequence) {
    int len = charSequence.length();
    char[] characters = new char[len];

    for (int i = 0; i < len; i++) {
      characters[i] = charSequence.charAt(i);
    }

    return characters;
  }

  private static CharSequence toLowerCase(CharSequence src) {
    CharSequence dest = null;

    if (src instanceof CharArray) {
      dest = ((CharArray) src).toLowerCase();
    } else if (src instanceof String) {
      dest = ((String) src).toLowerCase();
    }

    return dest;
  }

  public CharArray transcode(CharSequence trancodage) {
    CharArray dest = this.copy();

    for (int i = 0; i < trancodage.length(); i++) {
      char trancoded = trancodage.charAt(i);
      char newChar = (char) ('a' + i);
      dest = dest.replace(trancoded, newChar);
    }

    return dest;
  }

  // FIXME
  public CharArray trim() {
    int len = length();
    char[] trimmed = new char[len];
    int j = 0;
    boolean hasNonBlank = false;

    for (int i = 0; i < len; i++) {
      char ch = this.charAt(i);
      boolean isBlank = (ch == ' ');
      hasNonBlank = hasNonBlank || !isBlank;
      boolean doCopy = !isBlank || hasNonBlank;

      if (doCopy) {
        trimmed[j++] = ch;
      }
    }

    return CharArray.of(trimmed);
  }



}
