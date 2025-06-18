package com.marcosavard.awsmodule.postalcode;

import com.marcosavard.common.geog.GeoLocation;

public record SchoolRecord(GeoLocation location, String city, String mrc, String region, String cep) {
}
