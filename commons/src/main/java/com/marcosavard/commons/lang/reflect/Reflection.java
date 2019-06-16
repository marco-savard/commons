package com.marcosavard.commons.lang.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Reflection {

  public static Object get(Object instance, String fieldName) throws NoSuchFieldException {
    Field field = getDeclaredField(instance.getClass(), fieldName);
    Object value;

    try {
      if (field == null) {
        throw new NoSuchFieldException(fieldName);
      }

      field.setAccessible(true);
      value = field.get(instance);
    } catch (IllegalAccessException e) {
      value = null;
    }

    return value;
  }

  public static void set(Object instance, String fieldName, Object value)
      throws NoSuchFieldException {
    Field field = getDeclaredField(instance.getClass(), fieldName);

    try {
      if (field == null) {
        throw new NoSuchFieldException(fieldName);
      }

      field.setAccessible(true);
      field.set(instance, value);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static Object invoke(Object instance, String methodName, Object... args) {
    List<Method> namedMethods = findNamedMethods(instance.getClass(), methodName, args);
    Object value = null;

    if (namedMethods.size() >= 1) {
      Method namedMethod = namedMethods.get(0);
      try {
        value = namedMethod.invoke(instance, args);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }

    return value;
  }

  public static Object invokeStatic(Class claz, String methodName, Object... args) {
    List<Method> namedMethods = findNamedMethods(claz, methodName, args);
    Object value = null;

    if (namedMethods.size() >= 1) {
      Method namedMethod = namedMethods.get(0);
      try {
        value = namedMethod.invoke(null, args);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }

    return value;
  }



  //
  // private
  //
  private static List<Method> findNamedMethods(Class<? extends Object> clazz, String methodName,
      Object... args) {
    List<Method> namedMethods = new ArrayList<>();
    Method[] allMethods = clazz.getDeclaredMethods();

    for (Method method : allMethods) {
      if (methodName.equals(method.getName())) {
        namedMethods.add(method);
      }
    }

    List<Method> compliantNamedMethods = new ArrayList<>();
    for (Method method : namedMethods) {
      boolean compliant = isCompliantMethod(method, args);

      if (compliant) {
        compliantNamedMethods.add(method);
      }
    }

    if (compliantNamedMethods.size() == 0) {
      Class superClass = clazz.getSuperclass();

      if (superClass != null) {
        compliantNamedMethods = findNamedMethods(superClass, methodName, args);
      }
    }

    return compliantNamedMethods;
  }

  private static boolean isCompliantMethod(Method method, Object[] args) {
    Class[] parameterTypes = method.getParameterTypes();
    boolean compliant = parameterTypes.length == args.length;

    if (compliant) {
      for (int i = 0; i < args.length; i++) {
        if (args[i] != null) {
          compliant = compliant && isCompliant(args[i], parameterTypes[i]);
        }
      }
    }

    return compliant;
  }

  private static boolean isCompliant(Object value, Class type) {
    type = type.isPrimitive() ? getWrapperType(type) : type;
    boolean compliant = type.isAssignableFrom(value.getClass());
    return compliant;
  }

  private static Class getWrapperType(Class type) {
    Class wrapperType = null;

    if (boolean.class.equals(type)) {
      wrapperType = Boolean.class;
    } else if (byte.class.equals(type)) {
      wrapperType = Byte.class;
    } else if (char.class.equals(type)) {
      wrapperType = Character.class;
    } else if (double.class.equals(type)) {
      wrapperType = Double.class;
    } else if (float.class.equals(type)) {
      wrapperType = Float.class;
    } else if (int.class.equals(type)) {
      wrapperType = Integer.class;
    } else if (long.class.equals(type)) {
      wrapperType = Long.class;
    } else if (short.class.equals(type)) {
      wrapperType = Short.class;
    }

    return wrapperType;
  }

  // find field in superclasses, recursively
  private static Field getDeclaredField(Class clazz, String fieldName) throws NoSuchFieldException {
    Field field = null;

    try {
      field = clazz.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      Class superClass = clazz.getSuperclass();
      boolean isRoot = Object.class.equals(superClass);
      field = isRoot ? null : getDeclaredField(superClass, fieldName);
    }

    return field;
  }



}
