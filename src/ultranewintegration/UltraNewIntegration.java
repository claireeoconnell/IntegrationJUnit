/**
 * Title: Force Field X.
 *
 * Description: Force Field X - Software for Molecular Biophysics.
 *
 * Copyright: Copyright (c) Michael J. Schnieders 2001-2016.
 *
 * This file is part of Force Field X.
 *
 * Force Field X is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 as published by
 * the Free Software Foundation.
 *
 * Force Field X is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Force Field X; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Linking this library statically or dynamically with other modules is making a
 * combined work based on this library. Thus, the terms and conditions of the
 * GNU General Public License cover the whole combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce an
 * executable, regardless of the license terms of these independent modules, and
 * to copy and distribute the resulting executable under terms of your choice,
 * provided that you also meet, for each linked independent module, the terms
 * and conditions of the license of that module. An independent module is a
 * module which is not derived from or based on this library. If you modify this
 * library, you may extend this exception to your version of the library, but
 * you are not obligated to do so. If you do not wish to do so, delete this
 * exception statement from your version.
 */
package ultranewintegration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This program integrates using three methods: the trapezoidal method,
 * Simpson's Three Point Integration, and Boole's Five Point Integration
 *
 * @author ceoconnell
 */
public class UltraNewIntegration {

    private final static double[] x = new double[201];
    private static final double DEFAULT_WIDTH = 0.005;
    private static final double ONE_THIRD = (1.0 / 3.0);
    private static final double BOOLE_FACTOR = (2.0 / 45.0);

    static {
        /*x[0] = 0;
        for (int i = 1; i < 201; i++) {
            x[i] = .0025 + .005 * (i - 1);
        }
        x[201] = 1;*/
        for (int i = 0; i < x.length; i++) {
            x[i] = i * DEFAULT_WIDTH;
        }
    }

    public static void main(String[] args) {
        double testAnswer;
        double testTrap, testTrapRight, avgTrap, avgTrapError;
        double testSimp, testSimpRight, avgSimp, avgSimpError;
        double testBoole, testBooleRight, avgBoole, avgBooleError;
        double testRect, testRectRight, avgRect, avgRectError;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        FunctionDataCurve testData = generateStandardData();

        testAnswer = 2.98393938659796;
        System.out.print("The test case answer is " + testAnswer + "\n\n");

        /*
        testRect = rectangularMethodLeft(generateTestData_v1());
        //System.out.print("Testing rectangular left " + testRect + "\n");
        testRectRight = rectangularMethodRight(generateTestData_v1());
        //System.out.print("Testing rectangular right " + testRectRight + "\n");
        avgRect = (testRect + testRectRight) / 2.0;
        //System.out.print("Average rectangular " + avgRect + "\n");
        avgRectError = Math.abs(testAnswer - avgRect) / testAnswer * 100;
        //System.out.print("Average rectangular error " + decimalFormat.format(avgRectError) + "%\n");

        testTrap = trapInputLeft(generateTestData_v1());
        //System.out.print("Testing trapezoid left " + testTrap + "\n");
        testTrapRight = trapInputRight(generateTestData_v1());
        //System.out.print("Testing trapezoid right " + testTrapRight + "\n");
        avgTrap = (testTrap + testTrapRight) / 2.0;
        //System.out.print("Average trap " + avgTrap + "\n");
        avgTrapError = Math.abs(avgTrap - testAnswer) / testAnswer * 100;
        //System.out.print("Avg Trapezoidal error  " + decimalFormat.format(avgTrapError) + "%\n");

        testSimp = simpsonsLeft(generateTestData_v1()) + HalfBinComposite(generateTestData_v1(), IntegrationType.SIMPSONS, IntegrationSide.LEFT);
        //System.out.print("Testing Simpsons left " + testSimp + "\n");
        testSimpRight = simpsonsRight(generateTestData_v1()) + HalfBinComposite(generateTestData_v1(), IntegrationType.SIMPSONS, IntegrationSide.RIGHT);
        //System.out.print("Testing Simpsons right " + testSimpRight + "\n");
        avgSimp = (testSimp + testSimpRight) / 2.0;
        //System.out.print("Average Simpsons " + avgSimp + "\n");
        avgSimpError = Math.abs(testAnswer - avgSimp) / testAnswer * 100;
        //System.out.print("Average Simpsons error " + decimalFormat.format(avgSimpError) + "%\n");

        testBoole = booleLeft(generateTestData_v1()) + HalfBinComposite(generateTestData_v1(), IntegrationType.BOOLE, IntegrationSide.LEFT);
        //System.out.print("Testing Boole left " + testBoole + "\n");
        testBooleRight = booleRight(generateTestData_v1()) + HalfBinComposite(generateTestData_v1(), IntegrationType.BOOLE, IntegrationSide.RIGHT);
        //System.out.print("Testing Boole Right " + testBooleRight + "\n");
        avgBoole = (testBoole + testBooleRight) / 2.0;
        //System.out.print("Average Boole " + avgBoole + "\n");
        avgBooleError = Math.abs(testAnswer - avgBoole) / testAnswer * 100;
        //System.out.print("Average Boole error " + decimalFormat.format(avgBooleError) + "%\n");
        
        //Average integrals
        System.out.print("Average integrals \n\n");
        System.out.print("Average rectangular " + avgRect + "\n");
        System.out.print("Average trap " + avgTrap + "\n");
        System.out.print("Average Simpsons " + avgSimp + "\n");
        System.out.print("Average Boole " + avgBoole + "\n");

        //Average integral errors
        System.out.print("\nAverage integral error \n\n");
        System.out.print("Average rectangular error " + decimalFormat.format(avgRectError) + "%\n");
        System.out.print("Avg Trapezoidal error  " + decimalFormat.format(avgTrapError) + "%\n");
        System.out.print("Average Simpsons error " + decimalFormat.format(avgSimpError) + "%\n");
        System.out.print("Average Boole error " + decimalFormat.format(avgBooleError) + "%\n");
        */
    }
    
    private static FunctionDataCurve generateStandardData() {
        // Replace as desired with other standardized data generators.
        return generateStandardDataV2();
    }
    
    private static FunctionDataCurve generateStandardDataV2() {
        double[] x = new double[201];
        for (int i = 0; i < x.length; i++) {
            x[i] = i * DEFAULT_WIDTH;
        }
        List<Double> coeffs = null;
        List<FunctionDataCurve> curves = new ArrayList<>();
        
        FunctionDataCurve newCurve = new SinWave(x, 10, 6);
        curves.add(newCurve);
        
        newCurve = new CosineWave(x, 7, 5);
        curves.add(newCurve);
        
        newCurve = new SinWave(x, 11, 8);
        curves.add(newCurve);
        
        // Alternate method: set leading coefficients for the CompositeCurve constructor.
        /*
        coeffs = new ArrayList<>();
        DataCurve newCurve = new SinWave(x, 1, 6);
        coeffs.add(10.0);
        curves.add(newCurve);
        
        newCurve = new CosineWave(x, 1, 5);
        coeffs.add(7.0);
        curves.add(newCurve);
        
        newCurve = new SinWave(x, 1, 8);
        coeffs.add(11.0);
        curves.add(newCurve);*/
        
        return new CompositeCurve(curves, coeffs);
    }

    public static double averageIntegral(double leftInt, double rightInt) {
        double avgInt = 0;

        avgInt = (leftInt + rightInt) / 2.0;
        //System.out.print("Average integral " + avgInt + "\n");
        return avgInt;
    }

    public static double[] generateTestData_v1() {
        double y[] = new double[202];

        for (int i = 0; i < 202; i++) {
            y[i] = 10 * Math.sin(6 * x[i]) - 7 * Math.cos(5 * x[i]) + 11 * Math.sin(8 * x[i]);
        }

        return y;
    }
    
    /**
     * Generates a set of points along x.
     * @param lb Beginning value, inclusive
     * @param ub Ending value, inclusive
     * @param nPoints Total number of points
     * @param halfWidthEnds If ends should have 1/2 regular separation
     * @return 
     */
    public static double[] generateXPoints(double lb, double ub, int nPoints, boolean halfWidthEnds) {
        if (lb >= ub) {
            throw new IllegalArgumentException("ub must be greater than lb");
        }
        double[] points = new double[nPoints];
        int sepDivisor = halfWidthEnds ? nPoints - 2 : nPoints - 1;
        double sep = (ub - lb) / ((double) sepDivisor);
        
        if (halfWidthEnds) {
            points[0] = lb;
            points[nPoints-1] = ub;
            for (int i = 1; i < nPoints - 1; i++) {
                points[i] = lb + ((double) i)*sep - 0.5*sep;
            }
        } else {
            for (int i = 0; i < nPoints; i++) {
                points[i] = lb + ((double) i)*sep;
            }
        }
        return points;
    }

    public static double HalfBinComposite(double[] inputData, IntegrationType type, IntegrationSide side) {
        double halfBinComposite = 0, lowerHalfBin, upperHalfBin, upperTrapArea;
        double lowerTrapArea, upperSimpson, lowerSimpson;
        int n;

        n = inputData.length;

        //Split by side first, then integration type
        if (side == IntegrationSide.LEFT) {
            //using trapezoidal integral for lower half bin
            lowerHalfBin = (inputData[1] + inputData[0]) / 2.0 * (x[1] - x[0]);
            halfBinComposite += lowerHalfBin;
            //System.out.print("lower bin " + halfBinComposite + "\n");
            switch (type) {
                //case 1 is the Simpson's method, uses trapezoid on right for bin left out of Simpson's
                case SIMPSONS:
                    upperTrapArea = (inputData[n - 3] + inputData[n - 2]) / 2.0 * (x[n - 2] - x[n - 3]);
                    //System.out.print("upper trap area " + upperTrapArea + "\n");
                    halfBinComposite += upperTrapArea;
                    break;
                //case 2 is the Boole's method, uses Simpsons and trapezoidal integral on right to cover remaining bins
                case BOOLE:
                    upperSimpson = (1.0 / 3.0) * (x[n - 4] - x[n - 5]) * (inputData[n - 5] + 4 * inputData[n - 4] + inputData[n - 3]);
                    halfBinComposite += upperSimpson;
                    //System.out.print("Upper Simpson " + upperSimpson + "\n");
                    upperTrapArea = (inputData[n - 3] + inputData[n - 2]) / 2.0 * (x[n - 2] - x[n - 3]);
                    //System.out.print("upper trap area " + upperTrapArea + "\n");
                    halfBinComposite += upperTrapArea;
                    break;
            }
        } else if (side == IntegrationSide.RIGHT) {
            //upper half bin calculated with trapezoid
            upperHalfBin = (inputData[n - 1] + inputData[n - 2]) / 2.0 * (x[n - 1] - x[n - 2]);
            halfBinComposite += upperHalfBin;
            switch (type) {
                //case 1 is the Simpson's method, uses trapezoid on left for bin left out of Simpson's
                case SIMPSONS:
                    lowerTrapArea = (inputData[1] + inputData[2]) / 2.0 * (x[2] - x[1]);
                    halfBinComposite += lowerTrapArea;
                    break;
                //case 2 is the Boole's method, uses Simpsons and trapezoidal integral on left to cover remaining bins
                case BOOLE:
                    lowerTrapArea = (inputData[1] + inputData[2]) / 2.0 * (x[2] - x[1]);
                    halfBinComposite += lowerTrapArea;
                    lowerSimpson = (1.0 / 3.0) * (x[3] - x[2]) * (inputData[2] + 4 * inputData[3] + inputData[4]);
                    halfBinComposite += lowerSimpson;
                    break;
            }
        }
        //System.out.print("Half bin composite " + halfBinComposite + "\n");
        return halfBinComposite;
    }
    
    public static double trapezoidal(DataSet data, IntegrationSide side) {
        double area = 0;
        int lb = 0;
        int ub = data.numPoints() - 1;
        if (data.halfWidthEnds()) {
            area = trapezoidalEnds(data, side);
            lb++;
            ub--;
        }
        area += trapezoidal(data, side, lb, ub);
        return area;
    }
    
    public static double trapezoidal(DataSet data, IntegrationSide side, int lb, int ub) {
        int nPoints = data.numPoints();
        double width = data.binWidth();
        double[] points = data.getAllPoints();
        
        double area = 0.5 * points[lb];
        area += 0.5 * points[ub];
        for (int i = lb + 1; i < ub; i++) {
            area += points[i];
        }
        area *= width;
        return area;
    }
    
    public static double trapezoidalEnds(DataSet data, IntegrationSide side) {
        double width = 0.5 * data.binWidth();
        int nPts = data.numPoints();
        double area = data.getPoint(0) + data.getPoint(1) + data.getPoint(nPts-2) + data.getPoint(nPts - 1);
        area *= (0.5 * width);
        return area;
    }

    public static double trapInputLeft(double[] inputData, double width) {
        double trapIntegral = 0, sum, area, total;

        int n = 0;

        n = x.length;

        sum = 0;
        total = 0;
        for (int a = 0; a < n - 2; a++) {
            if (a > 0) {
                area = (inputData[a + 1] + inputData[a]) / (double) 2 * (x[a + 1] - x[a]);
                sum = area + total;
                total = sum;
            }
            if (a == n - 3) {
                trapIntegral = sum;
                //System.out.print("\nThe trapezoidal integral is " + trapIntegral + "\n");
            }
            if (a == 0) {
                area = (inputData[a + 1] + inputData[a]) / (double) 2 * (x[a + 1] - x[a]);
                total = area;
            }
        }

        return trapIntegral;
    }

    public static double trapInputRight(double[] inputData, double width) {
        double trapIntegral = 0, sum, area, total;
        int n = 0;

        n = x.length;

        sum = 0;
        total = 0;
        for (int a = 1; a < n - 1; a++) {
            if (a > 1) {
                area = (inputData[a + 1] + inputData[a]) / 2.0 * (x[a + 1] - x[a]);
                sum = area + total;
                total = sum;
            }
            if (a == n - 2) {
                trapIntegral = sum;
                //System.out.print("\nThe trapezoidal integral is " + trapIntegral + "\n");

            }
            if (a == 1) {
                area = (inputData[a + 1] + inputData[a]) / 2.0 * (x[a + 1] - x[a]);
                total = area;
            }
        }

        return trapIntegral;
    }
    
    public static double simpsons(DataSet data, IntegrationSide side) {
        double area = 0;
        int lb = 0;
        int ub = data.numPoints() - 1;
        if (data.halfWidthEnds()) {
            area = trapezoidalEnds(data, side);
            lb++;
            ub--;
        }
        area += simpsons(data, side, lb, ub);
        return area;
    }

    public static double simpsons(DataSet data, IntegrationSide side, int lb, int ub) {
        double area = 0;
        double width = data.binWidth();
        double[] points = data.getAllPoints();
        int increment = 2;
        
        int nBins = (ub - lb) / increment;
        int lowerNeglected;
        int upperNeglected;
        
        switch (side) {
            case RIGHT:
                for (int i = ub; i > (lb + increment - 1); i -= increment) {
                    area += points[i] + (4*points[i-1]) + points[i-2];
                }
                lowerNeglected = lb;
                upperNeglected = ub - (increment*nBins);
                break;
            case LEFT:
            default:
                for (int i = lb; i < (ub - increment + 1); i+= increment) {
                    area += points[i] + (4*points[i+1]) + points[i+2];
                }
                lowerNeglected = lb + (increment*nBins);
                upperNeglected = ub;
                break;
        }
        area *= ONE_THIRD;
        area *= width;
        
        area += finishIntegration(data, side, lowerNeglected, upperNeglected, IntegrationType.SIMPSONS);
        return area;
    }
    
    private static double finishIntegration(DataSet data, IntegrationSide side, int lb, int ub, IntegrationType type) {
        int totPoints = (ub - lb);
        
        int perBin = type.binsNeeded();
        int increment = perBin - 1;
        increment = Math.max(1, increment); // Needed for rectangular integration
        
        int nBins = totPoints / increment;
        int remainder = totPoints % increment;
        
        IntegrateWindow intMode;
        switch (type) {
            case BOOLE:
                intMode = UltraNewIntegration::booles;
                break;
            case SIMPSONS:
                intMode = UltraNewIntegration::simpsons;
                break;
            case RECTANGULAR:
                intMode = UltraNewIntegration::rectangular;
                break;
            case TRAPEZOIDAL:
            default:
                intMode = UltraNewIntegration::trapezoidal;
                break;
        }
        
        double area = 0.0;
        
        switch (side) {
            case RIGHT:
                for (int i = ub; i > (lb - 1 + increment); i -= increment) {
                    area += intMode.toArea(data, side, (i-increment), i);
                }
                ub -= (nBins * increment);
                break;
            case LEFT:
            default:
                for (int i = lb; i < (ub + 1 - increment); i+= increment) {
                    area += intMode.toArea(data, side, i, (i + increment));
                }
                lb += (nBins * increment);
                break;
        }
        
        assert remainder == (ub - lb);
        switch(remainder) {
            case 0:
                break;
            case 1:
                area += trapezoidal(data, side, lb, ub);
                break;
            case 2:
                area += simpsons(data, side, lb, ub);
                break;
            case 3:
                // Alternately implement Simpson's 3/8 4-point rule.
                area += simpsons(data, side, lb, ub); // Will recursively call finishIntegration, thus getting another round of trapezoidal.
                break;
            case 4:
            default:
                throw new IllegalArgumentException("This should not be currently possible.");
                /*System.err.println(String.format("Total points: %d perBin %d increment %d type %s nBins %d remainder %d", totPoints, perBin, increment, type.toString(), nBins, remainder));
                break;*/
        }
        return area;
    }
    
    public static double simpsonsLeft(double[] inputData, double width) {
        double normalSimpsons = 0, area, sum, total;
        int n;

        n = inputData.length;

        sum = 0;
        total = 0;
        for (int a = 1; a < n - 4; a += 2) {
            area = (1.0 / 3.0) * (x[a + 1] - x[a]) * (inputData[a] + 4 * inputData[a + 1] + inputData[a + 2]);
            normalSimpsons += area;
            /*if (a == n-5){
             System.out.print("Last Simpson's left starts at n-5 \n");
             }
             */
            //With half bin, goes into n-5
        }

        return normalSimpsons;
    }

    public static double simpsonsRight(double[] inputData, double width) {
        double normalSimpsons = 0, area;
        int n;

        n = inputData.length;

        for (int a = 2; a < n - 3; a += 2) { //extra trap on lower edge so right edge of rightmost bin aligns with the upper half bin
            area = (1.0 / 3.0) * (x[a + 1] - x[a]) * (inputData[a] + 4 * inputData[a + 1] + inputData[a + 2]);
            normalSimpsons += area;
            /*if (a == n-4){ 
             System.out.print("Last Simpson's right starts at n-4\n");
             }
             */

        }

        return normalSimpsons;
    }
    
    public static double booles(DataSet data, IntegrationSide side) {
        double area = 0;
        int lb = 0;
        int ub = data.numPoints() - 1;
        if (data.halfWidthEnds()) {
            area = trapezoidalEnds(data, side);
            lb++;
            ub--;
        }
        area += booles(data, side, lb, ub);
        return area;
    }

    public static double booles(DataSet data, IntegrationSide side, int lb, int ub) {
        double area = 0;
        double width = data.binWidth();
        double[] points = data.getAllPoints();
        int increment = 4;
        
        int nBins = (ub - lb) / increment;
        int lowerNeglected;
        int upperNeglected;
        
        switch (side) {
            case RIGHT:
                for (int i = ub; i > (lb + increment - 1); i -= increment) {
                    area += (7 * points[i] + 32*points[i-1] + 12*points[i-2] + 32*points[i-3] + 7*points[i-4]);
                }
                lowerNeglected = lb;
                upperNeglected = ub - (increment*nBins);
                break;
            case LEFT:
            default:
                for (int i = lb; i < (ub - increment + 1); i+= increment) {
                    area += (7*points[i] + 32*points[i+1] + 12*points[i+2] + 32*points[i+3] + 7*points[i+4]);
                }
                lowerNeglected = lb + (increment*nBins);
                upperNeglected = ub;
                break;
        }
        area *= BOOLE_FACTOR;
        area *= width;
        
        area += finishIntegration(data, side, lowerNeglected, upperNeglected, IntegrationType.BOOLE);
        return area;
    }

    public static double booleLeft(double[] inputData, double width) {
        double normalBoole = 0, area;
        int n;

        n = inputData.length;

        for (int a = 1; a < n - 5; a += 4) {
            area = (2.0 / 45.0) * (x[a + 1] - x[a]) * (7 * inputData[a] + 32 * inputData[a + 1] + 12 * inputData[a + 2] + 32 * inputData[a + 3] + 7 * inputData[a + 4]);
            normalBoole += area;

            /*if (a == n - 9) { //interval not compatible with 201 bins because of the half sized bin on the end
             System.out.print("Last Boole's left starts at n-9 \n"); 
             }
             */
        }

        return normalBoole;
    }
    
    @Deprecated
    public static double booleRight(double[] inputData) {
        return booleRight(inputData, DEFAULT_WIDTH);
    }

    public static double booleRight(double[] inputData, double width) {
        double normalBoole = 0, area;
        int n;

        n = inputData.length;

        for (int a = 4; a < n - 5; a += 4) { //Simpsons and trapezoid + lower bin on left
            area = (2.0 / 45.0) * (x[a + 1] - x[a]) * (7 * inputData[a] + 32 * inputData[a + 1] + 12 * inputData[a + 2] + 32 * inputData[a + 3] + 7 * inputData[a + 4]);
            normalBoole += area;

            /*if (a == n - 6) { //extra Simpsons on left edge so right edge of rightmost bin aligns with the upper half bin
             System.out.print("Last Boole's right starts at n-6 \n"); 
             }
             */
        }

        return normalBoole;
    }
    
    private static double finishIntegrationLeft(double[] inputData, int pos, IntegrationType maxLevel, double width) {
        int nBins = inputData.length;
        int remainingBins = nBins - pos;
        double[] subset = new double[remainingBins];
        System.arraycopy(inputData, pos, subset, 0, remainingBins);
        
        int levelwidth = maxLevel.binsNeeded();
        int fullBins = remainingBins / levelwidth;
        
        double area = 0;
        if (fullBins > 0) {
            switch (maxLevel) {
                case BOOLE:
                    area = booleLeft(subset, width);
                    break;
                case SIMPSONS:
                    area = simpsonsLeft(subset, width);
                    break;
                case TRAPEZOIDAL:
                    area = trapInputLeft(subset, width);
                    break;
                case RECTANGULAR:
                    area = rectangularMethodLeft(subset, width);
                    break;
            }
        }
        
        pos += (levelwidth * fullBins);
        subset = new double[remainingBins];
        System.arraycopy(inputData, pos, subset, 0, remainingBins);
        switch(remainingBins) {
            case 1:
                break;
        }
        return 0;
    }
    
    @Deprecated
    public static double rectangularMethodLeft(double[] inputData) {
        return rectangularMethodLeft(inputData, DEFAULT_WIDTH);
    }

    public static double rectangularMethodLeft(double[] inputData, double width) {
        /*double rectangularIntegral = 0, area;
        double[] y = new double[202];
        int n;

        n = inputData.length;

        y = generateTestData_v1();

        for (int a = 0; a < n - 2; a++) {
            area = (x[a + 1] - x[a]) * y[a];
            rectangularIntegral += area;
        }

        //System.out.print("The left rectangular method is " + rectangularIntegral + "\n");
        return rectangularIntegral;*/
        int n = inputData.length;
        double area = 0.0;
        for (int a = 0; a < n-1; a++) {
            area += (inputData[a] * width);
        }
        return area;
    }
    
    public static double rectangular(DataSet data, IntegrationSide side) {
        double area = 0;
        int lb = 0;
        int ub = data.numPoints() - 1;
        if (data.halfWidthEnds()) {
            area = rectangularEnds(data, side);
            ++lb;
            --ub;
        }
        area += rectangular(data, side, lb, ub);
        return area;
    }
    
    public static double rectangular(DataSet data, IntegrationSide side, int lb, int ub) {
        double area = 0;
        double width = data.binWidth();
        double[] points = data.getAllPoints();
        assert ub > lb;
        assert ub < points.length;
        
        switch (side) {
            case RIGHT:
                for (int i = ub; i > lb; i--) {
                    area += (width * points[i]);
                }
                break;
            case LEFT:
            default:
                for (int i = lb; i < ub; i++) {
                    area += width * points[i];
                }
                break;
        }
        return area;
    }
    
    public static double rectangularEnds(DataSet data, IntegrationSide side) {
        double width = 0.5 * data.binWidth();
        double area = 0;
        int npts = data.numPoints();
        switch (side) {
            case LEFT:
                area = data.getPoint(0) * width;
                area += (data.getPoint(npts - 2) * width);
                break;
            case RIGHT:
                area = data.getPoint(1) * width;
                area += (data.getPoint(npts - 1) * width);
                break;
        }
        return area;
    }
    
    @Deprecated
    public static double rectangularMethodRight(double[] inputData) {
        return rectangularMethodRight(inputData, DEFAULT_WIDTH);
    }

    public static double rectangularMethodRight(double[] inputData, double width) {
        /*double rectangularIntegral = 0, area = 0;
        double[] y = new double[202];
        int n;

        n = inputData.length;

        y = generateTestData_v1();

        for (int a = 1; a < n - 1; a++) {
            area = (x[a + 1] - x[a]) * y[a];
            rectangularIntegral += area;
        }

        //System.out.print("The right rectangular method is " + rectangularIntegral + "\n");
        return rectangularIntegral;*/
        int n = inputData.length;
        double area = 0.0;
        for (int a = n-1; a > 0; a--) {
            area += (inputData[a] * width);
        }
        return area;
    }

    public static enum IntegrationSide {

        LEFT, RIGHT
    }

    public static enum IntegrationType {

        //RECTANGULAR, TRAPEZOIDAL, SIMPSONS, BOOLE, UNK
        RECTANGULAR(1),
        TRAPEZOIDAL(2),
        SIMPSONS(3),
        BOOLE(5);
        
        private final int requiredBins;
        IntegrationType(int bins) {
            requiredBins = bins;
        }
        
        public final int binsNeeded() {
            return requiredBins;
        }
    }
    
    @FunctionalInterface
    private static interface IntegrateWindow {
        public abstract double toArea(DataSet data, IntegrationSide side, int lb, int ub);
    }
}
