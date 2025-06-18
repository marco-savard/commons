package com.marcosavard.docmodule.template;

import com.marcosavard.common.io.reader.ResourceReader;

import java.io.InputStream;

public class SunEventTemplateReader {
    private InputStream input;

    public SunEventTemplateReader() {
        input = ResourceReader.toInput(SunEventTemplateReader.class, "sunEvents.template.docx");
    }

    public InputStream getInput() {
        return input;
    }
}
