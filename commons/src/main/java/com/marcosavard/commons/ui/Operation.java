package com.marcosavard.commons.ui;

import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.util.Locale;

public enum Operation {
  ADD,
  REMOVE;

  public String getDisplayName(Locale display) {
    UIDefaults uiDefaults = UIManagerFacade.getDefaults();
    String name = "?";

    if (this.equals(ADD)) {
      name = uiDefaults.getString("AbstractDocument.additionText", display);
    } else if (this.equals(REMOVE)) {
      name = uiDefaults.getString("AbstractDocument.deletionText", display);
    }

    return name;
  }
}
