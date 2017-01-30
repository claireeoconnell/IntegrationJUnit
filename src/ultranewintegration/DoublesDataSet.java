/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultranewintegration;

import static ultranewintegration.FunctionDataCurve.approxEquals;
import static ultranewintegration.FunctionDataCurve.approxEquals;
import static ultranewintegration.FunctionDataCurve.approxEquals;
import static ultranewintegration.FunctionDataCurve.approxEquals;

/**
 * Descibes a set of x, f(x) obtained by some mechanism; intended for numerical
 * integration.
 * @author Jacob M. Litman
 */
public class DoublesDataSet implements DataSet {
    
    private final double[] x;
    private final double[] fX;
    private final double lb;
    private final double ub;
    private final int nX;
    private final double sep;
    private final boolean halfWidthEnd;
    
    public DoublesDataSet(double[] x, double[] fX) {
        this(x, fX, false);
    }
    
    public DoublesDataSet(double[] x, double[] fX, boolean halvedEnds) {
        nX = x.length;
        assert nX == fX.length;
        
        this.x = new double[nX];
        System.arraycopy(x, 0, this.x, 0, nX);
        
        this.fX = new double[nX];
        System.arraycopy(fX, 0, this.fX, 0, nX);
        
        lb = x[0];
        ub = x[nX-1];
        //sep = ((ub - lb) / ((double) nX));
        halfWidthEnd = halvedEnds;
        double sepDist = ub - lb;
        sep = halfWidthEnd ? (sepDist / ((double) nX-2)) : (sepDist / ((double) nX-1));
        assertXIntegrity(x);
    }

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
        return x.length;
    }

    @Override
    public double binWidth() {
        return sep;
    }

    @Override
    public double getPoint(int index) {
        return fX[index];
    }

    @Override
    public double[] getAllPoints() {
        double[] pts = new double[nX];
        System.arraycopy(fX, 0, pts, 0, nX);
        return pts;
    }
    
    @Override
    public boolean halfWidthEnds() {
        return halfWidthEnd;
    }
    
    /**
     * Used to check that the passed-in x array is composed of equally-spaced
     * points from lb to ub.
     * @param x 
     */
    private void assertXIntegrity(double[] x) {
        assert ub > lb;
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
    
}
