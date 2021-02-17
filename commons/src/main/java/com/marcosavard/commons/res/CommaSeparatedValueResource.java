package com.marcosavard.commons.res;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.marcosavard.commons.io.csv.CsvReader;

public abstract class CommaSeparatedValueResource {
  private String ressourceName;
  private Charset charSet;
  private char commentCharacter;
  private int nbHeaders;
  private char delimiter;
  private List<String> columns;
  private List<String[]> rows;

  protected CommaSeparatedValueResource(String ressourceName, Charset charSet) {
    this.ressourceName = ressourceName;
    this.charSet = charSet;
  }

  protected CommaSeparatedValueResource withNbHeaders(int nbHeaders) {
    this.nbHeaders = nbHeaders;
    return this;
  }

  protected CommaSeparatedValueResource withDelimiter(char delimiter) {
    this.delimiter = delimiter;
    return this;
  }

  protected CommaSeparatedValueResource withCommentCharacter(char commentCharacter) {
    this.commentCharacter = commentCharacter;
    return this;
  }


  public void loadAll() {
    load(this);
  }

  protected void load(CommaSeparatedValueResource ressource) {
    Class<? extends CommaSeparatedValueResource> claz = ressource.getClass();
    InputStream input = claz.getResourceAsStream(ressourceName);

    try {
      Reader r = new InputStreamReader(input, charSet.name());
      CsvReader cr = CsvReader.of(r).withHeader(nbHeaders, delimiter);
      columns = Arrays.asList(cr.readHeaderColumns());
      rows = cr.readAll();



      // //cr.close();


    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<String> getColumns() {
    return columns;
  }

  public List<String[]> getRows() {
    return rows;
  }

  public List<String[]> where(Predicate<String[]> predicate) {
    List<String[]> filtered = rows.stream().filter(predicate).collect(Collectors.toList());
    return filtered;
  }


}
