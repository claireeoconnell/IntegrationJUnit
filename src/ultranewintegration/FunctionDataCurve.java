/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultranewintegration;

/**
 * A DataCurve represents a set of points along a 1-dimensional, integrable 
 * function.
 * @author Jacob
 */
public abstract class FunctionDataCurve implements DataSet {
    protected double lb;
    protected double ub;
    protected double[] points;
    protected boolean halfWidthEnd;
    
    @Override
    public double lowerBound() {
        return lb;
    }

    @Override
    public double upperBound() {
        return ub;
    }
    
    @Override
    public int numPoints() {
        return points.length;
    }
    
    @Override
    public double binWidth() {
        double divisor = halfWidthEnds() ? (double) (points.length - 2) : (double) (points.length - 1);
        return (ub - lb) / divisor;
    }
    
    /**
     * Evaluates the functions analytical integral over the entire range of points.
     * @return Exact finite integral
     */
    public double analyticalIntegral() {
        return anaylticalIntegral(lowerBound(), upperBound());
    }
    
    /**
     * Evaluates the function's analytical integral over a range.
     * @param lb Lower integration bound
     * @param ub Upper integration bound
     * @return Exact finite integral of range
     */
    public double anaylticalIntegral(double lb, double ub) {
        return integralAt(ub) - integralAt(lb);
    }
    
    /**
     * Analytical integral at a point.
     * @param x Point
     * @return Exact finite integral of 0 to this point
     */
    public abstract double integralAt(double x);
    
    /**
     * Evaluates the function at x.
     * @param x x
     * @return f(x)
     */
    public abstract double fX(double x);
    
    @Override
    public double getPoint(int index) {
        return points[index];
    }
    
    @Override
    public double[] getAllPoints() {
        int npoints = points.length;
        double[] retArray = new double[npoints];
        System.arraycopy(points, 0, retArray, 0, npoints);
        return retArray;
    }
    
    /**
     * Used to check that the passed-in x array is composed of equally-spaced
     * points from lb to ub.
     * @param x 
     */
    protected final void assertXIntegrity(double[] x) {
        assert ub > lb;
        int nX = numPoints();
        double sep = binWidth();
        if (halfWidthEnd) {
            assert x.length == nX;
            assert lb == x[0];
            assert ub == x[nX-1];
            
            assert approxEquals(x[1], lb + 0.5*sep);
            assert approxEquals(x[nX-2], (ub - 0.5*sep));
            
            for (int i = 2; i < (nX - 2); i++) {
                double target = lb + 0.5 * sep;
                target += ((i-1) * sep);
                assert approxEquals(x[i], target);
            }
        } else {
            for (int i = 0; i < x.length; i++) {
                assert approxEquals(x[i], x[0] + i * sep);
            }
        }
    }
    
    /**
     * Checks for equality to +/- 10 ulp.
     * @param x1
     * @param x2
     * @return 
     */
    public static boolean approxEquals(double x1, double x2) {
        return (approxEquals(x1, x2, 10.0));
    }
    
    /**
     * Compare two doubles to machine precision.
     * @param x1
     * @param x2
     * @param ulpMult
     * @return 
     */
    public static boolean approxEquals(double x1, double x2, double ulpMult) {
        double diff = Math.abs(x1 - x2);
        // Ulp the larger of the two values.
        double ulp = Math.ulp(Math.max(Math.abs(x1), Math.abs(x2)));
        return (diff < (ulp * ulpMult));
    }
    
    @Override
    public boolean halfWidthEnds() {
        return halfWidthEnd;
    }
}
