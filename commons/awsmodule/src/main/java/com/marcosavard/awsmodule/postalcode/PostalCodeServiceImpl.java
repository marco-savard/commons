package com.marcosavard.awsmodule.postalcode;

import com.marcosavard.common.geog.GeoLocation;
import com.marcosavard.common.geog.ca.PostalCode;
import com.marcosavard.common.geog.ca.qc.educ.res.EsCollegialReader;
import com.marcosavard.common.geog.ca.qc.educ.res.PpsPriveEtablissementReader;
import com.marcosavard.common.geog.ca.qc.educ.res.PpsPublicEcoleReader;
import com.marcosavard.common.io.reader.CsvReader;
import com.marcosavard.common.text.WordDistance;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostalCodeServiceImpl implements PostalCodeService {
    Map<String, SchoolRecord> schoolByPostalCode;

    @Override
    public String[] findPostalCode(String postalCode) throws IOException {
        validatePostalCode(postalCode);
        loadSchools();
        String schoolPostalCode = findClosestPostalCode(postalCode);
        SchoolRecord school = schoolByPostalCode.get(schoolPostalCode);

        List<String> infos = new ArrayList<>();
        infos.add("\"coordonnées\" : \"" + school.location() + "\"");
        infos.add("\"municipalité\" : \"" + school.city() + "\"");
        infos.add("\"MRC\" : \"" + school.mrc() + "\"");
        infos.add("\"région administrative\" : \"" + school.region() + "\"");
        infos.add("\"circonscription électorale\" : \"" + school.cep() + "\"");

        return infos.toArray(new String[0]);
    }

    private void validatePostalCode(String postalCode) {
    }

    private void loadSchools() throws IOException {
        if (schoolByPostalCode == null) {
            schoolByPostalCode = new HashMap<>();
            loadPublicSchools(schoolByPostalCode);
            loadPrivateSchools(schoolByPostalCode);
            loadColleges(schoolByPostalCode);
            completeMissingInfos(schoolByPostalCode);
        }
    }

    private void loadPublicSchools(Map<String, SchoolRecord> schoolByPostalCode) throws IOException {
        CsvReader reader = new PpsPublicEcoleReader();
        List<String[]> allrows = reader.readAll();

        for (String[] row : allrows) {
            String postalCode = reader.getField(row, "CD_POSTL_GDUNO_ORGNS");
            String cite = reader.getField(row, "NOM_MUNCP");
            String mrc = reader.getField(row, "NOM_MRC");
            String region = reader.getField(row, "NOM_REG_ADM");
            String cep = reader.getField(row, "NOM_CEP");
            String lat = reader.getField(row, "COORD_Y_LL84_IMM");
            String lon = reader.getField(row, "COORD_X_LL84_IMM");
            GeoLocation location = GeoLocation.of(Double.valueOf(lat), Double.valueOf(lon));

            SchoolRecord schoolRecord = new SchoolRecord(location, cite, mrc, region, cep);
            schoolByPostalCode.put(postalCode, schoolRecord);
        }
    }

    private void loadPrivateSchools(Map<String, SchoolRecord> schoolByPostalCode) throws IOException {
        CsvReader reader = new PpsPriveEtablissementReader();
        List<String[]> allrows = reader.readAll();

        for (String[] row : allrows) {
            String postalCode =  reader.getField(row, "CD_POSTL_GDUNO");
            String lat = reader.getField(row, "COORD_Y_LL84");
            String lon = reader.getField(row, "COORD_X_LL84");
            String cite = reader.getField(row, "NOM_MUNCP");
            GeoLocation location = GeoLocation.of(Double.valueOf(lat), Double.valueOf(lon));

            SchoolRecord schoolRecord = new SchoolRecord(location, cite, null, null, null);
            schoolByPostalCode.put(postalCode, schoolRecord);
        }
    }

    private void loadColleges(Map<String, SchoolRecord> schoolByPostalCode) throws IOException {
        CsvReader reader = new EsCollegialReader();
        List<String[]> allrows = reader.readAll();

        for (String[] row : allrows) {
            String postalCode =  reader.getField(row, "CD_POSTL_GDUNO");
            String lat = reader.getField(row, "COORD_Y_LL84");
            String lon = reader.getField(row, "COORD_X_LL84");
            String cite = reader.getField(row, "NOM_MUNCP");
            String mrc = reader.getField(row, "NOM_MRC");
            String region = reader.getField(row, "NOM_REG_ADM");
            String cep = reader.getField(row, "NOM_CEP");
            GeoLocation location = GeoLocation.of(Double.valueOf(lat), Double.valueOf(lon));

            SchoolRecord schoolRecord = new SchoolRecord(location, cite, mrc, region, cep);
            schoolByPostalCode.put(postalCode, schoolRecord);
        }
    }

    private void completeMissingInfos(Map<String, SchoolRecord> schoolByPostalCode) {
        List<GeoLocation> locations = new ArrayList<>();

        for (String code : schoolByPostalCode.keySet()) {
            SchoolRecord school = schoolByPostalCode.get(code);
            locations.add(school.location());
        }

        for (String code : schoolByPostalCode.keySet()) {
            SchoolRecord school = schoolByPostalCode.get(code);

            if (school.mrc() == null) {
                GeoLocation location = school.location();
                SchoolRecord closest = findClosestSchool(schoolByPostalCode, school);
                SchoolRecord newRecord = new SchoolRecord(location, school.city(), closest.mrc(), closest.region(), closest.cep());
                schoolByPostalCode.put(code, newRecord);
            }
        }
    }

    public static SchoolRecord findClosestSchool(Map<String, SchoolRecord> schoolByPostalCode, SchoolRecord givenSchool) {
        SchoolRecord closestSchool = null;
        double lowestDistance = Double.MAX_VALUE;

        for (String code : schoolByPostalCode.keySet()) {
            SchoolRecord school = schoolByPostalCode.get(code);

            if (school.mrc() != null) {
                GeoLocation location = school.location();
                double distance = location.findDistanceFrom(givenSchool.location());

                if (distance < lowestDistance) {
                    lowestDistance = distance;
                    closestSchool = school;
                }
            }
        }

        return closestSchool;
    }

    private String findClosestPostalCode(String postalCode) {
        List<String> postalCodes = new ArrayList<>();
        postalCode = PostalCode.of(postalCode).toString(); //normalize

        for (String code : schoolByPostalCode.keySet()) {
            PostalCode schoolPostalCode = PostalCode.of(code);
            postalCodes.add(schoolPostalCode.toString());
        }

        String closestPostalCode = WordDistance.findNearestString(postalCode, postalCodes);
        return closestPostalCode;
    }




}
