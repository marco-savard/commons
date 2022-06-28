package com.marcosavard.commons.io.csv;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public abstract class CsvFormatter<T> {
    private Class<T> claz;
    private List<String> columns = new ArrayList<>();
    private Map<String, String> formatByColumn = new HashMap<>();
    private Map<String, Method> methodByColumn = new HashMap<>();

    protected CsvFormatter(Class<T> claz) {
        try {
            this.claz = claz;
            addColumns();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void addColumns() throws NoSuchMethodException;

    protected void addColumn(String column) throws NoSuchMethodException {
        addColumn(column, "%s");
    }

    protected void addColumn(String column, String format) throws NoSuchMethodException {
        columns.add(column);
        formatByColumn.put(column, format);
        String getter = "get" + column;
        Method method = claz.getDeclaredMethod(getter);
        methodByColumn.put(column, method);
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
        List<String> values = new ArrayList<>();

        for (String column : columns) {
            Method method = methodByColumn.get(column);
            String format = formatByColumn.get(column);
            String raw = method.invoke(item).toString();
            String formatted = String.format(format, raw);
            values.add(formatted);
        }

        String[] row = values.toArray(new String[0]);
        return row;
    }

}
