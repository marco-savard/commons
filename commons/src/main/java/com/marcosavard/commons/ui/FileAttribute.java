package com.marcosavard.commons.ui;

import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.util.Locale;

public enum FileAttribute {
  NAME,
  SIZE,
  TYPE;

  public String getDisplayName(Locale display) {
    UIDefaults uiDefaults = UIManagerFacade.getDefaults();
    String name = "?";

    if (this.equals(NAME)) {
      name = uiDefaults.getString("FileChooser.fileNameHeaderText", display);
    } else if (this.equals(SIZE)) {
      name = uiDefaults.getString("FileChooser.fileSizeHeaderText", display);
    } else if (this.equals(TYPE)) {
      name = uiDefaults.getString("FileChooser.fileTypeHeaderText", display);
    }

    return name;
  }
}
