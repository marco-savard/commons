package com.marcosavard.commons.util.collection;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.TreeWriter;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.StringWriter;

public class TreeNodeDemo {

  public static void main(String[] args) {
    TreeNode canada1 = buildTreeCanada1(); // 33 LOC
    TreeNode canada2 = buildTreeCanada2(); // 8 LOC

    compareTree(canada1, canada2);
    printTree(canada2);
    buildTreeWithErrors(); // error
  }

  private static void compareTree(TreeNode node1, TreeNode node2) {
    boolean equal = node2.equals(node1);
    System.out.println("node1 : " + SimpleTreeNode.toString(node1));
    System.out.println("node2 : " + SimpleTreeNode.toString(node2));
    System.out.println("Trees are equal : " + equal);
  }

  private static TreeNode buildTreeCanada1() {
    DefaultMutableTreeNode canada = new DefaultMutableTreeNode("Canada");

    DefaultMutableTreeNode east = new DefaultMutableTreeNode("East");
    DefaultMutableTreeNode central = new DefaultMutableTreeNode("Central");
    DefaultMutableTreeNode west = new DefaultMutableTreeNode("West");
    canada.add(east);
    canada.add(central);
    canada.add(west);

    MutableTreeNode nl = new DefaultMutableTreeNode("NL");
    MutableTreeNode ns = new DefaultMutableTreeNode("NS");
    MutableTreeNode pe = new DefaultMutableTreeNode("PE");
    MutableTreeNode nb = new DefaultMutableTreeNode("NB");
    east.add(nl);
    east.add(ns);
    east.add(pe);
    east.add(nb);

    MutableTreeNode qc = new DefaultMutableTreeNode("QC");
    MutableTreeNode on = new DefaultMutableTreeNode("ON");
    central.add(qc);
    central.add(on);

    MutableTreeNode mb = new DefaultMutableTreeNode("MB");
    MutableTreeNode sk = new DefaultMutableTreeNode("SK");
    MutableTreeNode ab = new DefaultMutableTreeNode("AB");
    MutableTreeNode bc = new DefaultMutableTreeNode("BC");
    west.add(mb);
    west.add(sk);
    west.add(ab);
    west.add(bc);

    return canada;
  }

  private static TreeNode buildTreeCanada2() {
    SimpleTreeNode<String> canada = SimpleTreeNode.createRoot("Canada");
    SimpleTreeNode east = canada.addChild("East");
    SimpleTreeNode central = canada.addChild("Central");
    SimpleTreeNode west = canada.addChild("West");

    // add leaves
    east.addChildren("NL", "NS", "PE", "NB");
    central.addChildren("QC", "ON");
    west.addChildren("MB", "SK", "AB", "BC");

    return canada;
  }

  private static TreeNode buildTreeWithErrors() {
    DefaultMutableTreeNode canada = new DefaultMutableTreeNode("Canada");

    DefaultMutableTreeNode east = new DefaultMutableTreeNode("East");
    DefaultMutableTreeNode central = new DefaultMutableTreeNode("Central");
    DefaultMutableTreeNode west = new DefaultMutableTreeNode("West");
    DefaultMutableTreeNode error = new DefaultMutableTreeNode(0.0); // mixed types
    canada.add(east);
    canada.add(central);
    canada.add(west);
    System.out.println("tree : " + SimpleTreeNode.toString(canada));

    canada.add(east); // add twice, do not complain
    System.out.println("tree : " + SimpleTreeNode.toString(canada));

    west.add(east); // add node under child, do not complain
    System.out.println("tree : " + SimpleTreeNode.toString(canada));

    // east.add(canada); // recursion : compiles, but fails at runtime

    return canada;
  }

  private static void printTree(TreeNode root) {
    System.out.println(root.toString() + " is leaf : " + root.isLeaf());
    System.out.println(root.toString() + " has " + root.getChildCount() + " children");
  }

  public static void mainOld(String[] args) {
    TreeNode usa = buildTreeUsa();

    Console.println(((SimpleTreeNode) usa).toLongString());
    Console.println(usa.isLeaf());
    Console.println(((SimpleTreeNode) usa).isRoot());
    Console.println(Integer.toString(usa.getChildCount()));
    Console.println(Integer.toString(((SimpleTreeNode) usa).getLevel()));

    // print a tree structure
    StringWriter sw = new StringWriter();
    TreeWriter tw = new TreeWriter(sw);
    tw.write(usa);
    Console.println(sw.toString());
  }

  private static SimpleTreeNode buildTreeUsa() {
    SimpleTreeNode usa = SimpleTreeNode.createRoot("USA");
    SimpleTreeNode northeast = usa.addChild("Northeast");
    SimpleTreeNode midwest = usa.addChild("Midwest");
    SimpleTreeNode south = usa.addChild("South");
    SimpleTreeNode west = usa.addChild("West");

    SimpleTreeNode newEngland = northeast.addChild("newEngland");
    newEngland.addChildren("MA", "CT", "RI", "ME", "VT", "NH");

    SimpleTreeNode midAtlantic = northeast.addChild("midAtlantic");
    midAtlantic.addChildren("NY", "NJ", "PE");

    SimpleTreeNode eastNorthCentral = midwest.addChild("eastNorthCentral");
    eastNorthCentral.addChildren("IL", "IN", "MI", "OH", "WI");

    SimpleTreeNode westNorthCentral = midwest.addChild("westNorthCentral");
    westNorthCentral.addChildren("IO", "KS", "MN", "NE", "ND", "SD");

    SimpleTreeNode southAtlantic = south.addChild("southAtlantic");
    southAtlantic.addChildren("DE", "MD", "VI", "WV", "NC", "SC", "GE", "FL");

    SimpleTreeNode eastSouthCentral = south.addChild("eastSouthCentral");
    eastSouthCentral.addChildren("AL", "KE", "MS", "TS");

    SimpleTreeNode westSouthCentral = south.addChild("westSouthCentral");
    westSouthCentral.addChildren("AR", "LA", "OK", "TX");

    SimpleTreeNode mountain = west.addChild("mountain");
    mountain.addChildren("AZ", "NM", "NV", "UT", "CO", "ID", "WY", "MT");

    SimpleTreeNode pacific = west.addChild("pacific");
    pacific.addChildren("CA", "OR", "WA", "AK", "HW");

    return usa;
  }
}
