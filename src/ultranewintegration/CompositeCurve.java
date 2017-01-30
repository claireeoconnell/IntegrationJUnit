/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultranewintegration;

import java.util.Arrays;
import java.util.List;

/**
 * A CompositeCurve represents points along a sum of functions which also extend
 * FunctionDataCurve.
 * @author Jacob M. Litman
 */
public class CompositeCurve extends FunctionDataCurve {
    private final FunctionDataCurve[] curves;
    private final double[] coeffs;
    private final int nCurves;
    
    public CompositeCurve(List<FunctionDataCurve> componentCurves, List<Double> coefficients) {
        assert !componentCurves.isEmpty();
        nCurves = componentCurves.size();
        this.curves = new FunctionDataCurve[nCurves];
        componentCurves.toArray(this.curves);
        assert (coefficients == null || nCurves == coefficients.size());
        
        if (coefficients == null) {
            coeffs = new double[nCurves];
            Arrays.fill(coeffs, 1.0);
        } else {
            coeffs = coefficients.stream().mapToDouble(Double::doubleValue).toArray();
        }
        
        FunctionDataCurve curve0 = curves[0];
        lb = curve0.lowerBound();
        ub = curve0.upperBound();
        halfWidthEnd = curve0.halfWidthEnds();
        x = curve0.getX();
        double sep = curve0.binWidth();
        
        boolean isInvalid = Arrays.stream(curves).anyMatch((FunctionDataCurve c) -> {
            if (lb != c.lowerBound()) {
                return true;
            }
            if (ub != c.upperBound()) {
                return true;
            }
            if (halfWidthEnd != c.halfWidthEnds()) {
                return true;
            }
            return sep != c.binWidth();
        });
        if (isInvalid) {
            throw new IllegalArgumentException(" Not all curves passed to CompositeCurve had the same x[] points!");
        }
        
        int nPoints = curve0.numPoints();
        points = new double[nPoints];
        for (int i = 0; i < nPoints; i++) {
            points[i] = 0.0;
            for (int j = 0; j < nCurves; j++) {
                points[i] += (coeffs[j] * curves[j].getPoint(i));
            }
        }
    }

    @Override
    public double integralAt(double x) {
        double val = 0.0;
        for (int i = 0; i < nCurves; i++) {
            val += (curves[i].integralAt(x) * coeffs[i]);
        }
        return val;
    }
    
    @Override
    public double fX(double x) {
        double val = 0.0;
        for (int i = 0; i < nCurves; i++) {
            val += (curves[i].fX(x) * coeffs[i]);
        }
        return val;
    }
    
    public List<FunctionDataCurve> getSubCurves() {
        return Arrays.asList(curves);
    }
}
