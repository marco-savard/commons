package com.marcosavard.commons.io.flatfile;

import java.util.List;

public abstract class CapitalOneBlockHandler {

  public void onStart() {}

  public void onHeaderEvent(String header) {}

  public void onEntryEvent(List<String> header) {}

  public void onTrailerEvent(String header) {}

  public void onFinish() {}

}
