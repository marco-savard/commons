package com.marcosavard.library.poi.word;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class WordMerger {
    private OutputStream output;
    private List<InputStream> inputs = new ArrayList<>();
    private XWPFDocument first;

    public WordMerger(OutputStream output) {
        this.output = output;
    }

    public void add(InputStream input) throws IOException, InvalidFormatException {
        inputs.add(input);
        OPCPackage srcPackage = OPCPackage.open(input);
        XWPFDocument src1Document = new XWPFDocument(srcPackage);

        if(inputs.size() == 1){
            first = src1Document;
        } else {
            CTBody srcBody = src1Document.getDocument().getBody();
            first.getDocument().addNewBody().set(srcBody);
        }
    }

    public void merge() throws IOException {
        first.write(output);
    }

    public void close() throws IOException {
        output.flush();
        output.close();
        for (InputStream input : inputs) {
            input.close();
        }

    }
}
