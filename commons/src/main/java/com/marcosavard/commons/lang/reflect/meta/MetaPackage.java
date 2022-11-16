package com.marcosavard.commons.lang.reflect.meta;

public abstract class MetaPackage {

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
        private final String name;
        private final Package pack;

        public static MetaPackage of(Package pack) {
            String name = (pack == null) ? "" : pack.getName();
            MetaPackage mp = MetaModel.getInstance().findPackageByName(name);
            mp = (mp != null) ? mp :  new ReflectiveMetaPackage(pack);
            return mp;
        }

        private ReflectiveMetaPackage(Package pack) {
            this.pack = pack;
            this.name = (pack == null) ? "" : pack.getName();
            MetaModel.getInstance().addPackage(this.name, this);
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
