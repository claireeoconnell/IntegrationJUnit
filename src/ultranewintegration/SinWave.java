/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultranewintegration;

/**
 * A SinWave describes points along a sine wave of f(x) = a*sin(jx).
 * @author Jacob M. Litman
 */
public class SinWave extends FunctionDataCurve {
    
    private final double a;
    private final double j;
    private final double jinv;
    
    /**
     * Constructs f(x) = a*sin(jx).
     * @param x
     * @param a
     * @param j 
     */
    public SinWave(double[] x, double a, double j) {
        this(x, false, a, j);
    }

    /**
     * Constructs f(x) = a*sin(jx).
     * @param x
     * @param halfWidthEnds Use half-width start and end bins.
     * @param a
     * @param j 
     */
    public SinWave(double[] x, boolean halfWidthEnds, double a, double j) {
        int npoints = x.length;
        points = new double[npoints];
        this.a = a;
        this.j = j;
        jinv = 1.0 / j;
        this.halfWidthEnd = halfWidthEnds;
        
        for (int i = 0; i < points.length; i++) {
            points[i] = sinAt(x[i]);
        }
        lb = x[0];
        ub = x[npoints-1];
        assertXIntegrity(x);
        this.x = new double[x.length];
        System.arraycopy(x, 0, this.x, 0, x.length);
    }
    
    @Override
    public double integralAt(double x) {
        return -1 * a * jinv * Math.cos(j*x);
    }
    
    @Override
    public double fX(double x) {
        return sinAt(x);
    }
    
    // Private, non-overrideable method for use in the constructor.
    private double sinAt(double x) {
        return a*Math.sin(j*x);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Sine wave f(x) = ");
        sb.append(a).append("*sin(").append(j).append("x)");
        sb.append(String.format(" with %d points from lower bound %9.3g and upper bound %9.3g", points.length, lb, ub));
        if (halfWidthEnd) {
            sb.append(" and half-width start/end bins");
        }
        
        return sb.toString();
    }
}
