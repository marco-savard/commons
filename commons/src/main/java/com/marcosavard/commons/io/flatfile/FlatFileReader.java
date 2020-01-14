package com.marcosavard.commons.io.flatfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public abstract class FlatFileReader {
  private BufferedReader bf;
  private List<FixedBlock> fixedBlocks = new ArrayList<>();
  private Deque<String> lineStack = new ArrayDeque<>();

  protected FlatFileReader(Class<?> resourceClaz, String resourceName, Charset encoding) {
    this(resourceClaz.getResourceAsStream(resourceName), encoding);
  }

  protected FlatFileReader(InputStream input, Charset encoding) {
    this(new InputStreamReader(input, encoding));
  }

  protected FlatFileReader(InputStream input) {
    this(input, Charset.defaultCharset());
  }

  protected FlatFileReader(Reader reader) {
    this.bf = new BufferedReader(reader);
    defineFileStructure();
  }

  protected abstract void defineFileStructure();

  protected FixedBlock ofBlock(String wildcard) {
    FixedBlock block = new FixedBlock(toRegex(wildcard));
    return block;
  }

  public FixedBlock ofBlock(FixedBlock... entries) {
    FixedBlock block = new FixedBlock(entries);
    return block;
  }

  public FlatFileReader withBlock(FixedBlock block) {
    fixedBlocks.add(block);
    return this;
  }

  public void readAll() {
    String line;

    do {
      try {
        line = readNextNotEmptyLine();

        if (line != null) {
          processLine(line);
        }
      } catch (IOException e) {
        line = null;
      }
    } while (line != null);
  }

  protected void processLine(String line) {
    for (FixedBlock block : fixedBlocks) {
      block.tryMatch(line);
    }
  }

  protected String readNextNotEmptyLine() throws IOException {
    String line;

    if (!lineStack.isEmpty()) {
      line = lineStack.pop();
    } else {
      do {
        line = this.bf.readLine();

        if (line == null || !line.equals("")) {
          break;
        }
      } while (true);
    }

    return line;
  }

  public static String toRegex(String wildcard) {
    String regex = wildcard.replaceAll("\\?", ".");
    regex = regex.replaceAll("\\*", ".+");
    return regex;
  }

  protected void onFixedBlockFound(FixedBlock fixedBlock, String line) {}

  public void close() throws IOException {
    this.bf.close();
  }

  public class FixedBlock {
    private String pattern;
    private FixedBlock[] subBlocks;

    public FixedBlock(String pattern) {
      this.pattern = pattern;
    }

    public FixedBlock(FixedBlock[] subBlocks) {
      this.subBlocks = subBlocks;
    }

    public void tryMatch(String line) {
      if (pattern != null) {
        boolean matched = line.matches(pattern);

        if (matched) {
          onFixedBlockFound(this, line);
        }
      } else if (subBlocks != null) {
        for (FixedBlock subBlock : subBlocks) {
          subBlock.tryMatch(line);
        }
      }
    }

    public FixedBlock repeatable() {
      return this;
    }



  }



}
