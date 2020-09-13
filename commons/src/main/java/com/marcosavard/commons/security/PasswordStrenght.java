package com.marcosavard.commons.security;

import com.marcosavard.commons.lang.CharArray;

// simplify by:
// 1- lowercase; find in dictionary
// 2- replace @ by a, $ by s, 0 by o; find in dictionary
// 3- remove repeating characters; find in dictionary
// 4 -remove sequential characters; find in dictionary
// qwerty is a sequence
//
public class PasswordStrenght {
  private DictionaryPresenceMethod dictionaryPresenceMethod = new DictionaryPresenceMethod();
  private PasswordStrenghtMethod[] strenghtMethods = new PasswordStrenghtMethod[] { //
      new LowercaseMethod(), // lowercase password and evaluates its strenght
      dictionaryPresenceMethod, //
      new RepeatingCharacterMethod(), //
      new SequentialCharacterMethod(), //
      new SpecialCharacterReplacementMethod(), //
      dictionaryPresenceMethod, //
      new RepeatingCharacterMethod(), //
      new SequentialCharacterMethod() //
  };
  private int strenght;

  public static PasswordStrenght of(Password password) {
    PasswordStrenght strenght = new PasswordStrenght(password);
    return strenght;
  }

  private PasswordStrenght(Password password) {
    char[] characters = password.getCharacters();
    int entropy, lowestEntropy = Integer.MAX_VALUE;

    for (PasswordStrenghtMethod method : strenghtMethods) {
      CharArray simplified = method.simplify(CharArray.of(characters));
      characters = simplified.toCharArray();
      entropy = method.evaluateStrenght(characters);
      lowestEntropy = Math.min(entropy, lowestEntropy);
    }

    strenght = lowestEntropy;
    // boolean sequence = isRepeating(characters);
    // boolean repeating = isSequence(characters);

    // strenght = password.getEntropy();
    // strenght = repeating ? 1 : strenght;
    // strenght = sequence ? 1 : strenght;
  }

  private boolean isRepeating(char[] characters) {
    boolean repeating = true;
    char previous = characters[0];

    for (int i = 1; i < characters.length; i++) {
      repeating = (characters[i] == previous);
      previous = characters[i];

      if (!repeating) {
        break;
      }
    }
    return repeating;
  }

  private boolean isSequence(char[] characters) {
    boolean sequence = true;
    char previous = characters[0];

    for (int i = 1; i < characters.length; i++) {
      sequence = (characters[i] == (previous + 1));
      previous = characters[i];

      if (!sequence) {
        break;
      }
    }
    return sequence;
  }

  @Override
  public String toString() {
    return Integer.toString(strenght);
  }

  private static abstract class PasswordStrenghtMethod {

    abstract CharArray simplify(CharArray src);

    public int evaluateStrenght(char[] characters) {
      CharArray dest = simplify(CharArray.of(characters));
      int entropy = Password.of(dest.toCharArray()).getEntropy();
      return entropy;
    }
  }

  private static class LowercaseMethod extends PasswordStrenghtMethod {
    @Override
    CharArray simplify(CharArray src) {
      CharArray dest = src.toLowerCase();
      return dest;
    }
  }

  private static class SpecialCharacterReplacementMethod extends PasswordStrenghtMethod {
    @Override
    public CharArray simplify(CharArray src) {
      CharArray dest = src.replace('@', 'a');
      dest = dest.replace('$', 's');
      dest = dest.replace('0', 'o');
      dest = dest.replace('1', 'l');
      dest = dest.replace('3', 'e');
      return dest;
    }
  }

  private static class RepeatingCharacterMethod extends PasswordStrenghtMethod {
    @Override
    CharArray simplify(CharArray src) {
      CharArray dest = src.removeRepeatingCharacters();
      dest = dest.transcode("qwertyuiop");
      dest = dest.removeRepeatingCharacters();
      return dest;
    }
  }

  private static class SequentialCharacterMethod extends PasswordStrenghtMethod {
    @Override
    CharArray simplify(CharArray src) {
      CharArray dest = src.removeSequantialCharacters();
      return dest;
    }
  }

  public String removeDuplicates(String input) {
    String result = "";
    for (int i = 0; i < input.length(); i++) {
      if (!result.contains(String.valueOf(input.charAt(i)))) {
        result += String.valueOf(input.charAt(i));
      }
    }
    return result;
  }


  private static class DictionaryPresenceMethod extends PasswordStrenghtMethod {
    private static final String[] DICTIONARY = {"password"};

    @Override
    public CharArray simplify(CharArray src) {
      CharArray dest = src;

      for (String word : DICTIONARY) {
        int idx = src.indexOf(word.toCharArray());

        if (idx >= 0) {
          int len = word.length();
          dest = CharArray.of(src.subSequence(0, idx))
              .concat(CharArray.of(src.subSequence(idx + len)));
        }
      }

      return dest;
    }
  }

}
