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
    
    public SinWave(double[] x, double a, double j) {
        this(x, false, a, j);
    }

    public SinWave(double[] x, boolean halfWidthEnds, double a, double j) {
        int npoints = x.length;
        points = new double[npoints];
        this.a = a;
        this.j = j;
        jinv = 1.0 / j;
        this.halfWidthEnd = halfWidthEnds;
        
        for (int i = 0; i < points.length; i++) {
            points[i] = fX(x[i]);
        }
        lb = x[0];
        ub = x[npoints-1];
        assertXIntegrity(x);
    }
    
    @Override
    public double integralAt(double x) {
        return -1 * a * jinv * Math.cos(j*x);
    }
    
    @Override
    public double fX(double x) {
        return a*Math.sin(j*x);
    }
}
