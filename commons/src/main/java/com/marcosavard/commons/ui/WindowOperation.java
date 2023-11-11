package com.marcosavard.commons.ui;

import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.util.Locale;

public enum WindowOperation {
  CLOSE,
  ENLARGE,
  OPEN,
  REDUCE,
  RESIZE;

  public String getDisplayName(Locale display) {
    UIDefaults uiDefaults = UIManagerFacade.getDefaults();
    String name = "?";

    if (this.equals(CLOSE)) {
      name = uiDefaults.getString("FileChooser.openButtonText", display);
    } else if (this.equals(ENLARGE)) {
      name = uiDefaults.getString("InternalFrame.closeButtonToolTip", display);
    } else if (this.equals(OPEN)) {
      name = uiDefaults.getString("InternalFrame.iconButtonToolTip", display);
    } else if (this.equals(REDUCE)) {
      name = uiDefaults.getString("InternalFrame.maxButtonToolTip", display);
    } else if (this.equals(RESIZE)) {
      name = uiDefaults.getString("InternalFrameTitlePane.sizeButtonText", display);
    }

    return name;
  }
}
