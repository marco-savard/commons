package com.marcosavard.commons.ui;

import com.marcosavard.commons.geog.Glossary;
import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.util.Locale;

public enum Collection {
  LIST,
  DIRECTORY;

  private Glossary glossary = new CollectionGlossary();

  public String getDisplayName(Locale display) {
    UIDefaults uiDefaults = UIManagerFacade.getDefaults();
    String name = "?";

    if (this.equals(LIST)) {
      name = uiDefaults.getString("FileChooser.listViewActionLabelText", display);
    } else if (this.equals(DIRECTORY)) {
      name = uiDefaults.getString("FileChooser.directoryDescription.textAndMnemonic", display);
    }

    return name;
  }

  private static class CollectionGlossary extends Glossary {}
}
