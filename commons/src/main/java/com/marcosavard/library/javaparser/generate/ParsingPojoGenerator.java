package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.ast.CompilationUnit;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.MetaField;
import com.marcosavard.commons.lang.reflect.meta.PojoGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsingPojoGenerator extends PojoGenerator {
    protected CompilationUnit cu;

    protected ParsingPojoGenerator(File outputFolder, CompilationUnit cu) {
        super(outputFolder);
        this.cu = cu;
    }

    @Override
    protected String getInitialValue(MetaField mf) {
        return "null";
    }

    @Override
    protected MetaField getReferenceForClass(MetaClass mc) {
        return null;
    }

    @Override
    protected String getGetterName(MetaField mf) {
        String verb = "get";
        return verb + StringUtil.capitalize(mf.getName());
    }

    @Override
    protected List<MetaClass> getSubClasses(MetaClass mc) {
        return new ArrayList<>();
    }

}
