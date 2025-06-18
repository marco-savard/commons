package com.marcosavard.common.geog.ca.qc.educ.res;

import com.marcosavard.common.io.reader.CsvReader;

import java.io.IOException;
import java.util.List;

public class PpsPublicEcoleReaderDemo {

    public static void main(String[] args) throws IOException {
       //readPublicEcole();
     //   readPriveEtablissement();
        readEsCollegial();
    }

    private static void readEsCollegial() throws IOException {
        CsvReader reader = new EsCollegialReader();
        List<String[]> allrows = reader.readAll();

        for (String[] row : allrows) {
            String postalCode =  reader.getField(row, "CD_POSTL_GDUNO");
            String lat = reader.getField(row, "COORD_X_LL84");
            String lon = reader.getField(row, "COORD_Y_LL84");
            String cite = reader.getField(row, "NOM_MUNCP");
            String mrc = reader.getField(row, "NOM_MRC");
            String region = reader.getField(row, "NOM_REG_ADM");
            String cep = reader.getField(row, "NOM_CEP");

            String[] info = new String[] {postalCode, lat, lon, cite, mrc, region, cep};
            String line = String.join(", ", info);
            System.out.println(line);
        }
    }

    private static void readPriveEtablissement() throws IOException {
        CsvReader reader = new PpsPriveEtablissementReader();
        List<String[]> allrows = reader.readAll();

        for (String[] row : allrows) {
            int len = row.length;
            String postalCode =  reader.getField(row, "CD_POSTL_GDUNO");
            String lat = reader.getField(row, "COORD_X_LL84");
            String lon = reader.getField(row, "COORD_Y_LL84");
            String cite = reader.getField(row, "NOM_MUNCP");

            String[] info = new String[] {postalCode, lat, lon, cite};
            String line = String.join(", ", info);
            System.out.println(line);
        }
    }

    private static void readPublicEcole() throws IOException {
        CsvReader reader = new PpsPublicEcoleReader();
        List<String[]> allrows = reader.readAll();

        for (String[] row : allrows) {
            int len = row.length;
            String postalCode = reader.getField(row, "CD_POSTL_GDUNO_ORGNS");
            String cite = reader.getField(row, "NOM_MUNCP");
            String mrc = reader.getField(row, "NOM_MRC");
            String region = reader.getField(row, "NOM_REG_ADM");
            String cep = reader.getField(row, "NOM_CEP");
            String lat = reader.getField(row, "COORD_X_LL84_IMM");
            String lon = reader.getField(row, "COORD_Y_LL84_IMM");
            String[] info = new String[] {postalCode, lon, lat, cite, mrc, region, cep};
            String line = String.join(", ", info);
            System.out.println(line);
        }
    }
}
