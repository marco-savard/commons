package com.marcosavard.commons.lang.reflect.meta;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class PojoGenerator {
    protected final File outputFolder;

    //Options
    protected int indentation = 2;
    protected boolean generateMetadata = false;

    protected boolean generateParameterlessConstructor = false;

    protected AccessorOrder accessorOrder = AccessorOrder.GROUPED_BY_PROPERTIES;

    protected String containerName = "owner";

    public enum AccessorOrder {
        GROUPED_BY_PROPERTIES,
        GROUPED_BY_GETTERS_SETTERS
    }

    protected PojoGenerator(File outputFolder) {
        this.outputFolder = outputFolder;
    }

    public PojoGenerator withAccessors(AccessorOrder accessorOrder) {
        this.accessorOrder = accessorOrder;
        return this;
    }

    public PojoGenerator withContainerName(String containerName) {
        this.containerName = containerName;
        return this;
    }

    public PojoGenerator withIndentation(int indentation) {
        this.indentation = indentation;
        return this;
    }

    public PojoGenerator withMetadata() {
        generateMetadata = true;
        return this;
    }

    public PojoGenerator withParameterlessConstructor() {
        generateParameterlessConstructor = true;
        return this;
    }

    public abstract File generateClass(MetaClass mc) throws IOException;
}
