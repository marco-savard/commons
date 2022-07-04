package com.marcosavard.commons.io.csv;

import com.marcosavard.commons.io.csv.decorator.StringFormatter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public abstract class CsvFormatter<T> {
    private Class<T> claz;
    private List<String> columns = null;

    private List<Decorator> decorators = null;

    private Map<String, Method> methodByColumn = new HashMap<>();

    private Map<String, String> titleByColumn = new HashMap<>();

    protected CsvFormatter(Class<T> claz) {
        this.claz = claz;
    }

    public abstract void addColumns();

    public abstract void addDecorators();

    protected void addColumn(String column) {
        addColumn(column, column);
    }

    protected void addColumn(String column, String title) {
        columns.add(column);
        Method method = findMethod(column);
        methodByColumn.put(column, method);
        titleByColumn.put(column, title);
    }

    private Method findMethod(String column) {
        Method foundMethod = null;
        Method[] methods = (claz == null) ? new Method[] {} : claz.getDeclaredMethods();
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
        init();
        return format(Arrays.asList(items));
    }

    private void init() {
        if (columns == null) {
            columns = new ArrayList<>();
            addColumns();
        }

        if (decorators == null) {
            decorators = new ArrayList<>();
            addDecorators();
        }
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
        String[] row = formatHeader();
        rows.add(row);

        for (T item : items) {
            row = formatRow(item);
            rows.add(row);
        }

        return rows;
    }

    private String[] formatHeader() {
        List<String> data = new ArrayList<>();

        for (String column : columns) {
            String title = titleByColumn.get(column);
            title = decorateTitle(column, title);
            data.add(title);
        }

        String[] row = data.toArray(new String[0]);
        return row;
    }

    private String decorateTitle(String column, String title) {

        for (Decorator decorator : decorators) {
            if (decorator instanceof StringFormatter) {
                title = (String)decorator.decorate(column, title);
            }
        }

        return title;
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
