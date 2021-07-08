/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultranewintegration;

/**
 * A CosineWave describes points along a sine wave of f(x) = a*sin(jx).
 * @author Jacob M. Litman
 */
public class CosineWave extends FunctionDataCurve {
    
    private final double a;
    private final double j;
    private final double jinv;
    
    /**
     * Constructs f(x) = a*cos(jx).
     * @param x
     * @param a
     * @param j 
     */
    public CosineWave(double[] x, double a, double j) {
        this(x, false, a, j);
    }

    /**
     * Constructs f(x) = a*cos(jx).
     * @param x
     * @param halfWidthEnds Use half-width start and end bins.
     * @param a
     * @param j 
     */
    public CosineWave(double[] x, boolean halfWidthEnds, double a, double j) {
        int npoints = x.length;
        points = new double[npoints];
        this.a = a;
        this.j = j;
        jinv = 1.0 / j;
        this.halfWidthEnd = halfWidthEnds;
        
        for (int i = 0; i < points.length; i++) {
            points[i] = cosAt(x[i]);
        }
        lb = x[0];
        ub = x[npoints-1];
        assertXIntegrity(x);
        this.x = new double[x.length];
        System.arraycopy(x, 0, this.x, 0, x.length);
    }
    
    @Override
    public double integralAt(double x) {
        return a * jinv * Math.sin(j*x);
    }
    
    @Override
    public double fX(double x) {
        return cosAt(x);
    }
    
    // Private, non-overrideable method for use in the constructor.
    private double cosAt(double x) {
        return a*Math.cos(j*x);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cosine wave f(x) = ");
        sb.append(a).append("*cos(").append(j).append("x)");
        sb.append(String.format(" with %d points from lower bound %9.3g and upper bound %9.3g", points.length, lb, ub));
        if (halfWidthEnd) {
            sb.append(" and half-width start/end bins");
        }
        
        return sb.toString();
    }
}
