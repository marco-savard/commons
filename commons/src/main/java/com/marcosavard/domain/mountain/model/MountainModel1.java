package com.marcosavard.domain.mountain.model;

import java.util.Optional;

public class MountainModel1 {

    public static class Mountain {
        public final String name = "Unnamed";
        public final double latitude = 0.0, longitude = 0.0;
        public Optional<String> country;
    }
}
