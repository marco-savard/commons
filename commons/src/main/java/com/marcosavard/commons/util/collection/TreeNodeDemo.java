package com.marcosavard.commons.util.collection;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.TreeWriter;

import java.io.StringWriter;

public class TreeNodeDemo {

    public static void main(String[] args) {
        TreeNode usa = TreeNode.createRoot("USA");
        TreeNode west = usa.addChild("West");
        west.addChild("CA");
        west.addChild("OR");
        west.addChild("WA");

        TreeNode east = usa.addChild("East");
        TreeNode newEngland = east.addChild("newEngland");
        newEngland.addChild("MA");
        newEngland.addChild("CT");
        newEngland.addChild("RI");
        newEngland.addChild("ME");
        newEngland.addChild("VT");
        TreeNode nh = newEngland.addChild("NH");

        Console.println(usa.toNestedString());
        Console.println(usa.isRoot());
        Console.println(usa.isLeaf());
        Console.println(usa.getLevel());
        Console.println(nh.getLevel());

        //print a tree structure
        StringWriter sw = new StringWriter();
        TreeWriter tw = new TreeWriter(sw);
        tw.write(usa);
        Console.println(sw.toString());
    }
}
