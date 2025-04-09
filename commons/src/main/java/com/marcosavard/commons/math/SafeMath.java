package com.marcosavard.commons.math;

/**
 * A utility class that defines safe methods missing in java.lang.Math
 *
 * @author Marco
 */
public class SafeMath {
    private static final double DOUBLE_TRIM_VALUE = 0.000_000_000_000_1;
    private static final double DOUBLE_PRECISION = 0.000_0001;
    private static final double FLOAT_PRECISION = 0.001;

    public static double acosd(double value) {
        return Math.toDegrees(Math.acos(value));
    }

    public static double asind(double value) {
        return Math.toDegrees(Math.asin(value));
    }

    public static double atand(double value) {
        return Math.toDegrees(Math.atan(value));
    }

    public static double atan2d(double y, double x) {
        return Math.toDegrees(Math.atan2(y, x));
    }

    public static double cosd(double degree) {
        return trim(Math.cos(Math.toRadians(degree)));
    }

    // cotangant
    public static double cotand(double degree) {
        return 1 / tand(degree);
    }

    // cosecant
    public static double cosecd(double degree) {
        return 1 / sind(degree);
    }

    //TODO safe modulo?

    /**
     * Tells if double numbers are equal. It is not a good practice to compare double directly, use if
     * (Maths.equal(d1, d2) instead of if (d1 == d2).
     *
     * @param d1 the 1st number
     * @param d2 the 2nd number
     * @param difference (default value EPSILON)
     * @return true if equal
     */
    public static boolean equal(double d1, double d2, double difference) {
        return Math.abs(d1 - d2) < difference;
    }

    public static boolean equal(double d1, double d2) {
        return equal(d1, d2, DOUBLE_PRECISION);
    }

    public static boolean equal(float d1, float d2, float difference) {
        return Math.abs(d1 - d2) < difference;
    }

    public static boolean equal(float d1, float d2) {
        return equal(d1, d2, FLOAT_PRECISION);
    }

    // haversine function
    public static double haversined(double degree) {
        return (1 - cosd(degree)) / 2;
    }


    /**
     * Keep in range [min..max]. Return value within the range [0..max]. For instance : range(90, 360)
     * gives 90 range(361, 360) gives 1 range(-90, 360) gives 270
     *
     * @param value a given value
     * @param min the lower bound of the range
     * @param max the upper bound of the range
     * @return the ranged value
     */
    public static double range(double value, double min, double max) {
        double span = max - min;
        double ranged = min + ((value - min) % span + span) % span;
        return ranged;
    }

    public static double range360(double value) {
        return range(value, 0, 360);
    }

    // secant
    public static double secd(double degree) {
        return 1 / cosd(degree);
    }

    public static double sind(double degree) {
        return trim(Math.sin(Math.toRadians(degree)));
    }

    public static double tand(double degree) {
        return trim(Math.tan(Math.toRadians(degree)));
    }

    //safely convert to short
    public static short toShort(double value) {
        short safeValue = (short) Math.min(value, Short.MAX_VALUE);
        safeValue = (short) Math.max(safeValue, Short.MIN_VALUE);
        return safeValue;
    }

    public static double[] toSpherical(double x, double y, double z) {
        double r = trim(Math.sqrt(x * x + y * y + z * z));
        double theta = trim(atan2d(y, x)); // Convert azimuthal angle to degrees
        double phi = trim(acosd(z / r)); // Convert polar angle to degrees
        return new double[] {r, theta, phi}; // Returns an array {r, theta, phi}
    }

    public static double[] toRectangular(double r, double theta, double phi) {
        // Compute Cartesian coordinates
        double x = trim(r * sind(phi) * cosd(theta));
        double y = trim(r * sind(phi) * sind(theta));
        double z = trim(r * cosd(phi));

        return new double[]{x, y, z}; // Returns an array {x, y, z}
    }


    public static double[] toRectangular(double[] spherical) {
        return toRectangular(spherical[0], spherical[1], spherical[2]);
    }

    /**
     * Round the full precision value at a given precision. For instance, round(Math.PI, 0.01) gives
     * 3.14
     *
     * @param original value
     * @param precision (0.01 to round at the nearest 0.01 value)
     * @return rounded values
     */
    public static double trim(double original, double precision) {
        double rounded = Math.round(original / precision) * precision;
        return rounded;
    }

    private static final double TRIM_FACTOR = 1_000_000.0;

    public static double trim(double original) {
        double rounded = Math.round(original * TRIM_FACTOR);
        double faction = Math.abs(rounded - original * TRIM_FACTOR);
        double trimmed = (faction < DOUBLE_PRECISION) ? (rounded / TRIM_FACTOR) : original;
        return trimmed;
    }

}
