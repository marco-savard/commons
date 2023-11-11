package com.marcosavard.commons.ui;

import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.util.Locale;

public enum FileOperation {
  OPEN,
  REFRESH,
  RESET,
  RESTORE,
  SAVE;

  public String getDisplayName(Locale display) {
    UIDefaults uiDefaults = UIManagerFacade.getDefaults();
    String name = "?";

    if (this.equals(OPEN)) {
      name = uiDefaults.getString("FileChooser.openButtonText", display);
    } else if (this.equals(REFRESH)) {
      name = uiDefaults.getString("FileChooser.refreshActionLabelText", display);
    } else if (this.equals(RESET)) {
      name = uiDefaults.getString("FormView.resetButtonText", display);
    } else if (this.equals(RESTORE)) {
      name = uiDefaults.getString("InternalFrame.restoreButtonToolTip", display);
    } else if (this.equals(SAVE)) {
      name = uiDefaults.getString("FileChooser.saveButtonText", display);
    }

    return name;
  }
}
