package com.marcosavard.commons.io.csv;

import com.marcosavard.commons.lang.NullSafe;

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

        for (String parameter : NullSafe.of(parameters)) {
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

        for (String parameter : NullSafe.of(parameters)) {
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
        parameter = (String) NullSafe.of(parameter);
        String[] array = parameter.split(",");
        String packName = PropertyCsvFormatter.class.getPackageName();
        String className = packName + ".decorator." + array[0];
        Class claz = Class.forName(className);
        Constructor constructor = findConstructor(claz, array);
        Object[] arguments = findArguments(constructor, array);


        /*
        List<Object> objectList = new ArrayList();

        for (int i=1; i<array.length-1; i++) {
            objectList.sadd(normalize(array[i]));
        }

        int last = array.length - 1;
        String lastParameter = normalize((String)array[last]);
        objectList.add(new String[] {lastParameter});
        Object[] parameters = objectList.toArray(new Object[0]);


         */

        Decorator decorator = (Decorator)constructor.newInstance(arguments);
        addDecorator(decorator);
    }

    private Object[] findArguments(Constructor constructor, String[] array) {
        for (int i=1; i<array.length; i++) {
            array[i] = normalize(array[i]);
        }

        int parameterCount = constructor.getParameterCount();
        Object[] arguments = new Object[parameterCount];

        for (int i=0; i<parameterCount-1; i++) {
            arguments[i] = array[i+1];
        }

        String[] lastParameter = Arrays.copyOfRange(array, parameterCount, array.length);
        arguments[parameterCount-1] = lastParameter;

        /*
        List<Object> arguments = Arrays.asList(array);
        arguments = arguments.subList(1, arguments.size());

        if (arguments.size() > parameterCount) {
            int arraySize = arguments.size() - parameterCount;
            String[] lastParameter = Arrays.copyOfRange(array, parameterCount, arguments.size() + 1);
            arguments = arguments.subList(0, lastParameter.length - 1);
            arguments.add(lastParameter);
        }
*/
        return arguments;
    }

    private Constructor findConstructor(Class claz, String[] array) throws NoSuchMethodException {
        int argumentCount = array.length - 1;
        Constructor[] constructors = claz.getConstructors();
        List<Constructor> constructorList = Arrays.asList(constructors);
        Comparator<Constructor> constructorComparator = new ConstructorComparator();
        constructorList = constructorList.stream().sorted(constructorComparator).toList();
        Constructor foundConstructor = constructorList.get(0);

        for (Constructor constructor : constructorList) {
            if (constructor.getParameterCount() == argumentCount) {
                foundConstructor = constructor;
                break;
            }
        }

        return foundConstructor;
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

    private class ConstructorComparator implements Comparator<Constructor> {
        @Override
        public int compare(Constructor c1, Constructor c2) {
            return c1.getParameterCount() - c2.getParameterCount();
        }
    }
}