package com.marcosavard.commons.io.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;

public class ResourceWriter extends FileWriter {

    public ResourceWriter(String path, Class<?> resourceClass, String filename, Charset charset) throws IOException {
        super(toFile(resourceClass, path, filename), charset);
    }

    private static File toFile(Class<?> resourceClass, String path, String filename) {
        Package pack = resourceClass.getPackage();
        String fullpath = path + "/" + pack.getName().replace(".", "/") + "/" + filename;
        return new File(fullpath);
    }


}
