package com.marcosavard.commons.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.marcosavard.commons.io.csv.CsvReader;

/**
 * A color palette is simply a set of colors identified by a name.
 * 
 * @author Marco
 *
 */
public class ColorPalette {
  // named colors: //http://html-color-codes.info/color-names/
  // named colors: //http://html-color-codes.info/color-names/
  private static ColorPalette singleInstance;

  public static ColorPalette getColorPalette() {
    if (singleInstance == null) {
      singleInstance = new ColorPalette();
    }

    return singleInstance;
  }

  private Map<String, Color> colorMap = new HashMap<>();
  private List<NamedColor> namedColors = new ArrayList<>();

  public List<NamedColor> getNamedColors() {
    return namedColors;
  }

  private ColorPalette() {
    try {
      // read csv
      InputStream input = ColorPalette.class.getResourceAsStream("ColorPalette.csv");
      Reader reader = new InputStreamReader(input);
      this.namedColors = readColors(reader);
    } catch (IOException e) {
      // File is missing
      e.printStackTrace();
    }
  }

  private List<NamedColor> readColors(Reader reader) throws IOException {
    CsvReader cr = CsvReader.of(reader).withSeparator(';');
    cr.readHeaderColumns();
    List<NamedColor> namedColors = new ArrayList<>();

    while (cr.hasNext()) {
      String[] line = cr.readNext();

      if (line.length > 0) {
        NamedColor namedColor = readColor(line);
        namedColors.add(namedColor);
      }
    }

    return namedColors;
  }

  private NamedColor readColor(String[] line) {
    String code = line[0];
    String name = line[1];
    Color color = Color.of(Integer.decode(code));
    NamedColor namedColor = new NamedColor(color, name);
    return namedColor;
  }

  public void addNamedColor(String colorName, int colorCode) {
    colorMap.put(colorName, Color.of(colorCode));
  }

  public static class NamedColor {
    private Color color;
    private String name;

    public NamedColor(Color color, String name) {
      this.color = color;
      this.name = name;
    }

    public Color getColor() {
      return color;
    }

    @Override
    public String toString() {
      String msg = MessageFormat.format("{0} {1}", name, color.toRGBString());
      return msg;
    }
  }
}
