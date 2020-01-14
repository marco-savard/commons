package com.marcosavard.commons.io.flatfile;

import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CapitalOneFileReader extends FlatFileReader {
  private static final Charset ENCODING_CHARSET = StandardCharsets.UTF_8;

  private FixedBlock header;
  private FixedBlock startEntry;
  private FixedBlock middleEntry;
  private FixedBlock endEntry;
  private FixedBlock trailer;
  private List<String> currentEntry = new ArrayList<>();
  private List<CapitalOneBlockHandler> handlers = new ArrayList<>();

  public static CapitalOneFileReader of(Class<?> claz, String filename) {
    CapitalOneFileReader reader = new CapitalOneFileReader(claz, filename, ENCODING_CHARSET);
    return reader;
  }

  public static CapitalOneFileReader of(Reader r) {
    CapitalOneFileReader reader = new CapitalOneFileReader(r);
    return reader;
  }

  public CapitalOneFileReader(Class<?> claz, String filename, Charset charset) {
    super(claz, filename, charset);
  }

  public CapitalOneFileReader(Reader reader) {
    super(reader);
  }

  protected void defineFileStructure() {
    header = ofBlock("1*");
    startEntry = ofBlock("5*");
    middleEntry = ofBlock("6*").repeatable();
    endEntry = ofBlock("8*");
    trailer = ofBlock("9*");

    // define file structure
    withBlock(header) //
        .withBlock(ofBlock(startEntry, middleEntry, endEntry).repeatable()) //
        .withBlock(trailer);
  }

  public void addBlockHandler(CapitalOneBlockHandler handler) {
    handlers.add(handler);
  }

  public void readAll() {
    for (CapitalOneBlockHandler handler : handlers) {
      handler.onStart();
    }

    super.readAll();

    for (CapitalOneBlockHandler handler : handlers) {
      handler.onFinish();
    }
  }

  protected void onFixedBlockFound(FixedBlock fixedBlock, String line) {
    List<String> copiedEntry = null;

    if (fixedBlock.equals(startEntry)) {
      currentEntry.add(line);
    } else if (fixedBlock.equals(middleEntry)) {
      currentEntry.add(line);
    } else if (fixedBlock.equals(endEntry)) {
      currentEntry.add(line);
      copiedEntry = new ArrayList<>(currentEntry);
      currentEntry.clear();
    }

    for (CapitalOneBlockHandler handler : handlers) {
      if (fixedBlock.equals(header)) {
        handler.onHeaderEvent(line);
      } else if (fixedBlock.equals(endEntry)) {
        handler.onEntryEvent(copiedEntry);
      } else if (fixedBlock.equals(trailer)) {
        handler.onTrailerEvent(line);
      }
    }
  }

  public List<String> getCurrentEntry() {
    return currentEntry;
  }
}
