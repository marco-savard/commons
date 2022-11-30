package com.marcosavard.domain.mountain.model;

import java.util.Optional;

public class MountainModel1 {

    public static class Mountain {
        public final String name = "Unnamed";
        public double latitude, longitude;
        public double altitude;
        public Optional<String> country;
    }
}
