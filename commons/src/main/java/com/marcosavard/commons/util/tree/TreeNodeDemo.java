package com.marcosavard.commons.util.tree;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.TreeWriter;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.StringWriter;

public class TreeNodeDemo {

  public static void main(String[] args) {
    TreeNode canada1 = buildTreeCanada1(); // 33 LOC
    ITreeNode canada2 = buildTreeCanada2(); // 8 LOC
    ITreeNode canada3 = buildTreeCanada3(); // 8 LOC
    ITreeNode canada4 = buildTreeCanada4(); // 8 LOC

    compareTree(canada1, canada2);
    compareTree(canada2, canada3);
    compareTree(canada3, canada4);

    //printTree(canada2);
    //buildTreeWithErrors(); // error
  }

  private static void compareTree(TreeNode node1, TreeNode node2) {
    boolean equal = node2.equals(node1);
    System.out.println("  node1 : " + ITreeNode.toString(node1));
    System.out.println("  node2 : " + ITreeNode.toString(node2));
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

  private static ITreeNode buildTreeCanada2() {
    ITreeNode<String> canada = SwingTreeNode.createRoot("Canada");
    ITreeNode east = canada.addChild("East");
    ITreeNode central = canada.addChild("Central");
    ITreeNode west = canada.addChild("West");

    // add leaves
    east.addChildren("NL", "NS", "PE", "NB");
    central.addChildren("QC", "ON");
    west.addChildren("MB", "SK", "AB", "BC");

    return canada;
  }

  private static ITreeNode buildTreeCanada3() {
    ITreeNode<String> canada = TreeNodeFactory.SWING.createRoot("Canada");
    ITreeNode east = canada.addChild("East");
    ITreeNode central = canada.addChild("Central");
    ITreeNode west = canada.addChild("West");

    // add leaves
    east.addChildren("NL", "NS", "PE", "NB");
    central.addChildren("QC", "ON");
    west.addChildren("MB", "SK", "AB", "BC");

    return canada;
  }

  private static ITreeNode buildTreeCanada4() {
    ITreeNode<String> canada = TreeNodeFactory.CUSTOM.createRoot("Canada");
    ITreeNode east = canada.addChild("East");
    ITreeNode central = canada.addChild("Central");
    ITreeNode west = canada.addChild("West");

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
    System.out.println("tree : " + ITreeNode.toString(canada));

    canada.add(east); // add twice, do not complain
    System.out.println("tree : " + ITreeNode.toString(canada));

    west.add(east); // add node under child, do not complain
    System.out.println("tree : " + ITreeNode.toString(canada));

    // east.add(canada); // recursion : compiles, but fails at runtime

    return canada;
  }

  private static void printTree(TreeNode root) {
    System.out.println(root.toString() + " is leaf : " + root.isLeaf());
    System.out.println(root.toString() + " has " + root.getChildCount() + " children");
  }

  public static void mainOld(String[] args) {
    TreeNode usa = buildTreeUsa();

    Console.println(((SwingTreeNode) usa).toLongString());
    Console.println(usa.isLeaf());
    Console.println(((SwingTreeNode) usa).isRoot());
    Console.println(Integer.toString(usa.getChildCount()));
    Console.println(Integer.toString(((SwingTreeNode) usa).getLevel()));

    // print a tree structure
    StringWriter sw = new StringWriter();
    TreeWriter tw = new TreeWriter(sw);
    tw.write(usa);
    Console.println(sw.toString());
  }

  private static SwingTreeNode buildTreeUsa() {
    SwingTreeNode usa = SwingTreeNode.createRoot("USA");
    ITreeNode northeast = usa.addChild("Northeast");
    ITreeNode midwest = usa.addChild("Midwest");
    ITreeNode south = usa.addChild("South");
    ITreeNode west = usa.addChild("West");

    ITreeNode newEngland = northeast.addChild("newEngland");
    newEngland.addChildren("MA", "CT", "RI", "ME", "VT", "NH");

    ITreeNode midAtlantic = northeast.addChild("midAtlantic");
    midAtlantic.addChildren("NY", "NJ", "PE");

    ITreeNode eastNorthCentral = midwest.addChild("eastNorthCentral");
    eastNorthCentral.addChildren("IL", "IN", "MI", "OH", "WI");

    ITreeNode westNorthCentral = midwest.addChild("westNorthCentral");
    westNorthCentral.addChildren("IO", "KS", "MN", "NE", "ND", "SD");

    ITreeNode southAtlantic = south.addChild("southAtlantic");
    southAtlantic.addChildren("DE", "MD", "VI", "WV", "NC", "SC", "GE", "FL");

    ITreeNode eastSouthCentral = south.addChild("eastSouthCentral");
    eastSouthCentral.addChildren("AL", "KE", "MS", "TS");

    ITreeNode westSouthCentral = south.addChild("westSouthCentral");
    westSouthCentral.addChildren("AR", "LA", "OK", "TX");

    ITreeNode mountain = west.addChild("mountain");
    mountain.addChildren("AZ", "NM", "NV", "UT", "CO", "ID", "WY", "MT");

    ITreeNode pacific = west.addChild("pacific");
    pacific.addChildren("CA", "OR", "WA", "AK", "HW");

    return usa;
  }
}
