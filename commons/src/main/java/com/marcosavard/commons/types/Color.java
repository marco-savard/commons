package com.marcosavard.commons.types;

import java.io.Serializable;

/**
 * Define a color in term of RGB (Red, Green and Blue) components. 
 * Provide method to blend colors, to calculate contrast of colors, 
 * to compute hue of a color, saturation and luminence of colors. 
 * This class is purely mathematical, thus it can be used in 
 * an environment like GWT (Google Widget Toolkit), where imports
 * of AWT or Swing library is prohibited. 

 * 
 * @author Marco
 *
 */
//A type-safe color type
//Replaces java.awt.Color, which is not supported in GWT
@SuppressWarnings("serial")
public class Color implements Serializable {
	private static final double RED_LUMINENCE = 0.299; 
	private static final double GREEN_LUMINENCE = 0.587; 
	private static final double BLUE_LUMINENCE = 0.114;
	
	//constants
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color BLACK = new Color(0, 0 ,0);
	
	private int _r, _g, _b;
	private double _alpha;
	
	//required by GWT
	private Color() {}
	
	/**
	 * Create a color based on Red, Green, Blue and Alpha channel. 
	 * 
	 * @param red in the range 0..255
	 * @param green in the range 0..255
	 * @param blue in the range 0..255
	 * @param alpha in the range 0.0 1.0 (default value 1.0)
	 */
	public Color(int red, int green, int blue, double alpha) {
		_r = red;
		_g = green; 
		_b = blue;
		_alpha = alpha;
	}
	
	public Color(int r, int g, int b) {
		this(r, g, b, 1.0); 
	}
	
	public Color(int colorCode) {
		_r = (colorCode & 0xff0000) / (256 * 256);
		_g = (colorCode & 0x00ff00) / 256; 
		_b = colorCode & 0x0000ff;
	}
	
	public Color(String rgb) {
		_r = Integer.parseInt(rgb.substring(0, 2), 16);
		_g = Integer.parseInt(rgb.substring(2, 4), 16);
		_b = Integer.parseInt(rgb.substring(4, 6), 16);
	}

	public int getRed() {
		return _r;
	}
	
	public int getGreen() {
		return _g;
	}
	
	public int getBlue() {
		return _b;
	}
	
	public int getValue() {
		int value = _r << 16 | _g << 8 | _b;
		return value;
	}
	
	@Override
	public String toString() {
		String r = Integer.toHexString(0x100 | _r).substring(1);
		String g = Integer.toHexString(0x100 | _g).substring(1);
		String b = Integer.toHexString(0x100 | _b).substring(1);
		String str = "#" + r + g + b;
		return str;
	}
	

	public String getHexCode() {
		StringBuffer buf = new StringBuffer("#"); 
		buf.append((_r <= 9) ? "0" : ""); 
		buf.append(Integer.toHexString(_r)); 
		
		buf.append((_g <= 9) ? "0" : ""); 
		buf.append(Integer.toHexString(_g)); 
		
		buf.append((_b <= 9) ? "0" : ""); 
		buf.append(Integer.toHexString(_b)); 
		
		String hex = buf.toString().toUpperCase();  
		return hex;
	}

	/**
	 * Represents a color as a RGB string, that can be used in HTML.
	 * 
	 * @return textual representation of a color
	 */
	public String toRGBString() {
		String rgbString = "rgb(" + _r + "," + _g + "," + _b + ")"; 
		return rgbString;
	}
	
	/**
	 * Represents a color as a RGBA string, that can be used in HTML.
	 * 
	 * @return textual representation of a color
	 */
	public String toRGBAString() {
		String rgbString = "rgba(" + _r + "," + _g + "," + _b + "," + _alpha + ")"; 
		return rgbString;
	}

	/**
	 * Converts a string formatted as #ff00ff to a Color instance. 
	 * 
	 * @param value such as #ff00ff
	 * @return a color instance
	 */
	public static Color fromString(String value) {
		Color color;
		
		try {
			String r = value.substring(1, 3);
			String g = value.substring(3, 5);
			String b = value.substring(5, 7);
			
			int red = Integer.parseInt(r, 16); 
			int green = Integer.parseInt(g, 16); 
			int blue = Integer.parseInt(b, 16); 
			color = new Color(red, green, blue); 
		} catch (RuntimeException ex) {
			color = null;
		}
		
		return color;
	}
	
	@Override
	public boolean equals(Object o) {
		Color that = (o instanceof Color) ? (Color)o : null;
		boolean equal = that != null;
		
		equal &= (that._r == _r);
		equal &= (that._g == _g);
		equal &= (that._b == _b);
		
		return equal;
	}
	
	@Override
	public int hashCode() {
		int code = (_r * 13) + (_g * 7) + _b;
		return code; 
	}

	/**
	 * Return the grayscale of a color.
	 * 
	 * @return a color (white, grey or black) 
	 */
	public Color getGrayscale() {
		int luminence = (int)((RED_LUMINENCE * _r) + (GREEN_LUMINENCE * _g) + (BLUE_LUMINENCE * _b));
		return new Color(luminence, luminence, luminence);
	}
	
	/**
	 * Build a color brighter from a given color 
	 * 
	 * @param factor from 1.0 to 100.0 (1.2 default)
	 * @return a brighter color
	 */
	public Color brighter(double factor) {
		float[] hsb = toHSB();
		int hue = (int)(radToDegree(hsb[0])); 
		int saturation = (int)(100 * hsb[1]); 
		int brightness = (int)(100 * hsb[2] * factor);
		brightness = (brightness > 100) ? 100 : brightness;
		
		Color brighterColor = Color.createFromHsl(hue, saturation, brightness); 
		return brighterColor; 
	}
	
	public Color brighter() {
		return brighter(1.2); 
	}

	/**
	 * Build a color darker from a given color 
	 * 
	 * @param factor from 1.0 to 100.0 (1.2 default)
	 * @return a darker color
	 */
	public Color darker(double factor) {
		Color darkerColor = brighter(1.0 / factor); 
		return darkerColor;
	}
	
	public Color darker() {
		return darker(1.2); 
	}
	
	/**
	 * Return the contrast (from 0.0 to 1.0) with that color
	 * 
	 * @param thatColor another color
	 * @return contrast from 0.0 (no contrast) and 1.0 (full contrast)
	 */
	public double constrastWith(Color thatColor) {
		double thisLuminence = (RED_LUMINENCE * _r) + (GREEN_LUMINENCE * _g) + (BLUE_LUMINENCE * _b);
		double otherLuminence = (RED_LUMINENCE * thatColor._r) + (GREEN_LUMINENCE * thatColor._g) + (BLUE_LUMINENCE *thatColor. _b);
		double contrast = Math.abs(thisLuminence - otherLuminence) / 255.0;
		return contrast;
	}
	
	/**
	 * Return black or white, depending which color gives the highest contrast. 
	 * For instance, yellow.getContrastColor() gives black, while 
	 * darkBlue.getContrastColor() gives white. 
	 * 
	 * @return Black or white 
	 * 
	 */
	public Color getContrastColor() {
		int brightness = getBrightness();
		Color contrast = (brightness > 50) ? Color.BLACK : Color.WHITE;
		return contrast;
	}

	/**
	 * Return the complimentary color. For instance getComplimentaryColor(black) 
	 * returns white. 
	 * 
	 * @return complimentary color.
	 */
	public Color getComplimentaryColor() {
		int r = 255 - _r; 
		int g = 255 - _g;
		int b = 255 - _b;
		return new Color(r, g, b);
	}
	
	/**
	 * Blend this color with that color 
	 * 
	 * @param thatColor the color to blend with 
	 * @param thatPercent the percentage of the color to blend (default value : 50)
	 * @return blended color 
	 */
	public Color blendWith(Color thatColor, int thatPercent) {
		int thisPercent = 100 - thatPercent;
		int r = (_r * thisPercent + thatColor._r * thatPercent) / 100;
		int g = (_g * thisPercent + thatColor._g * thatPercent) / 100;
		int b = (_b * thisPercent + thatColor._b * thatPercent) / 100;

		Color color = new Color(r, g, b);
		return color;
	}
	
	public Color blendWith(Color otherColor) {
		int r = (_r + otherColor._r) / 2;
		int g = (_g + otherColor._g) / 2;
		int b = (_b + otherColor._b) / 2;
		
		Color color = new Color(r, g, b);
		return color;
	}

	/**
	 * Convert RGB components to HSB (hue, saturation,  brightness)
	 * 
	 * @return array of 3 numbers, representing the hue, saturation and brightness
	 */
	public float[] toHSB() {
		double y = Math.sqrt(3.0) * (_g - _b);
		double x = (2.0 * _r) - _g - _b;
		double h = Math.atan2(y, x);
		
		int min = Math.min(Math.min(_r, _g), _b);
	    int max = Math.max(Math.max(_r, _g), _b);
	    int delta = max - min;
	    double s = (max == 0) ? 0 : delta / (double)max;
	    
	    double b = (((max+min) / 2) / 255.0);
		
		float[] hsb = new float[] {(float)h, (float)s, (float)b};
		return hsb;
	}

	/**
	 * Get the hue of a color. Hue varies from red (0.0), 
	 * green (120.0), ble (240.0) and red again (360.0).
	 * 
	 * @return a number from 0.0 (red) to 360.0 (red)
	 */
	public int getHue() {
		float[] hsb = toHSB();
		double degrees = (hsb[0] * (180/Math.PI));
		degrees = (degrees + 360.0) % 360;
		return (int)degrees;
	}

	/**
	 * Get saturation 
	 * 
	 * @return saturation
	 */
	public int getSaturation() {
		float[] hsb = toHSB();
		return (int)(hsb[1] * 100);
	}
	
	/**
	 * Get brightness
	 * 
	 * @return brightness
	 */
	public int getBrightness() {
		float[] hsb = toHSB();
		return (int)(hsb[2] * 100);
	}

	/**
	 * Convert RGB to CMYK values. C is for cyan, M for magenta,
	 * Y for yellow. 
	 * 
	 * @return array of four numbers representing CMYK values
	 */
	public float[] getCMYK() {
		double rp = _r / 255.0; 
		double gp = _g / 255.0; 
		double bp = _b / 255.0; 
	
		double k = 1.0 - Math.max(rp, Math.max(gp, bp));
		double kr = 1.0 - k;
		double c = (1.0 - rp - k) / kr;
		double m = (1.0 - gp - k) / kr;
		double y = (1.0 - bp - k) / kr;
		
		float[] cmyk = new float[] {(float)c, (float)m, (float)y, (float)k};
		return cmyk;
	}
	
	/**
	 * Create a color, from HSB instead of RGB values 
	 * 
	 * @param hue in the range [0..360]
	 * @param saturation in percentage
	 * @param lightness in percentage
	 * @return a color 
	 */
	public static Color createFromHsl(int hue, int saturation, int lightness) {
		hue = (hue >= 360) ? hue % 360 : hue;
		int h = (int)((hue/360.0) * 6);
	    double f = (hue/360.0) * 6 - h;
	    double v = lightness/100.0;
	    double p = v * (1 - (saturation/100.0));
	    double q = v * (1 - f * (saturation/100.0));
	    double t = v * (1 - (1 - f) * (saturation/100.0));
	    double r, g, b;
	    
	    switch (h) {
	      case 0: {r=v; g=t; b=p;} break;
	      case 1: {r=q; g=v; b=p;} break;
	      case 2: {r=p; g=v; b=t;} break;
	      case 3: {r=p; g=q; b=v;} break;
	      case 4: {r=t; g=p; b=v;} break;
	      case 5: {r=v; g=p; b=p;} break;
	      
	      default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + v);
	    }
	    
	    Color color = new Color((int)(r*255), (int)(g*255), (int)(b*255));
	    return color;
	}


	public Hue getHueCode() {
		int value = getHue();
		Hue hue = Hue.RED; 
		
		if (value < 15) {
			hue = Hue.RED;
		} else if (value < 45) { 
			hue =  Hue.ORANGE;
		} else if (value < 75) { 
			hue = Hue.YELLOW;
		} else if (value < 180) { 
			hue = Hue.GREEN;
		} else if (value < 200) { 
			hue = Hue.CYAN;
		} else if (value < 270) { 
			hue = Hue.BLUE;
		} else if (value < 345) { 
			hue =  Hue.PURPLE;
		} 
		return hue;
	}
	public enum Hue {RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PURPLE}; 
	
	private double radToDegree(double radian) {
		double degrees = (radian * 180) / Math.PI; 
		degrees = (degrees > 0) ? (degrees % 360) : (degrees + 360); 
		return degrees;
	}
	
	

	
	

	
	
}
