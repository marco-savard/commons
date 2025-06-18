package com.marcosavard.docmodule;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WordStringReplacer {
    private OutputStream output;
    private XWPFDocument document;

    public WordStringReplacer(InputStream input, OutputStream output) throws IOException {
        this.output = output;
        document = new XWPFDocument(input); //read document
    }

    public void close() throws IOException {
        document.write(output);
    }

    public void replaceStrings(String original, String replacement) {
        replaceStrings(original, replacement, null);
    }

    public void replaceStrings(String original, String replacement, String nodeName) {
        // Replace text in paragraphs
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            replaceTextInParagraph(paragraph, original, replacement, nodeName);
        }

        // Replace text in tables
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        replaceTextInParagraph(paragraph, original, replacement, nodeName);
                    }
                }
            }
        }

       // document.getDocument().getBody().g
    }

    private void replaceTextInParagraph(XWPFParagraph paragraph, String nodeName, String original, String replacement) {
        for (XWPFRun run : paragraph.getRuns()) {
            replaceTextInRun(run, nodeName, original, replacement);
        }
    }

    private void replaceTextInRun(XWPFRun run, String original, String replacement, String nodeName) {
        String text = run.getText(0);
        if (text != null && text.contains(original)) {
            text = text.replace(original, replacement);
            run.setText(text, 0);
        }

        if (nodeName != null) {
            CTR ctr = run.getCTR();
            Node node = ctr.getDomNode();
            replaceTextInNode(node, original, replacement, nodeName);
        }
    }

    private void replaceTextInNode(Node node, String original, String replacement, String nodeName) {
        NodeList children = node.getChildNodes();
        int len = children.getLength();

        for (int i=0; i<len; i++) {
            Node child = children.item(i);
            String name = child.getNodeName();

            if (nodeName.equals(name)) {
                NodeList nodes = child.getChildNodes();
                int len1 = nodes.getLength();

                for (int j=0; j<len1; j++) {
                    Node sub = nodes.item(j);
                    String text = sub.getNodeValue();

                    if (original.equals(text)) {
                        sub.setNodeValue(replacement);
                    }
                }
            }

            replaceTextInNode(child, original, replacement, nodeName);
        }
    }

    public XWPFDocument getDocument() {
        return document;
    }
}
