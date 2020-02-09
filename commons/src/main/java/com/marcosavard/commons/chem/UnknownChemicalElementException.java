package com.marcosavard.commons.chem;

import java.text.MessageFormat;
import com.marcosavard.commons.io.SerialVersion;

public class UnknownChemicalElementException extends RuntimeException {
  private static final long serialVersionUID =
      SerialVersion.of(UnknownChemicalElementException.class);

  private String unknownChemicalElement;

  public UnknownChemicalElementException(String unknownChemicalElement) {
    this.unknownChemicalElement = unknownChemicalElement;
  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("Unknown Chemical Element : {0}", unknownChemicalElement);
    return msg;
  }

}
