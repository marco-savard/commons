package com.marcosavard.commons.lang;

interface CharStringOperations {

  /**
   * Capitalize the original string.
   * 
   * @return capitalized string
   */
  public String capitalize();

  /**
   * Capitalize each words in sentence
   * 
   * @return capitalized string
   */
  public String capitalizeWords();

  public boolean equalsIgnoreAccents(String that);

  /**
   * Pad n blanks at left until totalLength is reached
   * 
   * @param source a given string
   * @param length of padded string
   * @return padded string
   */
  public String padLeft(int totalLength);

  /**
   * Pad n blanks at right until totalLength is reached
   * 
   * @param source a given string
   * @param length of padded string
   * @return padded string
   */
  public String padRight(int totalLength);

  public String[] splitLine();

  /**
   * Strip off accents from characters
   * 
   * @param text with accents
   * @return stripped text
   */
  public String stripAccents();

  /**
   * Return a string in which all the blanks (whitespaces and tabs) of the text parameter are
   * stripped off. For instance, stripBlanks("hello world") returns "helloworld".
   * 
   * @param text the original String, that may contain characters with blanks.
   * @return the same String, but without blanks
   */
  public String stripBlanks();

  /**
   * Strip off non digit characters
   * 
   * @param text a given string
   * @return stripped string
   */
  public String stripNonDigit();

  /**
   * Convert "HELLO_WORLD" to "Hello World"
   * 
   * @return string to display
   */
  public String toDisplayString();

  public String trimDoubleBlanks();


  /**
   * Returns the same String, but without the quotes. For instance,
   * removeSurroundingQuotes("'text'", '\'') returns "text".
   * 
   * @param quoteCharacter such as ' or "
   * @return the text without the quotes, if any
   */
  public String unquote(char quoteCharacter);
}
