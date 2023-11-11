package com.marcosavard.commons.ui;

import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.util.Locale;

public enum Affirmation {
  YES,
  NO;

  public String getDisplayName(Locale display) {
    UIDefaults uiDefaults = UIManagerFacade.getDefaults();
    String name = "?";

    if (this.equals(YES)) {
      name = uiDefaults.getString("OptionPane.yesButtonText", display);
    } else if (this.equals(NO)) {
      name = uiDefaults.getString("OptionPane.noButtonText", display);
    }

    return name;
  }
}
