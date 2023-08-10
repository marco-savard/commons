package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.domain.mountain.model.MountainModel2;
import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DynamicPackageTest {

  @Test
  public void testArrayListIsSubclassOfList() {
    Class[] classes = getUtilClasses();
    DynamicPackage pack = new DynamicPackage(classes);
    List<Class> subclasses = pack.getSubclasses(List.class);
    Assert.assertTrue(subclasses.contains(ArrayList.class));
  }

  @Test
  public void testInstantiate() {
    Class claz = MountainModel2.Mountain.class;
    DynamicPackage pack = new DynamicPackage(claz);

    try {
      Object instance = pack.instantiate(claz);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private Class[] getUtilClasses() {
    return new Class[] {List.class, ArrayList.class, LinkedList.class};
  }
}
