package com.marcosavard.commons.io;

import com.marcosavard.commons.util.collection.TreeNode;

import java.io.Writer;

public class TreeWriter extends IndentWriter {

    public TreeWriter(Writer w) {
        super(w);
    }

    public void write(TreeNode root) {
        super.println(root.toString());
        int count = root.getChildCount();

        if (count > 0) {
            super.indent();

            for (int i=0; i<count; i++) {
                TreeNode child = root.getChildAt(i);
                write(child);
            }

            super.println();
            super.unindent();
        }
    }
}
