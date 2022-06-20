package com.marcosavard.commons.math.algebra;

public class NewtonSolver {
  /**
   * Newton's Method to solve f(x)=y for x with an initial guess of x0.
   *
   * @param functionCallable f(x)=y
   * @param y y
   * @param x0 x0
   * @return x x
   * @throws SolverException Solver does not converge
   */
  public double solve(FunctionCallable functionCallable, double y, double x0)
      throws SolverException, IllegalArgumentException {

    double x = x0;
    double xNew;
    double maxCount = 10;
    double count = 0;
    while (true) {
      if (count > maxCount) {
        throw new SolverException("Solver does not converge!");
      }
      double dx = x / 1000.0;
      double z = functionCallable.function(x);
      xNew = x + dx * (y - z) / (functionCallable.function(x + dx) - z);
      if (Math.abs((xNew - x) / xNew) < 0.0001) {
        return xNew;
      }
      x = xNew;
      count++;
    }
  }


  public static interface FunctionCallable {

    /**
     *
     * @param x x
     * @return y y
     * @throws SolverException Solver does not converge
     */
    public double function(double x) throws SolverException;

  }

  @SuppressWarnings("serial")
  public static final class SolverException extends Exception {

    public SolverException(String message) {
      super(message);
    }

  }

}
