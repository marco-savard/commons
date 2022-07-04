package com.marcosavard.commons.io.csv;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class PropertyCsvFormatter extends CsvFormatter {
    private Map<String, Object> propertyMap;

    public PropertyCsvFormatter(Map<String, Object> propertyMap) throws ClassNotFoundException {
        super(Class.forName((String)propertyMap.get("class")));
        this.propertyMap = propertyMap;
    }

    @Override
    public void addColumns() {
        List<String> parameters = (List<String>)propertyMap.get("column");

        for (String parameter : parameters) {
            addColumnParameter(parameter);
        }
    }

    private void addColumnParameter(String parameter) {
        String[] array = parameter.split(",");

        if (array.length == 1) {
            addColumn(normalize(array[0]));
        } else {
            addColumn(normalize(array[0]), normalize(array[1]));
        }
    }

    @Override
    public void addDecorators() {
        List<String> parameters = (List<String>)propertyMap.get("decorator");

        for (String parameter : parameters) {
            try {
                addDecoratorParameter(parameter);
            } catch (ClassNotFoundException |  NoSuchMethodException e) {
                reportException(e);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                reportException(e);
            }
        }
    }

    private void reportException(ReflectiveOperationException e) {
        //e.printStackTrace();
    }

    private void addDecoratorParameter(String parameter) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String[] array = parameter.split(",");
        String packName = PropertyCsvFormatter.class.getPackageName();
        String className = packName + ".decorator." + array[0];
        Class claz = Class.forName(className);
        Class[] parameterTypes = findParameters(array);
        Constructor constructor = claz.getConstructor(parameterTypes);

        List<Object> objectList = new ArrayList();
        for (int i=1; i<array.length-1; i++) {
            objectList.add(normalize(array[i]));
        }

        int last = array.length - 1;
        String lastParameter = normalize((String)array[last]);
        objectList.add(new String[] {lastParameter});
        Object[] parameters = objectList.toArray(new Object[0]);
        Decorator decorator = (Decorator)constructor.newInstance(parameters);
        addDecorator(decorator);
    }

    private Class[] findParameters(String[] array) {
        List<Class> parameterList = new ArrayList<>();

        for (int i=1; i<array.length-1; i++) {
            parameterList.add(String.class);
        }

        parameterList.add(String[].class);

        return parameterList.toArray(new Class[0]);
    }

    private static String normalize(String quoted) {
        String normalized = quoted.strip().replaceAll("\"", "");
        return normalized;
    }
}
