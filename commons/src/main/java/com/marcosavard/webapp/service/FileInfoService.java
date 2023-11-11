package com.marcosavard.webapp.service;

import com.marcosavard.webapp.model.FileData;
// import org.springframework.stereotype.Service;

// @Service
public class FileInfoService {
  private FileData fileData;

  public void store(FileData fileData) {
    this.fileData = fileData;
  }

  public FileData getFileData() {
    return fileData;
  }
}
