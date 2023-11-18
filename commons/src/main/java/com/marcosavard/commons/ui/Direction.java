package com.marcosavard.commons.ui;

import com.marcosavard.commons.geog.Glossary;
import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.util.Locale;

public enum Direction {
  LEFT,
  RIGHT;

  private Glossary glossary = new DirectionGlossary();

  public String getDisplayName(Locale display) {
    UIDefaults uiDefaults = UIManagerFacade.getDefaults();
    String leftButton = uiDefaults.getString("SplitPane.leftButtonText", display);
    String rightButton = uiDefaults.getString("SplitPane.rightButtonText", display);
    String name = "?";

    if (this.equals(LEFT)) {
      name = String.join("", glossary.removeCommon(leftButton, rightButton));
    } else if (this.equals(RIGHT)) {
      name = String.join("", glossary.removeCommon(rightButton, leftButton));
    }

    return name;
  }

  private static class DirectionGlossary extends Glossary {}
}
