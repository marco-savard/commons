package com.marcosavard.common.io.reader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ResourceReader extends InputStreamReader {
    public ResourceReader(Class<?> resourceClass, String resource, Charset charset) {
        super(toInput(resourceClass, resource), charset);
    }

    public static InputStream toInput(Class<?> resourceClass, String resource) {
        Package pack = resourceClass.getPackage();
        String path = pack.getName().replace(".", "/") + "/" + resource;
        return resourceClass.getClassLoader().getResourceAsStream(path);
    }
}
