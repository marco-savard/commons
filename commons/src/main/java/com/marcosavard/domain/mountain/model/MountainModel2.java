package com.marcosavard.domain.mountain.model;

import com.marcosavard.commons.lang.reflect.meta.annotations.Description;

import java.util.Optional;

public class MountainModel2 {
    @Description("represents a mountain with a name and geographical coordinates")
    public static class Mountain {
        public final @Description("of the mountain") String name = "Unnamed";;
        public final @Description("from -90 to +90") double latitude = 0.0;
        public final @Description("from -180 to +180") double longitude = 0.0;
        public Optional<String> country;
    }
}
