/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UltraNewIntegration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ultranewintegration.DataSet;
import ultranewintegration.DoublesDataSet;
import ultranewintegration.FunctionDataCurve;
import ultranewintegration.PolynomialCurve;
import ultranewintegration.SinWave;
import ultranewintegration.CosineWave;

import ultranewintegration.UltraNewIntegration;
import ultranewintegration.UltraNewIntegration.IntegrationSide;
import ultranewintegration.UltraNewIntegration.IntegrationType;

import static ultranewintegration.UltraNewIntegration.IntegrationSide.*;

/**
 * The IntegrationTest is a JUnit test for the Integration program that ensures
 * that the integrals are calculated correctly. This test is run using known
 * integrals calculated with the equation y=10sin(6x)-7cos(5x)+11sin(8x).
 *
 * @author ceoconnell
 */
public class UltraNewJUnit {

    /**
     * Create array with pointers to doubles that will contain known integrals.
     */
    private double[] knownIntegral;
    
    private final static int NUM_INTEGRATION_TYPES = 8; // Left/right, rect/trap/simp/boole.
    private final static double[] x = new double[201];

    static {
        /*x[0] = 0;
        for (int i = 1; i < 201; i++) {
            x[i] = .0025 + .005 * (i - 1);
        }
        x[201] = 1;*/
        for (int i = 0; i < x.length; i++) {
            x[i] = i * 0.005;
        }
    }

    /*
     Set the delta value for the assertEquals comparison
     */
    private final double DELTA = .1;

    /**
     * Initializes the array before testing.
     */
    @Before
    public void setUp() {
        // Instantiate the knownIntegral array.
        knownIntegral = new double[8];

        /*The answers are in the order of the trapezoidal integral first, the
         Simpson's second, Boole's third, and rectangular method last. The 
         integrals are calculated with the bounds 1 and 201 with an interval of 
         .1. The first four are using left hand integrals and the second four
         use right hand integrals.
         */
        /*knownIntegral[0] = 2.9684353512887753;
         knownIntegral[1] = 2.9687126459508564;
         knownIntegral[2] = 2.968712622691869;
         knownIntegral[3] = 2.936215172510247;
        
         knownIntegral[4] = 3.0006927996084642;
         knownIntegral[5] = 3.000977174918476;
         knownIntegral[6] = 3.000977149598709;
         knownIntegral[7] = 2.968898509509485;
         */
    }

    public static double[] generateTestData(int j, boolean flip) {
        double[] data = generateTestData(j);
        double[] y = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            y[i] = data[201 - i];
        }
        return y;
    }

    public static double[] generateTestData(int j) {

        double y[] = new double[201];

        for (int i = 0; i < 202; i++) {
            y[i] = j * Math.sin(j * x[i]);
        }

        return y;
    }

    public static double[] generateReverseData(double[] data) {
        double reverseData[] = new double[201];

        for (int i = 0; i < 201; i++) {
            reverseData[201 - i] = data[i];
        }

        return reverseData;
    }

    private double analyticIntegral(int j) {
        double val = 0;
        val = -Math.cos(j) + 1;
        return val;
    }
    
    @Test
    public void polynomialTest() {
        System.out.println("Testing integration methods on polynomials with known integrals.");
        
        /**
         * This sets up 9 evenly spaced points from 0-1, which should be 0, 0.125,
         * 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, and 1.0. Having very few points
         * of integration should stress the integration methods to the limit...
         * not that it appears to be causing any problems for Simpson's method
         * on the third-order integral.
         * 
         * 9 points also helps because neither Simpson's nor Boole's rule should
         * be truncated and require lower-order integration at an end. If we
         * add Simpson's 3/8 rule, 13 points may be preferable.
         */
        double[] points = UltraNewIntegration.generateXPoints(0.0, 1.0, 9, false);
        
        /**
         * These are the coefficients for polynomials of order 0 to 5, in reverse
         * order; thus the second-order polynomial is x^2 - 4x + 1.5.
         */
        double[] zeroOrder = {1.0}; // f(x) = 1
        double[] firstOrder = {2.0, 1.0}; // f(x) = x+2
        double[] secondOrder = {1.5, -4.0, 1.0}; // f(x) = x^2 - 4x + 1.5
        double[] thirdOrder = {2.0, -1.0, -3.0, 1.0}; // f(x) = x^3 - 3x^2 - x + 2
        double[] fourthOrder = {1, -4.0, -6.0, 4.0, 1.0}; // f(x) = x^4 + 4x^3 - 6x^2 - 4x + 1
        double[] fifthOrder = {2.0, 10.0, -18.0, 8.0, -5.0, 1.0}; // f(x) = x^5 - 5x^4 + 8x^3 - 18x^2 + 10x + 2
        
        /**
         * Hand-calculated integrals from 0-1, to test that the analyticalIntegral
         * function is indeed working for PolynomialCurve.
         */
        double[] trueVals = {1.0, 2.5, (-1.0 / 6.0), 0.75, -1.8, (13.0/6.0)};
        
        // Accumulate the coefficients into a 2-D array.
        double[][] coeffs = {zeroOrder, firstOrder, secondOrder, thirdOrder, fourthOrder, fifthOrder};
        
        /**
         * A bit of streaming/mapping logic here; coeffs[][] is streamed to a
         * Stream of double[]. For each of these double[], a PolynomialCurve
         * is constructed using the points array (points along x), explicitly
         * setting halfWidthBins to false, and then the streamed double[] of
         * coefficients. That transforms (maps) a Stream of double[] to a Stream
         * of PolynomialCurve, which is then collected into a list.
         * 
         * Each PolynomialCurve is:
         * A DataSet, describing a set of points f(x), starting at lb (lower bound),
         * ending at ub (upper bound), with even spacing with the optional exception
         * of half-width bins. The original set of points x[] is not guaranteed
         * to be stored, but can be reconstructed by knowing the number of points,
         * the lower bound, the upper bound, and if it used half-width start/end
         * bins.
         * 
         * A FunctionDataCurve, describing a DataSet generated from some function
         * f(x) which can be analytically integrated; this adds methods for
         * analytical integration and evaluating f(x) at any arbitrary x.
         * 
         * A PolynomialCurve, a concrete class extending FunctionDataCurve, where
         * f(x) is an arbitrary-order polynomial function.
         * 
         * This is very standard inheritance; an interface (DataSet) to describe
         * what you want something to do, an abstract class (FunctionDataCurve)
         * to provide some basic implementations, and, in this case, some 
         * additional methods for that particular subset of its interface,
         * and finally a concrete class (PolynomialCurve) which defines as little
         * as possible.
         * 
         * The whole point of this, then, is that the integration methods can
         * use any arbitrary DataSet, such as a DoublesDataSet taken straight
         * from OSRW, and can be thoroughly tested using any arbitrary 
         * FunctionDataCurve, so that we're confident they are working as intended
         * when applied to DataSets where we don't know the true answer.
         */
        
        /**
         * Create the polynomial curves, using points[] as x, disabling half-width
         * bins, and using each double[] of coefficients in turn. The values of
         * f(x) are automatically generated by the PolynomialCurve constructor.
         */
        List<FunctionDataCurve> polynomials = Arrays.stream(coeffs).map((double[] coeff) -> {
            return new PolynomialCurve(points, false, coeff);
        }).collect(Collectors.toList());
        
        for (int i = 0; i < polynomials.size(); i++) {
            FunctionDataCurve pn = polynomials.get(i);
            
            // Get the true value, and analyticalIntegral over the range.
            double trueVal = trueVals[i];
            double analytical = pn.analyticalIntegral();
            
            double rLeft = UltraNewIntegration.rectangular(pn, LEFT);
            double rRight = UltraNewIntegration.rectangular(pn, RIGHT);
            
            double tLeft = UltraNewIntegration.trapezoidal(pn, LEFT);
            double tRight = UltraNewIntegration.trapezoidal(pn, RIGHT);
            
            double sLeft = UltraNewIntegration.simpsons(pn, LEFT);
            double sRight = UltraNewIntegration.simpsons(pn, RIGHT);
            
            double bLeft = UltraNewIntegration.booles(pn, LEFT);
            double bRight = UltraNewIntegration.booles(pn, RIGHT);
            
            System.out.println(String.format(" Integrals for polynomial of degree %d", i));
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Exact", trueVal, "Analytical", analytical));
            System.out.println(" Numerical integration errors:");
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left rectangular", rLeft - trueVal, "Right rectangular", rRight - trueVal));
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left trapezoidal", tLeft - trueVal, "Right trapezoidal", tRight - trueVal));
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left Simpson's", sLeft - trueVal, "Right Simpson's", sRight - trueVal));
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left Boole's", bLeft - trueVal, "Right Boole's", bRight - trueVal));
            
            assertToUlp(trueVal, 10.0, analytical);
            if (i == 0) {
                assertToUlp(trueVal, 500.0, rLeft, rRight);
            }
            if (i <= 1) {
                assertToUlp(trueVal, 500.0, tLeft, tRight);
            }
            if (i <= 2) {
                assertToUlp(trueVal, 500.0, sLeft, sRight);
            }
            if (i <= 4) {
                assertToUlp(trueVal, 500.0, bLeft, bRight);
            }
            System.out.println();
        }
    }
    
    /**
     * A more difficult test on polynomials, with just five points and larger
     * coefficients
     */
    @Test
    public void polynomialGrinderTest () {
        System.out.println("Testing integration methods on polynomials with large coefficients.");
        double[] points = UltraNewIntegration.generateXPoints(0.0, 2.0, 5, false);
        
        double[] zeroOrder = {-2.0};
        double[] firstOrder = {6.0, -14.0};
        double[] secondOrder = {4.5, -5.0, 3.0};
        double[] thirdOrder = {-3.0, 5.0, -2.0, 12.0};
        double[] fourthOrder = {12, -4.5, 8.0, -5.0, 4.0};
        double[] fifthOrder = {-4.0, 10.0, -18.0, 8.0, -5.0, 8.0};
        
        double[][] coeffs = {zeroOrder, firstOrder, secondOrder, thirdOrder, fourthOrder, fifthOrder};
        
        List<FunctionDataCurve> polynomials = Arrays.stream(coeffs).map((double[] coeff) -> {
            return new PolynomialCurve(points, false, coeff);
        }).collect(Collectors.toList());
        
        for (int i = 0; i < polynomials.size(); i++) {
            FunctionDataCurve pn = polynomials.get(i);
            
            // Get the analyticalIntegral over the range.
            double analytical = pn.analyticalIntegral();
            
            double rLeft = UltraNewIntegration.rectangular(pn, LEFT);
            double rRight = UltraNewIntegration.rectangular(pn, RIGHT);
            
            double tLeft = UltraNewIntegration.trapezoidal(pn, LEFT);
            double tRight = UltraNewIntegration.trapezoidal(pn, RIGHT);
            
            double sLeft = UltraNewIntegration.simpsons(pn, LEFT);
            double sRight = UltraNewIntegration.simpsons(pn, RIGHT);
            
            double bLeft = UltraNewIntegration.booles(pn, LEFT);
            double bRight = UltraNewIntegration.booles(pn, RIGHT);
            
            System.out.println(String.format(" Integrals for polynomial of degree %d", i));
            System.out.println(String.format(" %-18s %9.3g", "Analytical", analytical));
            System.out.println(" Numerical integration errors:");
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left rectangular", rLeft - analytical, "Right rectangular", rRight - analytical));
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left trapezoidal", tLeft - analytical, "Right trapezoidal", tRight - analytical));
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left Simpson's", sLeft - analytical, "Right Simpson's", sRight - analytical));
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left Boole's", bLeft - analytical, "Right Boole's", bRight - analytical));
            
            if (i == 0) {
                assertToUlp(analytical, 500.0, rLeft, rRight);
            }
            if (i <= 1) {
                assertToUlp(analytical, 500.0, tLeft, tRight);
            }
            if (i <= 2) {
                assertToUlp(analytical, 500.0, sLeft, sRight);
            }
            if (i <= 4) {
                assertToUlp(analytical, 500.0, bLeft, bRight);
            }
            System.out.println();
        }
        
    }
    
    @Test
    public void testSinCosine() {
        double[] points = UltraNewIntegration.generateXPoints(0, 1, 201, false);
        boolean verb = false;
        // Test sine with full-width ends.
        System.out.println("\nSin wave test without half-width bins");
        sinTest(points, false, false, verb);
        System.out.println("\nCosine wave test without half-width bins");
        sinTest(points, false, true, verb);
        
        points = UltraNewIntegration.generateXPoints(0, 1, 202, true);
        System.out.println("\nSin wave test with half-width bins");
        sinTest(points, true, false, verb);
        System.out.println("\nCosine wave test with half-width bins");
        sinTest(points, true, true, verb);
        System.out.println("\n");
    }
    
    public void sinTest(double[] points, boolean halvedEnds, boolean cosine, boolean verbose) {
        int failRleft = 0;
        int failRright = 0;
        int failTleft = 0;
        int failTright = 0;
        int failSleft = 0;
        int failSright = 0;
        int failBleft = 0;
        int failBright = 0;
        double maxDelta = 0.05;
        
        for (int j = 1; j <= 500; j++) {
            FunctionDataCurve wave = cosine ? new CosineWave(points, halvedEnds, j, j) : new SinWave(points, halvedEnds, j, j);
            
            // Get the analyticalIntegral over the range.
            double analytical = wave.analyticalIntegral();
            
            double rLeft = UltraNewIntegration.rectangular(wave, LEFT);
            double erLeft = analytical - rLeft;
            double rRight = UltraNewIntegration.rectangular(wave, RIGHT);
            double erRight = analytical - rRight;
            
            double tLeft = UltraNewIntegration.trapezoidal(wave, LEFT);
            double etLeft = analytical - tLeft;
            double tRight = UltraNewIntegration.trapezoidal(wave, RIGHT);
            double etRight = analytical - tRight;
            
            double sLeft = UltraNewIntegration.simpsons(wave, LEFT);
            double esLeft = analytical - sLeft;
            double sRight = UltraNewIntegration.simpsons(wave, RIGHT);
            double esRight = analytical - sRight;
            
            double bLeft = UltraNewIntegration.booles(wave, LEFT);
            double ebLeft = analytical - bLeft;
            double bRight = UltraNewIntegration.booles(wave, RIGHT);
            double ebRight = analytical - bRight;
            if (verbose) {
                System.out.println(String.format(" Integrals for sine wave j*sin(j) of j %d", j));
                System.out.println(String.format(" %-18s %9.3g", "Analytical", analytical));
                System.out.println(" Numerical integration errors:");
                System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left rectangular", erLeft, "Right rectangular", erRight));
                System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left trapezoidal", etLeft, "Right trapezoidal", etRight));
                System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left Simpson's", esLeft, "Right Simpson's", esRight));
                System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left Boole's", ebLeft, "Right Boole's", ebRight));
            
                System.out.println();
            }
            
            if (failRleft == 0 && Math.abs(erLeft) > maxDelta) {
                failRleft = j;
            }
            if (failRright == 0 && Math.abs(erRight) > maxDelta) {
                failRright = j;
            }
            
            if (failTleft == 0 && Math.abs(etLeft) > maxDelta) {
                failTleft = j;
            }
            if (failTright == 0 && Math.abs(etRight) > maxDelta) {
                failTright = j;
            }
            
            if (failSleft == 0 && Math.abs(esLeft) > maxDelta) {
                failSleft = j;
            }
            if (failSright == 0 && Math.abs(esRight) > maxDelta) {
                failSright = j;
            }
            
            if (failBleft == 0 && Math.abs(ebLeft) > maxDelta) {
                failBleft = j;
            }
            if (failBright == 0 && Math.abs(ebRight) > maxDelta) {
                failBright = j;
            }
            
        }
        
        System.out.println(String.format("Error exceeded delta %8.3f at iterations:", maxDelta));
        System.out.println(String.format(" %-18s %9d  %-18s %9d", "Left rectangular", failRleft, "Right rectangular", failRright));
        System.out.println(String.format(" %-18s %9d  %-18s %9d", "Left trapezoidal", failTleft, "Right trapezoidal", failTright));
        System.out.println(String.format(" %-18s %9d  %-18s %9d", "Left Simpson's", failSleft, "Right Simpson's", failSright));
        System.out.println(String.format(" %-18s %9d  %-18s %9d", "Left Boole's", failBleft, "Right Boole's", failBright));
        
        StringBuilder sb = new StringBuilder(" integration failed for ");
        if (halvedEnds) {
            if (cosine) {
                sb.append("cosine wave, with half-width end bins, of frequency ");
            } else {
                sb.append("sine wave, with half-width end bins, of frequency ");
            }
            String midMessage = sb.toString();
            assertTrue(String.format("Rectangular%s%d", midMessage, 12), failRleft > 12 && failRright > 12);
            assertTrue(String.format("Trapezoidal%s%d", midMessage, 100), failTleft > 100 && failTright > 100);
            assertTrue(String.format("Simpson's%s%d", midMessage, 150), failSleft > 150 && failSright > 150);
            assertTrue(String.format("Boole's%s%d", midMessage, 150), failBleft > 150 && failBright > 150);
            
        } else {
            if (cosine) {
                sb.append("cosine wave of frequency ");
            } else {
                sb.append("sine wave of frequency ");
            }
            String midMessage = sb.toString();
            assertTrue(String.format("Rectangular%s%d", midMessage, 12), failRleft > 12 && failRright > 12);
            assertTrue(String.format("Trapezoidal%s%d", midMessage, 100), failTleft > 100 && failTright > 100);
            assertTrue(String.format("Simpson's%s%d", midMessage, 250), failSleft > 250 && failSright > 250);
            assertTrue(String.format("Boole's%s%d", midMessage, 240), failBleft > 240 && failBright > 240);
        }
    }
    
    @Test
    public void castToDoubleSetTest() {
        System.out.println("Testing casting of a function to a DoublesDataSet");
        
        double[] points = UltraNewIntegration.generateXPoints(0.0, 1.0, 11, false);
        
        double[] zeroOrder = {1.0}; // f(x) = 1
        double[] firstOrder = {2.0, 1.0}; // f(x) = x+2
        double[] secondOrder = {1.5, -4.0, 1.0}; // f(x) = x^2 - 4x + 1.5
        double[] thirdOrder = {2.0, -1.0, -3.0, 1.0}; // f(x) = x^3 - 3x^2 - x + 2
        double[] fourthOrder = {1, -4.0, -6.0, 4.0, 1.0}; // f(x) = x^4 + 4x^3 - 6x^2 - 4x + 1
        double[] fifthOrder = {2.0, 10.0, -18.0, 8.0, -5.0, 1.0}; // f(x) = x^5 - 5x^4 + 8x^3 - 18x^2 + 10x + 2
        
        // Accumulate the coefficients into a 2-D array.
        double[][] coeffs = {zeroOrder, firstOrder, secondOrder, thirdOrder, fourthOrder, fifthOrder};
        List<FunctionDataCurve> polynomials = Arrays.stream(coeffs).map((double[] coeff) -> {
            return new PolynomialCurve(points, false, coeff);
        }).collect(Collectors.toList());
        
        for (FunctionDataCurve pn : polynomials) {
            DataSet dpn = new DoublesDataSet(pn);
            
            for (int j = 0; j < NUM_INTEGRATION_TYPES; j++) {
                IntegrationResult rpn = new IntegrationResult(pn, j);
                IntegrationResult rdpn = new IntegrationResult(dpn, j);
                
                double pnVal = rpn.getValue();
                double dpnVal = rdpn.getValue();
                
                String message = String.format(" Casted function:\n%s\nfunction result %9.3g casted result %9.3g difference %9.3g\nintegration method %s", pn.toString(), pnVal, dpnVal, (pnVal - dpnVal), rpn.toString());
                assertToUlp(message, pnVal, 10.0, dpnVal);
            }
        }
        System.out.println("Polynomial casting tests without halved sides complete.");
        
        double[] points2 = UltraNewIntegration.generateXPoints(0.0, 1.0, 12, true);
        polynomials = Arrays.stream(coeffs).map((double[] coeff) -> {
            return new PolynomialCurve(points2, true, coeff);
        }).collect(Collectors.toList());
        
        for (FunctionDataCurve pn : polynomials) {
            DataSet dpn = new DoublesDataSet(pn);
            
            for (int j = 0; j < NUM_INTEGRATION_TYPES; j++) {
                IntegrationResult rpn = new IntegrationResult(pn, j);
                IntegrationResult rdpn = new IntegrationResult(dpn, j);
                
                double pnVal = rpn.getValue();
                double dpnVal = rdpn.getValue();
                
                String message = String.format(" Casted function:\n%s\nfunction result %9.3g casted result %9.3g difference %9.3g\nintegration method %s", pn.toString(), pnVal, dpnVal, (pnVal - dpnVal), rpn.toString());
                assertToUlp(message, pnVal, 10.0, dpnVal);
            }
        }
        System.out.println("Polynomial casting tests with halved sides complete.");
        
        double[] sinePoints = UltraNewIntegration.generateXPoints(0, 4.0, 101, false);
        FunctionDataCurve sinWave = new SinWave(sinePoints, false, 20, 30);
        DataSet dsw = new DoublesDataSet(sinWave);
        
        for (int j = 0; j < NUM_INTEGRATION_TYPES; j++) {
            IntegrationResult rsw = new IntegrationResult(sinWave, j);
            IntegrationResult rdsw = new IntegrationResult(dsw, j);
            
            double swVal = rsw.getValue();
            double dswVal = rdsw.getValue();
            
            String message = String.format(" Casted function:\n%s\nfunction result %9.3g casted result %9.3g difference %9.3g\nintegration method %s", sinWave.toString(), swVal, dswVal, (swVal - dswVal), rsw.toString());
            assertToUlp(message, swVal, 10.0, dswVal);
        }
        System.out.println("Sine-wave casting tests with halved sides complete.");
        
        double[] sinePoints2 = UltraNewIntegration.generateXPoints(0, 4.0, 102, true);
        sinWave = new SinWave(sinePoints2, true, 20, 30);
        dsw = new DoublesDataSet(sinWave);
        
        for (int j = 0; j < NUM_INTEGRATION_TYPES; j++) {
            IntegrationResult rsw = new IntegrationResult(sinWave, j);
            IntegrationResult rdsw = new IntegrationResult(dsw, j);
            
            double swVal = rsw.getValue();
            double dswVal = rdsw.getValue();
            
            String message = String.format(" Casted function:\n%s\nfunction result %9.3g casted result %9.3g difference %9.3g\nintegration method %s", sinWave.toString(), swVal, dswVal, (swVal - dswVal), rsw.toString());
            assertToUlp(message, swVal, 10.0, dswVal);
        }
        System.out.println("Sine-wave casting tests with halved sides complete.");
        System.out.println("Casting tests complete.");
    }
    
    /**
     * Assert that doubles are equal to within a multiplier of ulp (machine precision).
     * @param trueVal
     * @param ulpMult
     * @param values 
     */
    private static void assertToUlp(double trueVal, double ulpMult, double... values) {
        double ulp = Math.ulp(trueVal) * ulpMult;
        for (double val : values) {
            assertEquals(trueVal, val, ulp);
        }
    }
    
    /**
     * Assert that doubles are equal to within a multiplier of ulp (machine precision).
     * @param trueVal
     * @param ulpMult
     * @param values 
     */
    private static void assertToUlp(String message, double trueVal, double ulpMult, double... values) {
        double ulp = Math.ulp(trueVal) * ulpMult;
        for (double val : values) {
            assertEquals(message, trueVal, val, ulp);
        }
    }
    
    @Test
    public void parallelTest() {
        double[] pts = UltraNewIntegration.generateXPoints(0, 2.0, 92, false);
        FunctionDataCurve tcurve = new SinWave(pts, false, 60, 60);
        for (int i = 0; i < NUM_INTEGRATION_TYPES; i++) {
            IntegrationResult seqResult = new IntegrationResult(tcurve, i, false);
            IntegrationResult parResult = new IntegrationResult(tcurve, i, true);
            double seqVal = seqResult.getValue();
            double parVal = parResult.getValue();
            String message = String.format("Parallel value %9.3g did not match sequential value %9.3g, error %9.3g", parVal, seqVal, (parVal - seqVal));
            assertToUlp(message, seqVal, 80.0, parVal);
        }
    }

    /**
     * Compares the calculated integrals with the known values.
     */
    @Test
    public void integrationTest() {

        /**
         * Calculate the integrals using the left hand trapezoidal, Simpson's,
         * and Boole's methods using data generated with the bounds 1 and 201
         * with an interval of .1. The second four are the right handed
         * integrals in the same order.
         */
        double[] calculatedIntegral = new double[6];
        int[] failIter = new int[6];
        /*
         calculatedIntegral[0] = trapInputLeft(generateTestData_v1());
         calculatedIntegral[1] = simpsonsLeft(generateTestData_v1())+HalfBinComposite(generateTestData_v1(),1,"left");
         calculatedIntegral[2] = booleLeft(generateTestData_v1())+HalfBinComposite(generateTestData_v1(),2,"left");
         calculatedIntegral[3] = rectangularMethodLeft(generateTestData_v1());
        
         calculatedIntegral[4] = trapInputRight(generateTestData_v1());
         calculatedIntegral[5] = simpsonsRight(generateTestData_v1())+HalfBinComposite(generateTestData_v1(),1,"right");
         calculatedIntegral[6] = booleRight(generateTestData_v1())+HalfBinComposite(generateTestData_v1(),2,"right");
         calculatedIntegral[7] = rectangularMethodRight(generateTestData_v1());
         */

        // Assert that the known integrals and calculated integrals are the same.
        //for (int i=0;i<500;i++){
        for (int i = 1; i < 500; i++) {
            //double trueVal = analyticIntegral(i);
            //double[] testData = generateTestData(i);

            /*calculatedIntegral[0] = trapInputLeft(testData);
            calculatedIntegral[1] = simpsonsLeft(testData) + HalfBinComposite(testData, IntegrationType.SIMPSONS, IntegrationSide.LEFT);
            calculatedIntegral[2] = booleLeft(testData) + HalfBinComposite(testData, IntegrationType.BOOLE, IntegrationSide.LEFT);
            //calculatedIntegral[3] = rectangularMethodLeft(testData);

            calculatedIntegral[3] = trapInputRight(testData);
            calculatedIntegral[4] = simpsonsRight(testData) + HalfBinComposite(testData, IntegrationType.SIMPSONS, IntegrationSide.RIGHT);
            calculatedIntegral[5] = booleRight(testData) + HalfBinComposite(testData, IntegrationType.BOOLE, IntegrationSide.RIGHT);
            //calculatedIntegral[7] = rectangularMethodRight(testData);
            */
            
            // Flip the data, input to alternate methods.
            /*calculatedIntegral[0] = trapInputRight(testData);
            calculatedIntegral[1] = simpsonsRight(testData)+HalfBinComposite(testData, IntegrationType.SIMPSONS,IntegrationSide.RIGHT);
            calculatedIntegral[2] = booleRight(testData)+HalfBinComposite(testData, IntegrationType.BOOLE, IntegrationSide.RIGHT);
            calculatedIntegral[3] = trapInputLeft(testData);
            calculatedIntegral[4] = simpsonsLeft(testData)+HalfBinComposite(testData, IntegrationType.SIMPSONS, IntegrationSide.LEFT);
            calculatedIntegral[5] = booleLeft(testData)+HalfBinComposite(testData, IntegrationType.BOOLE, IntegrationSide.LEFT);*/
            
            /*
            System.out.print("\nSim number " + i + "\n\n");
            //assertEquals(knownIntegral[i],calculatedIntegral[i], DELTA);
            System.out.println("Analytic integral: " + trueVal);
            System.out.println("lolzupdatez 17");
            for (int j = 0; j < 6; j++) {
                //int mode = j + 1;

                switch (j) {
                    case 0:
                        System.out.print("Left Trapezoidal error ");
                        break;
                    case 1:
                        System.out.print("Left Simpson's error ");
                        break;
                    case 2:
                        System.out.print("Left Boole's error ");
                        break;
                    case 3:
                        System.out.print("Right Trapezoidal error ");
                        break;
                    case 4:
                        System.out.print("Right Simpson's error ");
                        break;
                    case 5:
                        System.out.print("Right Boole's error ");
                        break;

                }
                System.out.print(calculatedIntegral[j] - trueVal + "\n");
                if (failIter[j] == 0 && Math.abs(trueVal - calculatedIntegral[j]) > DELTA) {
                    failIter[j] = i;
                }
                //assertEquals(analyticIntegral(i),calculatedIntegral[j],DELTA);
            }

        }
        for (int i = 0; i < 6; i++) {
            System.out.println(" L/R Trap/Simp/Bool failiter: " + failIter[i]);
        }
        */
        }
    }
    
    /**
     * Intended to be a shorthand way of performing all the standard 
     * integrations, instead of tediously coding in separate tests for all
     * four integration types in both directions; auto-assigns several fields
     * based on the provided index.
     */
    private class IntegrationResult {
        private final IntegrationType type;
        private final IntegrationSide side;
        private final boolean halvedSides;
        private final DataSet data;
        private final double integratedValue;
        private final boolean parallel;
        
        /**
         * Basic constructor, automatically setting direction and integration
         * method based on index with a useful toString.
         * 
         * Even index values produce a left-hand integral, odd a right-hand 
         * integral.
         * 
         * Integration type is as follows, 0-7, after modulo(8)
         * 0-1: Rectangular
         * 2-3: Trapezoidal
         * 4-5: Simpson's rule
         * 6-7: Boole's rule
         * 
         * @param data Data to numerically integrate
         * @param index Automatically set method and direction
         */
        public IntegrationResult(DataSet data, int index) {
            this(data, index, false);
        }
        
        public IntegrationResult(DataSet data, int index, boolean parallel) {
            switch ((index / 2) % 4) {
                case 0:
                    type = IntegrationType.RECTANGULAR;
                    break;
                case 1:
                    type = IntegrationType.TRAPEZOIDAL;
                    break;
                case 2:
                    type = IntegrationType.SIMPSONS;
                    break;
                case 3:
                    type = IntegrationType.BOOLE;
                    break;
                default:
                    throw new IllegalArgumentException(" How did ((index / 2) % 4) ever come out to not be 0-3?");
            }
            side = (index % 2 == 0) ? LEFT : RIGHT;
            halvedSides = data.halfWidthEnds();
            this.data = data;
            this.parallel = parallel;
            
            if (parallel) {
                switch(type) {
                    case RECTANGULAR:
                        integratedValue = UltraNewIntegration.rectangularParallel(data, side);
                        break;
                    case TRAPEZOIDAL:
                        integratedValue = UltraNewIntegration.trapezoidalParallel(data, side);
                        break;
                    case SIMPSONS:
                        integratedValue = UltraNewIntegration.simpsonsParallel(data, side);
                        break;
                    case BOOLE:
                        integratedValue = UltraNewIntegration.boolesParallel(data, side);
                        break;
                    default:
                        throw new IllegalArgumentException(" How did this end up with an integration type not rectangular, trapezoidal, Simpson's, or Boole's?");
                }
                
            } else {
                switch(type) {
                    case RECTANGULAR:
                        integratedValue = UltraNewIntegration.rectangular(data, side);
                        break;
                    case TRAPEZOIDAL:
                        integratedValue = UltraNewIntegration.trapezoidal(data, side);
                        break;
                    case SIMPSONS:
                        integratedValue = UltraNewIntegration.simpsons(data, side);
                        break;
                    case BOOLE:
                        integratedValue = UltraNewIntegration.booles(data, side);
                        break;
                    default:
                        throw new IllegalArgumentException(" How did this end up with an integration type not rectangular, trapezoidal, Simpson's, or Boole's?");
                }
            }
        }
        
        public IntegrationType getType() {
            return type;
        }
        
        public IntegrationSide getSide() {
            return side;
        }
        
        public DataSet getDataSet() {
            return data;
        }
        
        public boolean getHalvedSides() {
            return halvedSides;
        }
        
        public double getValue() {
            return integratedValue;
        }
        
        @Override
        public String toString() {
            /*StringBuilder sb = new StringBuilder(type.toString().toLowerCase());
            sb.append(" ").append(side.toString().toLowerCase());
            sb.append("-side integral, value ").append(String.format("%9.3g", integratedValue));
            sb.append(" from range ");
            double lb = data.lowerBound();
            double ub = data.upperBound();
            double sep = data.binWidth();
            sb.append(String.format("%9.3g to %9.3g with %9.3g point separation", lb, ub, sep));
            if (halvedSides) {
                sb.append(" and half-width ending bins");
            }
            sb.append(".");
            return sb.toString();*/
            StringBuilder sb = new StringBuilder(type.toString().toLowerCase());
            sb.append(", ").append(side.toString().toLowerCase());
            sb.append("-side integral");
            if (halvedSides) {
                sb.append(" with half-width ending bins");
            }
            sb.append(".");
            return sb.toString();
        }
    }
}
