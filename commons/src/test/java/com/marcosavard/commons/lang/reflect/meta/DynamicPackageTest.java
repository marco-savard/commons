package com.marcosavard.commons.lang.reflect.meta;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DynamicPackageTest {

    @Test
    public void testArrayListSubclassOfList() {
        Class[] classes = getUtilClasses();
        DynamicPackage pack = new DynamicPackage(classes);
        List<Class> subclasses = pack.getSubclasses(List.class);
        Assert.assertTrue(subclasses.contains(ArrayList.class));
    }

    private Class[] getUtilClasses() {
        return new Class[] {List.class, ArrayList.class, LinkedList.class};
    }
}
