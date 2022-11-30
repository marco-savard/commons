package com.marcosavard.webapp.service;

import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.PojoGenerator;
import com.marcosavard.library.javaparser.generate.SourceBasedPojoGenerator;
import com.marcosavard.webapp.model.PojoModel;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class PojoGenService {
    private PojoModel pojoModel;

    public PojoGenService() {
    }

    public void store(PojoModel pojoModel) {
        this.pojoModel = pojoModel;
    }

    public PojoModel getPojoModel() {
        return pojoModel;
    }

    public void process(PojoModel pojoModel) {
        //generate Java files
        Reader reader = new StringReader(pojoModel.getModelAsString());
        Map<MetaClass, String> pojoByMetaclass = new HashMap<>();
        PojoGenerator pojoGenerator = new SourceBasedPojoGenerator(reader, pojoByMetaclass);
        pojoGenerator.generatePojos();
        pojoModel.storePojos(pojoByMetaclass);
    }
}
