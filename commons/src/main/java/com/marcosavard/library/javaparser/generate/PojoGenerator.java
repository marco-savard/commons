package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.io.FormatWriter;
import com.marcosavard.commons.lang.StringUtil;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PojoGenerator {
    private File destinationFolder;

    public PojoGenerator(File destinationFolder) {
        this.destinationFolder = destinationFolder;
    }

    public File generate(Class<?> claz) throws IOException {
        //create folder
        String folderName = claz.getPackageName().replace(".", "//");
        File subfolder = new File(destinationFolder, folderName);
        subfolder.mkdirs();

        //create file
        String filename = claz.getSimpleName() + ".java";
        File generated = new File(subfolder, filename);
        Writer w = new FileWriter(generated);
        FormatWriter fw = new FormatWriter(w);

        //generate code
        generateType(fw, claz);
        fw.close();

        return generated;
    }

    private void generateType(FormatWriter fw, Class<?> claz) {
        if (claz.isEnum()) {
            generateEnum(fw, claz);
        } else {
            generateClass(fw, claz);
        }
    }

    private void generateEnum(FormatWriter w, Class<?> claz) {
        w.println("package {0};", claz.getPackageName());
        w.println();

        w.println("public enum {0} '{'", claz.getSimpleName());
        w.indent();
        generateLiterals(w, claz);
        w.unindent();
        w.println("}");
    }

    private void generateLiterals(FormatWriter w, Class<?> claz) {
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields) {
            generateLiteral(w, field);
        }
    }

    private void generateLiteral(FormatWriter w, Field field) {
        boolean isPublic = Modifier.isPublic(field.getModifiers());

        if (isPublic) {
            w.println("{0},", field.getName());
        }
    }

    private void generateClass(FormatWriter w, Class<?> claz) {
        w.println("package {0};", claz.getPackageName());
        w.println();

        w.println("public class {0} '{'", claz.getSimpleName());
        w.indent();
        generateFields(w, claz);
        generateMethods(w, claz);
        w.unindent();
        w.println("}");
    }

    private void generateFields(FormatWriter w, Class<?> claz) {
        Field[] fields = claz.getDeclaredFields();
        
        for (Field field : fields) {
            generateField(w, field);
        }

        w.println();
    }

    private void generateField(FormatWriter w, Field field) {
        String modifiers = getModifiers(field);
        String type = field.getType().getSimpleName();
        String defaultValue = getDefaultValue(field);

        if (defaultValue == null) {
            w.println("{0} {1} {2};", modifiers, type, field.getName());
        } else {
            w.println("{0} {1} {2} = {3};", modifiers, type, field.getName(), defaultValue);
        }
    }

    private void generateMethods(FormatWriter w, Class<?> claz) {
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields) {
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            boolean isFinal = Modifier.isFinal(field.getModifiers());

            if (!isStatic && !isFinal) {
                generateGetter(w, field);
            }

            if (!isStatic && !isFinal) {
                generateSetter(w, field);
            }
        }

        w.println();
    }

    private void generateGetter(FormatWriter w, Field field) {
        String type = field.getType().getSimpleName();
        String methodName = "get" + StringUtil.capitalize(field.getName());
        w.println("public {0} {1}() '{'", type, methodName);
        w.println("  return {0};", field.getName());
        w.println("}");
        w.println();
    }

    private void generateSetter(FormatWriter w, Field field) {
        String type = field.getType().getSimpleName();
        String methodName = "set" + StringUtil.capitalize(field.getName());
        w.println("public void {0}({1} value) '{'", methodName, type);
        w.println("  this.{0} = value;", field.getName());
        w.println("}");
        w.println();
    }

    private String getModifiers(Field field) {
        List<String> modifiers = new ArrayList<>();
        boolean isPublic = Modifier.isPublic(field.getModifiers());
        boolean isProtected = Modifier.isProtected(field.getModifiers());
        boolean isPrivate = Modifier.isPrivate(field.getModifiers());
        boolean isStatic = Modifier.isStatic(field.getModifiers());
        boolean isFinal = Modifier.isFinal(field.getModifiers());

        if (! isStatic || !isFinal) {
            isPublic = false;
            isProtected = false;
            isPrivate = true;
        }

        if (isPublic) {
            modifiers.add("public");
        }

        if (isProtected) {
            modifiers.add("protected");
        }

        if (isPrivate) {
            modifiers.add("private");
        }

        if (isStatic) {
            modifiers.add("static");
        }

        if (isFinal) {
            modifiers.add("final");
        }


        return String.join(" ", modifiers);
    }

    private String getDefaultValue(Field field) {
        Object defaultValue = null;
        String text;

        try {
            Class<?> claz = field.getDeclaringClass();
            Constructor[] constrs = claz.getConstructors();
            Constructor constr = claz.getConstructor(new Class[] {});
            Object instance = constr.newInstance(new Object[] {});
            defaultValue = field.get(instance);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            defaultValue = null;
        }

        if (defaultValue instanceof String) {
            text = "\"" + defaultValue + "\"";
        } else {
            text = (defaultValue == null) ? null : Objects.toString(defaultValue);
        }

        return text;
    }



}
