package com.marcosavard.commons.io;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContentReader {
  public String readContent(Class claz, String filename) {
    URL url = claz.getResource(filename);
    String content;

    try {
      Path path = Paths.get(url.toURI());
      byte[] bytes = Files.readAllBytes(path);
      content = new String(bytes);
    } catch (Exception e) {
      content = null;
    }

    return content;
  }
}
