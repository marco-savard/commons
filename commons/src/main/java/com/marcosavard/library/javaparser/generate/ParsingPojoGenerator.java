package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.PojoGenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ParsingPojoGenerator extends PojoGenerator {

    protected ParsingPojoGenerator(File outputFolder) {
        super(outputFolder);
    }

    @Override
    public File generateClass(MetaClass mc) throws IOException {
        return null;
    }

}