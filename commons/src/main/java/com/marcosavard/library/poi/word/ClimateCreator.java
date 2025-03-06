package com.marcosavard.library.poi.word;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.ca.climat.NormalReader;
import com.marcosavard.library.poi.word.template.ClimateTemplateReader;
import com.microsoft.schemas.office.word.STWrapType;
import com.microsoft.schemas.vml.CTGroup;
import com.microsoft.schemas.vml.CTRect;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.w3c.dom.Node;

import java.io.*;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ClimateCreator {
    private final ClimateTemplateReader templateReader = new ClimateTemplateReader();
    private final NormalReader normalReader = new NormalReader();
    private final LocalDate date;
    private final Locale display;

    public ClimateCreator(LocalDate date, Locale display) {
        this.date = date;
        this.display = display;
    }

    public void create(File outputFile) {
        try (InputStream input = templateReader.getInput()) {
            try (OutputStream output = new FileOutputStream(outputFile)) {
                WordStringReplacer replacer = new WordStringReplacer(input, output);

                printCurrentDate(replacer);
                printNormalsInTable(replacer, normalReader);
                printNormalsOnMap(replacer, normalReader);
                printExtremes(replacer, normalReader);

                replacer.close();
                Console.println("String replacement completed. Output file: {0}", outputFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printCurrentDate(WordStringReplacer replacer) {
        //format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'du' eeee d MMMM yyyy", display);
        replacer.replaceStrings("currentDate", date.format(formatter));
    }

    private void printNormalsInTable(WordStringReplacer replacer, NormalReader normalReader) throws IOException {
        replacer.replaceStrings("minmad", toMinText(normalReader, NormalReader.Station.ILES_MAD, date.getMonth()));
        replacer.replaceStrings("maxmad", toMaxText(normalReader, NormalReader.Station.ILES_MAD, date.getMonth()));
        replacer.replaceStrings("minsi", toMinText(normalReader, NormalReader.Station.SEPT_ILES, date.getMonth()));
        replacer.replaceStrings("maxsi", toMaxText(normalReader, NormalReader.Station.SEPT_ILES, date.getMonth()));
        replacer.replaceStrings("minbc", toMinText(normalReader, NormalReader.Station.BAIE_COMEAU, date.getMonth()));
        replacer.replaceStrings("maxbc", toMaxText(normalReader, NormalReader.Station.BAIE_COMEAU, date.getMonth()));

        replacer.replaceStrings("mingas", toMinText(normalReader, NormalReader.Station.GASPE, date.getMonth()));
        replacer.replaceStrings("maxgas", toMaxText(normalReader, NormalReader.Station.GASPE, date.getMonth()));
        replacer.replaceStrings("minrim", toMinText(normalReader, NormalReader.Station.RIMOUSKI, date.getMonth()));
        replacer.replaceStrings("maxrim", toMaxText(normalReader, NormalReader.Station.RIMOUSKI, date.getMonth()));
        replacer.replaceStrings("minsag", toMinText(normalReader, NormalReader.Station.BAGOTVILLE, date.getMonth()));
        replacer.replaceStrings("maxsag", toMaxText(normalReader, NormalReader.Station.BAGOTVILLE, date.getMonth()));
        replacer.replaceStrings("minrob", toMinText(normalReader, NormalReader.Station.ROBERVAL, date.getMonth()));
        replacer.replaceStrings("maxrob", toMaxText(normalReader, NormalReader.Station.ROBERVAL, date.getMonth()));

        replacer.replaceStrings("minqc", toMinText(normalReader, NormalReader.Station.STE_FOY, date.getMonth()));
        replacer.replaceStrings("maxqc", toMaxText(normalReader, NormalReader.Station.STE_FOY, date.getMonth()));
        replacer.replaceStrings("minstg", toMinText(normalReader, NormalReader.Station.ST_GEORGES, date.getMonth()));
        replacer.replaceStrings("maxstg", toMaxText(normalReader, NormalReader.Station.ST_GEORGES, date.getMonth()));
        replacer.replaceStrings("mintr", toMinText(normalReader, NormalReader.Station.TROIS_RIVIERES, date.getMonth()));
        replacer.replaceStrings("maxtr", toMaxText(normalReader, NormalReader.Station.TROIS_RIVIERES, date.getMonth()));
        replacer.replaceStrings("mindru", toMinText(normalReader, NormalReader.Station.DRUMMONDVILLE, date.getMonth()));
        replacer.replaceStrings("maxdru", toMaxText(normalReader, NormalReader.Station.DRUMMONDVILLE, date.getMonth()));
        replacer.replaceStrings("minshb", toMinText(normalReader, NormalReader.Station.SHERBROOKE, date.getMonth()));
        replacer.replaceStrings("maxshb", toMaxText(normalReader, NormalReader.Station.SHERBROOKE, date.getMonth()));

        replacer.replaceStrings("minmtl", toMinText(normalReader, NormalReader.Station.MTL, date.getMonth()));
        replacer.replaceStrings("maxmtl", toMaxText(normalReader, NormalReader.Station.MTL, date.getMonth()));
        replacer.replaceStrings("mingat", toMinText(normalReader, NormalReader.Station.OTTAWA, date.getMonth()));
        replacer.replaceStrings("maxgat", toMaxText(normalReader, NormalReader.Station.OTTAWA, date.getMonth()));
        replacer.replaceStrings("minvld", toMinText(normalReader, NormalReader.Station.VAL_DOR, date.getMonth()));
        replacer.replaceStrings("maxvld", toMaxText(normalReader, NormalReader.Station.VAL_DOR, date.getMonth()));
        replacer.replaceStrings("minrou", toMinText(normalReader, NormalReader.Station.ROUYN, date.getMonth()));
        replacer.replaceStrings("maxrou", toMaxText(normalReader, NormalReader.Station.ROUYN, date.getMonth()));
    }

    private void printNormalsOnMap(WordStringReplacer replacer, NormalReader normalReader) {
        XWPFDocument document = replacer.getDocument();
        WordWalker wordWalker = new WordWalker(document);
        WordWalker.PictureListener listener = new NormalOnMapPictureProcessor(document, normalReader, date);
        wordWalker.addPictureListener(listener);
        wordWalker.walk();
    }

    private void printExtremes(WordStringReplacer replacer, NormalReader normalReader) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d MMMM yyyy", display);
        NumberFormat nf = NumberFormat.getInstance(display);
        NormalReader.NormalEvent min = normalReader.findExtremeMinimums(date, 1).get(0);
        NormalReader.NormalEvent max = normalReader.findExtremeMaximums(date, 1).get(0);

        String pat = "Le {0}, il a fait {1} °C à {2}.";
        String historicMin = MessageFormat.format(pat, toDate(min, fmt), toString(min.getValue(), nf), min.getStationName());
        String historicMax = MessageFormat.format(pat, toDate(max, fmt), toString(max.getValue(), nf), max.getStationName());

        replacer.replaceStrings("historicMin", historicMin);
        replacer.replaceStrings("historicMax", historicMax);
    }

    private static String toDate(NormalReader.NormalEvent min, DateTimeFormatter formatter) {
        return min.getDate().format(formatter);
    }

    private static String toString(double value, NumberFormat nf) {
        return nf.format(value);
    }

    private String toMinText(NormalReader normalReader, NormalReader.Station station, Month month) throws IOException {
        return Double.toString(normalReader.getDailyMinimum(station, month));
    }

    private String toMaxText(NormalReader normalReader, NormalReader.Station station, Month month) throws IOException {
        return Double.toString(normalReader.getDailyMaximum(station, month));
    }

    private String toText(NormalReader normalReader, NormalReader.Station station, Month month) throws IOException {
        double min = normalReader.getDailyMinimum(station, month);
        double max = normalReader.getDailyMaximum(station, month);
        return Double.toString(min) + ", " + Double.toString(max);
    }

    private static class NormalOnMapPictureProcessor extends WordWalker.PictureListener {
        private XWPFDocument document;
        private NormalReader normalReader;
        private LocalDate date;

        public NormalOnMapPictureProcessor(XWPFDocument document, NormalReader normalReader, LocalDate date) {
            this.document = document;
            this.normalReader = normalReader;
            this.date = date;
        }

        @Override
        public void onPicture(XWPFRun run, XWPFPicture picture) {
            addNormalOnPicture(run, NormalReader.Station.RIMOUSKI, 250, 60, 0);
            addNormalOnPicture(run, NormalReader.Station.BAGOTVILLE, 170, 15, 1);
            addNormalOnPicture(run, NormalReader.Station.STE_FOY, 125, 85, 2);
            addNormalOnPicture(run, NormalReader.Station.MTL, 90, 185, 3);
            addNormalOnPicture(run, NormalReader.Station.OTTAWA, 10, 140, 4);
        }

        private void addNormalOnPicture(XWPFRun run, NormalReader.Station station, int x, int y, int idx) {
            String displayName = station.getDisplayName();
            String normals = toText(normalReader, station, date.getMonth());

            CTGroup ctGroup = CTGroup.Factory.newInstance();
            CTRect ctRect = ctGroup.addNewRect();
            ctRect.addNewWrap().setType(STWrapType.SQUARE);
            ctRect.setFillcolor("#94bf8a");

            String style = MessageFormat.format("position:absolute;width:60pt;height:36pt;z-index:100;margin-left:{0}pt;margin-top:{1}pt;", x, y);
            ctRect.setStyle(style);

            CTTxbxContent ctTxbxContent = ctRect.addNewTextbox().addNewTxbxContent();
            CTP ctp = ctTxbxContent.addNewP();
            CTR ctr = ctp.addNewR();
            CTText text1 = ctr.addNewT();
            text1.setStringValue(displayName);
            ctr.addNewBr();

            CTR ctr1 = ctp.addNewR();
            CTText text2 = ctr1.addNewT();
            text2.setStringValue(normals);

            CTR runCtr = run.getCTR();
            runCtr.addNewPict();
            Node ctGroupNode = ctGroup.getDomNode();

            try {
                CTPicture ctPicture = CTPicture.Factory.parse(ctGroupNode);
                runCtr.setPictArray(idx, ctPicture);
            } catch (XmlException e) {
                throw new RuntimeException(e);
            }

            //  picture.
            Console.println("Picture Found!");
        }

        private String toText(NormalReader normalReader, NormalReader.Station station, Month month) {
            try {
                double min = normalReader.getDailyMinimum(station, month);
                double max = normalReader.getDailyMaximum(station, month);
                return Double.toString(min) + ", " + Double.toString(max);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
