package com.marcosavard.commons.util.collection;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.TreeWriter;

import javax.swing.tree.TreeNode;
import java.io.StringWriter;

public class TreeNodeDemo {

    public static void main(String[] args) {
        TreeNode usa = buildTreeUsa();

        Console.println(((TreeNodeImpl)usa).toLongString());
        Console.println(usa.isLeaf());
        Console.println(((TreeNodeImpl)usa).isRoot());
        Console.println(Integer.toString(usa.getChildCount()));
        Console.println(Integer.toString(((TreeNodeImpl)usa).getLevel()));

        //print a tree structure
        StringWriter sw = new StringWriter();
        TreeWriter tw = new TreeWriter(sw);
        tw.write(usa);
        Console.println(sw.toString());
    }

    private static TreeNodeImpl buildTreeUsa() {
        TreeNodeImpl usa = TreeNodeImpl.createRoot("USA");
        TreeNodeImpl northeast = usa.addChild("Northeast");
        TreeNodeImpl midwest = usa.addChild("Midwest");
        TreeNodeImpl south = usa.addChild("South");
        TreeNodeImpl west = usa.addChild("West");

        TreeNodeImpl newEngland = northeast.addChild("newEngland");
        newEngland.addChild("MA");
        newEngland.addChild("CT");
        newEngland.addChild("RI");
        newEngland.addChild("ME");
        newEngland.addChild("VT");
        newEngland.addChild("NH");

        TreeNodeImpl midAtlantic = northeast.addChild("midAtlantic");
        midAtlantic.addChild("NY");
        midAtlantic.addChild("NJ");
        midAtlantic.addChild("PE");

        TreeNodeImpl eastNorthCentral = midwest.addChild("eastNorthCentral");
        eastNorthCentral.addChild("IL");
        eastNorthCentral.addChild("IN");
        eastNorthCentral.addChild("MI");
        eastNorthCentral.addChild("OH");
        eastNorthCentral.addChild("WI");

        TreeNodeImpl westNorthCentral = midwest.addChild("westNorthCentral");
        westNorthCentral.addChild("IO");
        westNorthCentral.addChild("KS");
        westNorthCentral.addChild("MN");
        westNorthCentral.addChild("MS");
        westNorthCentral.addChild("NE");
        westNorthCentral.addChild("ND");
        westNorthCentral.addChild("SD");

        TreeNodeImpl southAtlantic = south.addChild("southAtlantic");
        southAtlantic.addChild("DE");
        southAtlantic.addChild("MD");
        southAtlantic.addChild("VI");
        southAtlantic.addChild("WV");
        southAtlantic.addChild("NC");
        southAtlantic.addChild("SC");
        southAtlantic.addChild("GE");
        southAtlantic.addChild("FL");

        TreeNodeImpl eastSouthCentral = south.addChild("eastSouthCentral");
        eastSouthCentral.addChild("AL");
        eastSouthCentral.addChild("KE");
        eastSouthCentral.addChild("MS");
        eastSouthCentral.addChild("TE");

        TreeNodeImpl westSouthCentral = south.addChild("westSouthCentral");
        westSouthCentral.addChild("AR");
        westSouthCentral.addChild("LA");
        westSouthCentral.addChild("OK");
        westSouthCentral.addChild("TX");

        TreeNodeImpl mountain = west.addChild("mountain");
        mountain.addChild("AZ");
        mountain.addChild("NM");
        mountain.addChild("NV");
        mountain.addChild("UT");
        mountain.addChild("CO");
        mountain.addChild("ID");
        mountain.addChild("WY");
        mountain.addChild("MT");

        TreeNodeImpl pacific = west.addChild("pacific");
        pacific.addChild("CA");
        pacific.addChild("OR");
        pacific.addChild("WA");
        pacific.addChild("AL");
        pacific.addChild("HW");

        return usa;
    }
}
