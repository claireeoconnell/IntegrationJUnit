/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultranewintegration;

/**
 * A CubicCurve describes points along a polynomial function.
 * @author Jacob M. Litman
 */
public class PolynomialCurve extends FunctionDataCurve {
    
    private final double[] coeff;

    public PolynomialCurve(double[] x, double[] coefficients) {
        this(x, false, coefficients);
    }
    
    public PolynomialCurve(double[] x, boolean halfWidthEnds, double[] coefficients) {
        int npoints = x.length;
        points = new double[npoints];
        coeff = new double[coefficients.length];
        System.arraycopy(coefficients, 0, coeff, 0, coefficients.length);
        this.halfWidthEnd = halfWidthEnds;
        
        for (int i = 0; i < points.length; i++) {
            points[i] = fX(x[i]);
        }
        lb = x[0];
        ub = x[npoints-1];
        assertXIntegrity(x);
        this.x = new double[x.length];
        System.arraycopy(x, 0, this.x, 0, x.length);
    }
    
    @Override
    public double integralAt(double x) {
        //double total = x * coeff[0];
        double total = 0;
        for (int i = 0; i < coeff.length; i++) {
            double val = 1.0 / ((double) i+1);
            val *= coeff[i];
            for (int j = 0; j <= i; j++) {
                val *= x;
            }
            total += val;
        }
        return total;
    }
    
    @Override
    public double fX(double x) {
        double total = 0.0;
        for (int i = 0; i < coeff.length; i++) {
            double val = coeff[i];
            for (int j = 0; j < i; j++) {
                val *= x;
            }
            total += val;
        }
        return total;
    }
}
