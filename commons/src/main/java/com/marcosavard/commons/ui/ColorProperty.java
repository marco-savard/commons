package com.marcosavard.commons.ui;

import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.util.Locale;

public enum ColorProperty {
  HUE,
  LIGHTNESS,
  SATURATION,
  TRANSPARENCY;

  public String getDisplayName(Locale display) {
    UIDefaults uiDefaults = UIManagerFacade.getDefaults();
    String name = "?";

    if (this.equals(HUE)) {
      name = uiDefaults.getString("ColorChooser.hslHue.textAndMnemonic", display);
    } else if (this.equals(LIGHTNESS)) {
      name = uiDefaults.getString("ColorChooser.hslLightness.textAndMnemonic", display);
    } else if (this.equals(SATURATION)) {
      name = uiDefaults.getString("ColorChooser.hslSaturation.textAndMnemonic", display);
    } else if (this.equals(TRANSPARENCY)) {
      name = uiDefaults.getString("ColorChooser.hslTransparency.textAndMnemonic", display);
    }

    return name;
  }
}
