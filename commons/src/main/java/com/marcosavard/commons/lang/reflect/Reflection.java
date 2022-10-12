package com.marcosavard.commons.lang.reflect;

import com.marcosavard.commons.lang.NullSafe;
import com.marcosavard.commons.lang.StringUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static java.util.Comparator.comparing;

public class Reflection {

  private Reflection() {}

  public static <T> T instantiate(Class<T> claz) {
    T instance = null;

    if (claz.isArray()) {
      instance = instantiateArray(claz);
    }

    if (instance == null) {
      instance = instantiateByDefaultConstructor(claz);
    }

    if ((instance == null) && Number.class.isAssignableFrom(claz)) {
      instance = instantiateByStringParameter(claz, "0");
    }

    if ((instance == null) && Boolean.class.isAssignableFrom(claz)) {
      instance = instantiateByStringParameter(claz, "0");
    }

    if ((instance == null) && URI.class.isAssignableFrom(claz)) {
      instance = instantiateByStringParameter(claz, "0.0.0.0");
    }

    if (instance == null) {
      instance = instantiateByIntegerParameters(claz);
    }

    if (instance == null) {
      instance = instantiateByMinValue(claz);
    }

    if (instance == null) {
      instance = instantiateByFactoryMethod(claz);
    }

    return instance;
  }

  public static boolean is(Object instance, String fieldName) {
    String method = "is" + StringUtil.capitalize(fieldName);
    return (Boolean) invoke(instance, method);
  }

  public static Object get(Object instance, String fieldName) {
    String method = "get" + StringUtil.capitalize(fieldName);
    return invoke(instance, method);
  }

  public static void set(Object instance, String fieldName, Object... values) {
    String method = "set" + StringUtil.capitalize(fieldName);
    invoke(instance, method, values);
  }

  public static boolean isAbstract(Member member) {
    return Modifier.isAbstract(member.getModifiers());
  }

  public static boolean isPublic(Member member) {
    return Modifier.isPublic(member.getModifiers());
  }

  public static boolean isStatic(Member member) {
    return Modifier.isStatic(member.getModifiers());
  }

  public static boolean isTransient(Member member) {
    return Modifier.isTransient(member.getModifiers());
  }

  public static Object invoke(Object instance, String methodName, Object... args) {
    List<Method> namedMethods = findNamedMethods(instance.getClass(), methodName, args);
    Object value = null;

    if (namedMethods.size() >= 1) {
      Method namedMethod = namedMethods.get(0);
      try {
        value = namedMethod.invoke(instance, args);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        value = null;
      }
    }

    return value;
  }

  public static Object invoke(Object instance, Method method) {
    Object value;

    try {
      value = method.invoke(instance, null);
    } catch (IllegalAccessException | InvocationTargetException e) {
      value = null;
    }

    return value;
  }

  public static Object invokeStatic(Class claz, String methodName, Object arg) {
    Object[] args = new Object[] {arg};
    return invokeStatic(claz, methodName, args);
  }

  public static Object invokeStatic(Class claz, String methodName, Object[] args) {
    List<Method> namedMethods = findNamedMethods(claz, methodName, args);
    Object value = null;

    if (namedMethods.size() >= 1) {
      Method namedMethod = namedMethods.get(0);
      value = invokeStatic(namedMethod, args);
    }

    return value;
  }

  // TODO
  // https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
  public static Class[] getClasses(Package pack) {
    List<Class> classes = new ArrayList<>();

    try {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      String path = pack.getName().replace('.', '/');
      Enumeration<URL> resources = classLoader.getResources(path);
      List<File> dirs = new ArrayList<>();

      while (resources.hasMoreElements()) {
        URL resource = resources.nextElement();
        dirs.add(new File(resource.getFile()));
      }

      for (File directory : dirs) {
        classes.addAll(findClasses(directory, pack.getName()));
      }

    } catch (IOException | ClassNotFoundException e) {
      // return empty array
    }

    return classes.toArray(new Class[classes.size()]);
  }

  public static String toString(Object instance) {
    List<Class> types = Arrays.asList(new Class[] {String.class, boolean.class, int.class});
    List<Method> allMethods = Arrays.asList(instance.getClass().getMethods());
    List<String> values = new ArrayList<>();

    List<Method> getters =
        allMethods.stream()
            .filter(m -> m.getParameterCount() == 0)
            .filter(m -> m.getName().startsWith("get") || m.getName().startsWith("is"))
            .filter(m -> types.contains(m.getReturnType()))
            .sorted(comparing(Method::getName))
            .toList();

    for (Method m : getters) {
      String name = m.getName();
      name = name.startsWith("get") ? name.substring(3) : name;
      name = name.startsWith("is") ? name.substring(2) : name;
      name = StringUtil.uncapitalize(name);
      Object value = invoke(instance, m);

      if (value instanceof Boolean) {
        if ((Boolean) value == true) {
          values.add(name);
        }
      } else {
        values.add(name + "=" + value.toString());
      }
    }

    String joined = String.join(",", values);
    return "[" + joined + "]";
  }

  //
  // private methods
  //

  private static <T> T instantiateArray(Class<T> claz) {
    return (T) Array.newInstance(claz.getComponentType(), 0);
  }

  private static <T> T instantiateByDefaultConstructor(Class<T> claz) {
    Constructor<T> constructor = findDefaultConstructor(claz);
    T instance = null;

    if (constructor != null) {
      Object[] argumentless = new Object[] {};
      try {
        instance = constructor.newInstance(argumentless);
      } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
        instance = null;
      }
    }

    return instance;
  }

  private static <T> T instantiateByStringParameter(Class<T> claz, String argument) {
    Constructor<T> constructor = findStringConstructor(claz);
    T instance = null;

    if (constructor != null) {
      try {
        Object[] arguments = new Object[] {argument};
        instance = constructor.newInstance(arguments);
      } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
        instance = null;
      }
    }

    return instance;
  }

  private static <T> T instantiateByIntegerParameters(Class<T> claz) {
    Constructor<T> constructor = findIntegerConstructor(claz);
    T instance = null;

    if (constructor != null) {
      try {
        Object[] arguments = new Object[] {0};
        instance = constructor.newInstance(arguments);
      } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
        instance = null;
      }
    }
    return instance;
  }

  private static <T> T instantiateByFactoryMethod(Class<T> claz) {
    Method[] methods = getFactoryMethods(claz);
    T instance = null;

    for (Method method : methods) {
      instance = instantiateByFactoryMethod(claz, method);

      if (instance != null) {
        break;
      }
    }
    return instance;
  }

  private static <T> Method[] getFactoryMethods(Class<T> claz) {
    List<Method> methods = Arrays.asList(claz.getDeclaredMethods());
    List<Method> factoryMethods =
        methods.stream()
            .filter(m -> isStatic(m))
            .sorted(comparing(Method::getParameterCount))
            .toList();
    Method[] array = factoryMethods.toArray(new Method[0]);
    return array;
  }

  private static <T> T instantiateByFactoryMethod(Class<T> claz, Method method) {
    T instance = null;
    Class type = method.getReturnType();
    Object[] args = getMethodArguments(method);

    if (claz.isAssignableFrom(type)) {
      instance = (T) invokeStatic(method, args);
    } else if (type.isArray() && claz.isAssignableFrom(type.getComponentType())) {
      T[] instances = (T[]) invokeStatic(method, args);
      instance = instances[0];
    }

    return instance;
  }

  private static Object[] getMethodArguments(Method method) {
    Object[] arguments = new Object[method.getParameterCount()];

    for (int i = 0; i < method.getParameterCount(); i++) {
      Class type = method.getParameterTypes()[i];
      arguments[i] = instantiate(type);
    }

    Object result =
        (arguments.length > 1) ? arguments : (arguments.length == 1) ? arguments[0] : null;
    return arguments;
  }

  private static <T> T instantiateByMinValue(Class<T> claz) {
    String[] values = new String[] {"MIN_VALUE", "MIN"};
    T instance = null;

    for (String value : values) {
      instance = instantiateByMinValue(claz, value);

      if (instance != null) {
        break;
      }
    }

    return instance;
  }

  private static <T> T instantiateByMinValue(Class<T> claz, String value) {
    T instance;

    try {
      Field field = claz.getDeclaredField(value);
      instance = (T) field.get(null);
    } catch (NoSuchFieldException e) {
      instance = null;
    } catch (IllegalAccessException e) {
      instance = null;
    }

    return instance;
  }

  private static <T> Constructor<T> findConstructor(Class<T> claz) {
    Constructor<T> constructor = findDefaultConstructor(claz);

    if (constructor == null) {
      constructor = findSimplestConstructor(claz);
    }

    return constructor;
  }

  private static <T> Constructor<T> findDefaultConstructor(Class<T> claz) {
    Class[] parameterless = new Class[] {};
    Constructor<T> constructor;

    try {
      constructor = claz.getConstructor(parameterless);
    } catch (NoSuchMethodException e) {
      constructor = null;
    }
    return constructor;
  }

  private static <T> Constructor<T> findStringConstructor(Class<T> claz) {
    Class[] stringParameter = new Class[] {String.class};
    Constructor<T> constructor;

    try {
      constructor = claz.getConstructor(stringParameter);
    } catch (NoSuchMethodException e) {
      constructor = null;
    }
    return constructor;
  }

  private static <T> Constructor<T> findIntegerConstructor(Class<T> claz) {
    Class[] integerParameter = new Class[] {int.class};
    Constructor<T> constructor;

    try {
      constructor = claz.getConstructor(integerParameter);
    } catch (NoSuchMethodException e) {
      constructor = null;
    }
    return constructor;
  }

  private static <T> Constructor<T> findSimplestConstructor(Class<T> claz) {
    Constructor<T> constructor = null;
    Constructor[] constructors = claz.getConstructors();

    if (constructors.length == 1) {
      constructor = constructors[0];
    }

    return constructor;
  }

  private static Object invokeStatic(Method method, Object[] args) {
    Object value = null;

    try {
      value = method.invoke(null, args);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      value = null;
    }

    return value;
  }

  private static List<Method> findNamedMethods(
      Class<? extends Object> clazz, String methodName, Object[] args) {
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
          compliant = compliant && isAssignable(args[i], parameterTypes[i]);
        }
      }
    }

    return compliant;
  }

  private static boolean isAssignable(Object value, Class type) {
    type = type.isPrimitive() ? getWrapperType(type) : type;
    return type.isAssignableFrom(value.getClass());
  }

  private static Class getWrapperType(Class type) {
    Class wrapperType;

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
    } else {
      wrapperType = type;
    }

    return wrapperType;
  }

  /**
   * Recursive method used to find all classes in a given directory and subdirs.
   *
   * @param directory The base directory
   * @param packageName The package name for classes found inside the base directory
   * @return The classes
   * @throws ClassNotFoundException
   */
  private static List<Class> findClasses(File directory, String packageName)
      throws ClassNotFoundException {
    List<Class> classes = new ArrayList<>();
    if (!directory.exists()) {
      return classes;
    }
    File[] files = directory.listFiles();

    for (File file : (File[]) NullSafe.of(files)) {
      if (file.isDirectory()) {
        assert !file.getName().contains(".");
        classes.addAll(findClasses(file, packageName + "." + file.getName()));
      } else if (file.getName().endsWith(".class")) {
        classes.add(
            Class.forName(
                packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
      }
    }
    return classes;
  }
}
