package com.marcosavard.commons.lang.reflect.meta;

public abstract class MetaPackage {

    public static MetaPackage of(Package pack) {
        return new ReflectiveMetaPackage(pack);
    }

    @Override
    public boolean equals(Object other) {
        boolean equal = false;

        if (other instanceof MetaPackage that) {
            equal = getName().equals(that.getName());
        }

        return equal;
    }

    public abstract String getName();

    @Override
    public String toString() {
        return getName();
    }

    public static class ReflectiveMetaPackage extends MetaPackage {
        private final Package pack;

        public ReflectiveMetaPackage(Package pack) {
            this.pack = pack;
        }

        @Override
        public String getName() {
            return (pack == null) ? "" : pack.getName();
        }
    }
}
