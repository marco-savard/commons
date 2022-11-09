package com.marcosavard.commons.lang.reflect.meta;

public abstract class MetaClass {

    public static MetaClass of(Class claz) {
        return new ReflectiveMetaClass(claz);
    }

    public abstract String getSimpleName();

    public abstract String getPackageName();

    public abstract boolean isEnum();

    //to be removed
    public Class getClaz() {
        return ((ReflectiveMetaClass)this).claz;
    }

    private static class ReflectiveMetaClass extends MetaClass {
        private final Class claz;

        public ReflectiveMetaClass(Class claz) {
            this.claz = claz;
        }

        public String getPackageName() {
            String modelPackage = claz.getPackageName();
            int idx = modelPackage.lastIndexOf('.');
            return modelPackage.substring(0, idx);
        }

        public String getSimpleName() {
            return claz.getSimpleName();
        }

        public boolean isEnum() {
            return claz.isEnum();
        }
    }
}
