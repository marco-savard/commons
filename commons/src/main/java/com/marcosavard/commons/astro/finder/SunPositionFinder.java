package com.marcosavard.commons.astro.finder;

public class SunPositionFinder {
  /*
  public static SpaceCoordinate findLocation(ZonedDateTime moment) {
    //compute ecliptic coordinate (https://en.wikipedia.org/wiki/Position_of_the_Sun)
    JulianDay jd =  JulianDay.of(moment);
    double n = jd.getValue() -2451545.0;
    double sunLon = range(280.460 + 0.9856474 * n, 360);
    double ma = range(357.528 + 0.9856003 * n, 360);

    double eclLon = sunLon + (1.915 * sind(ma)) + (0.02 * sind(2 * ma));
    double ecl = AstroMath.ECLIPTIC;
    double y = cosd(ecl) * sind(eclLon);
    double x = cosd(eclLon);
    double ra = range(atan2d(y, x), 0, 360);
    double dec = asind(sind(ecl) * sind(eclLon));

    SpaceCoordinate location = SpaceCoordinate.of(ra/15, dec);
    return location;
  }*/

  /*
  //less accurate
  private static SpaceCoordinate findLocationOld(ZonedDateTime moment) {
  	//day of year
  	LocalDate date = moment.toLocalDate();
  	int dayOfYear = date.getDayOfYear();

  	//convert to UT
  	ZonedDateTime momentUt = TimeConverter.toZonedDateTime(moment, ZoneOffset.UTC);
  	double hourUT = momentUt.toLocalTime().toSecondOfDay() / 3600.0;
  	System.out.println("hr : " + hourUT);

  	int n = (int)range(dayOfYear - 79, 366);
  	boolean springSummer = (n < 107);
  	double decl, ra;

  	if (springSummer) {
  		decl = 23.44 * sind(n * 360.0 / 372.0);
  		ra = 24 * (n / 372);
  	} else {
  		decl = 23.44 * sind(n * 360.0 / 359.0);
  		ra = 12 + 24 * ((n-186) / 358.0);
  	}

  	SpaceCoordinate location = SpaceCoordinate.of(ra, decl);
  	return location;
  }
  */

  /*
  	public static SkyPosition findPosition(ZonedDateTime moment, double[] coordinates) {
  		//compute momentUt
  		ZonedDateTime momentUt = TimeConverter.toZonedDateTime(moment, ZoneOffset.UTC);

  		//compute location
  		SpaceCoordinate location = findLocation(moment);

  		//compute position
  		SkyPosition position = SkyPositionFinder.findPosition(location, momentUt, coordinates);

  		/*
  		 * this section does not work
  		 * 		//compute n, hourUT
  		double hourUT = momentUt.toLocalTime().toSecondOfDay() / 3600.0;
  		LocalDate date = moment.toLocalDate();
  		int dayOfYear = date.getDayOfYear();
  		int n = (int)range(dayOfYear - 79, 366);

  				//get lat, lon
  		double lat = coordinates[0];
  		double lon = coordinates[1];

  		double ra = location.getRightAscensionHour();
  		double decl = location.getDeclination();

  		//compute ah
  		double k = 6.6383;
  		double tsl = k + (0.0657 * n) + (1.002734 * hourUT) + (lon / 15);
  		double ah = tsl - ra;

  		//compute h
  		double sinH = (sind(lat) * sind(decl)) + (cosd(lat) * cosd(decl) * cosd(ah * 15));
  		double h = asind(sinH);

  		//compute az
  		double cosAz = (sind(decl) - sind(lat) * sinH) / (cosd(lat) * cosd(h));
  		double az = acosd(cosAz);

  		System.out.println("h : " + h);
  		System.out.println("az : " + az);
  		SkyPosition position = SkyPosition.of(h, az);
  		System.out.println("position : " + position);


  		return position;
  	}

  */

}
