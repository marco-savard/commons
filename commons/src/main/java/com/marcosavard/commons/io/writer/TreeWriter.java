package com.marcosavard.commons.io.writer;

import javax.swing.tree.TreeNode;
import java.io.Writer;
import java.util.List;

public class TreeWriter<T> extends IndentWriter {

  public TreeWriter(Writer w) {
    super(w);
  }

  public void write(List<T> list) {
    int count = list.size();

    if (count > 0) {
      for (int i = 0; i < count; i++) {
        T item = list.get(i);

        if (item instanceof List) {
          super.indent();
          write((List) item);
          super.unindent();
        } else {
          super.println(item.toString());
        }
      }

      super.println();
      super.flush();
    }
  }

  public void write(TreeNode root) {
    super.println(root.toString());
    int count = root.getChildCount();

    if (count > 0) {
      super.indent();

      for (int i = 0; i < count; i++) {
        TreeNode child = root.getChildAt(i);
        write(child);
      }

      super.println();
      super.unindent();
    }
  }
}
