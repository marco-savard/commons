package com.marcosavard.commons.util.collection;

import com.marcosavard.commons.debug.Console;

public class TreeNodeDemo {

    public static void main(String[] args) {
        TreeNode usa = TreeNode.createRoot("USA");
        TreeNode west = usa.addChild("West");
        TreeNode ca = west.addChild("CA");
        TreeNode or = west.addChild("OR");
        TreeNode wa = west.addChild("WA");

        TreeNode east = usa.addChild("East");
        TreeNode newEngland = east.addChild("newEngland");
        TreeNode ma = newEngland.addChild("MA");
        TreeNode ct = newEngland.addChild("CT");
        TreeNode ri = newEngland.addChild("RI");
        TreeNode me = newEngland.addChild("ME");
        TreeNode vt = newEngland.addChild("VT");
        TreeNode nh = newEngland.addChild("NH");

        Console.println(usa);
        Console.println(usa.isRoot());
        Console.println(usa.isLeaf());
        Console.println(usa.getLevel());
        Console.println(nh.getLevel());
    }
}
