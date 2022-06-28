package com.marcosavard.commons.io.csv;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public abstract class CsvFormatter<T> {
    private Class<T> claz;
    private List<String> columns = new ArrayList<>();

    private List<Decorator> decorators = new ArrayList<>();

    private Map<String, Method> methodByColumn = new HashMap<>();

    protected CsvFormatter(Class<T> claz) {
        this.claz = claz;
        addColumns();
        addDecorators();
    }

    public abstract void addColumns();

    public abstract void addDecorators();

    protected void addColumn(String column) {
        columns.add(column);
        Method method = findMethod(column);
        methodByColumn.put(column, method);
    }

    private Method findMethod(String column) {
        Method foundMethod = null;
        Method[] methods = claz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getParameterCount() == 0) {
                String methodName = method.getName();
                if (methodName.equals("get" + column)) {
                    foundMethod = method;
                    break;
                }

                if (methodName.equals("is" + column)) {
                    foundMethod = method;
                    break;
                }

                if (methodName.equals(column)) {
                    foundMethod = method;
                    break;
                }
            }
        }

        return foundMethod;
    }

    public void addDecorator(Decorator decorator) {
        decorators.add(decorator);
    }

    public List<String[]> format(T[] items) {
        return format(Arrays.asList(items));
    }

    public List<String[]> format(List<T> items) {
        List<String[]> rows;

        try {
            rows = formatRows(items);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return rows;
    }

    private List<String[]> formatRows(List<T> items) throws InvocationTargetException, IllegalAccessException {
        List<String[]> rows = new ArrayList<>();

        for (T item : items) {
            String[] row = formatRow(item);
            rows.add(row);
        }

        return rows;
    }

    private String[] formatRow(T item) throws InvocationTargetException, IllegalAccessException {
        List<Object> values = new ArrayList<>();
        List<String> data = new ArrayList<>();

        for (String column : columns) {
            Method method = methodByColumn.get(column);
            Object raw = method.invoke(item);
            Object decorated = decorate(column, raw);
            values.add(decorated);
        }

        for (Object value : values) {
            data.add(value.toString());
        }

        String[] row = data.toArray(new String[0]);
        return row;
    }

    private Object decorate(String column, Object value) {
        for (Decorator decorator : decorators) {
            value = decorator.decorate(column, value);
        }

        return value;
    }

    public static abstract class Decorator<T> {
        private List<String> columns;

        protected Decorator(String[] columns) {
            this.columns = Arrays.asList(columns);
        }

        public Object decorate(String column, T value) {
            Object decorated = value;

            if (columns.contains(column)) {
                decorated = decorateValue(value);
            }

            return decorated;
        }

        public abstract Object decorateValue(T value);
    }
}
