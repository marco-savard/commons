package com.marcosavard.commons.math.algo;

import java.util.List;

/**
 * A class that computes correlation coefficients.  
 * 
 * ref http://en.wikipedia.org/wiki/Correlation_and_dependence
 * 
 * @author Marco
 *
 */
public class Correlation {

	/**
	 * Computes the correlation coefficient from a list of X-Y values. A 
	 * coefficient greater than 0.6 (representing 60%) is generally considered statistically 
	 * significant.
	 * 
	 * @param xs list of X values
	 * @param ys list of Y values
	 * @return a value between 0.0 and 1.0. 
	 */
	public static double computeCoefficient(List<Double> xs, List<Double> ys) {
		double sx = 0.0;
	    double sy = 0.0;
	    double sxx = 0.0;
	    double syy = 0.0;
	    double sxy = 0.0;

	    int n = xs.size();
	    
	    for(int i = 0; i < n; ++i) {
	        double x = xs.get(i); 
	        double y = (i < ys.size()) ? ys.get(i) : 0.0; 

	        sx += x;
	        sy += y;
	        sxx += x * x;
	        syy += y * y;
	        sxy += x * y;
	      }
	    
	    // covariation
	    double cov = sxy / n - sx * sy / n / n;
	    // standard error of x
	    double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);
	    // standard error of y
	    double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);

	    // correlation is just a normalized covariation
	    double correlation =  cov / sigmax / sigmay;

		return correlation;
	}

}
