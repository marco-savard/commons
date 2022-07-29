package com.marcosavard.commons.ling;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.TreeWriter;
import com.marcosavard.commons.util.collection.ReflectionTreeBuilder;
import com.marcosavard.commons.util.collection.TreeNode;

import java.io.StringWriter;

public class LanguageFamilyDemo {

    public static void main(String[] args) {
        String fieldName = "IndoEuropean";
        TreeNode languageFamily = ReflectionTreeBuilder.of(LanguageFamily.class, fieldName).build();
        Console.println(languageFamily.toNestedString());

        StringWriter sw = new StringWriter();
        TreeWriter tw = new TreeWriter(sw);
        tw.write(languageFamily);
        Console.println(sw.toString());


    }
}
