package com.marcosavard.common.ui;

import com.marcosavard.common.lang.StringUtil;
import com.marcosavard.common.ui.res.UIManagerFacade;
import com.marcosavard.common.util.RessourceEnum;

import javax.swing.*;
import java.util.Locale;

public enum Action implements RessourceEnum {
    ABORT("PrintingDialog.abortButtonText"),
    BROWSE("FormView.browseFileButtonText"),
    CANCEL("OptionPane.cancelButtonText"),
    CLOSE("InternalFrameTitlePane.closeButtonText"),
    CLOSE_WINDOW,
    COPY,
    CUT,
    DELETE,
    EXIT,
    FIND,
    FIND_NEXT,
    FIND_PREVIOUS,
    LOG_IN,
    LOG_OUT,
    MAXIMIZE("InternalFrameTitlePane.maximizeButtonText"),
    MINIMIZE("InternalFrameTitlePane.minimizeButtonText"),
    MOVE("InternalFrameTitlePane.moveButtonText"),
    OPEN("FileChooser.directoryOpenButtonText"),
    PASTE,
    PRINT,
    QUIT,
    REDO("AbstractDocument.redoText"),
    REFRESH("FileChooser.refreshActionLabelText"),
    RESET("FormView.resetButtonText"),
    RESTORE("InternalFrameTitlePane.restoreButtonText"),
    SAVE("FileChooser.saveButtonText"),
    SAVE_AS,
    SEARCH,
    SELECT,
    SELECT_ALL,
    UNDO("AbstractDocument.undoText"),
    UPDATE("FileChooser.updateButtonText");

    private static final UIDefaults uiDefaults = UIManagerFacade.getDefaults();
    private static final String ELLIPSIS = "...";
    private final String key;

    Action() {
        this.key = null;
    }

    Action(String key) {
        this.key = key;
    }

    public String getDisplayName(Locale display) {
        if (key != null) {
            return uiDefaults.getString(key, display).replace(ELLIPSIS, "");
        } else {
            return normalize(RessourceEnum.super.getDisplayName(display), display);
        }
    }

    private String normalize(String original, Locale display) {
        return StringUtil.toTitleCase(original.replace('_', ' '), display);
    }


}
