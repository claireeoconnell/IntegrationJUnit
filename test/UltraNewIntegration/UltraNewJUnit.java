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

import static ultranewintegration.UltraNewIntegration.HalfBinComposite;
import static ultranewintegration.UltraNewIntegration.generateTestData_v1;
import static ultranewintegration.UltraNewIntegration.trapInputLeft;
import static ultranewintegration.UltraNewIntegration.simpsonsLeft;
import static ultranewintegration.UltraNewIntegration.booleLeft;
import static ultranewintegration.UltraNewIntegration.rectangularMethodLeft;
import static ultranewintegration.UltraNewIntegration.trapInputRight;
import static ultranewintegration.UltraNewIntegration.simpsonsRight;
import static ultranewintegration.UltraNewIntegration.booleRight;
import ultranewintegration.UltraNewIntegration.IntegrationSide;
import ultranewintegration.UltraNewIntegration.IntegrationType;
import static ultranewintegration.UltraNewIntegration.rectangularMethodRight;

import static ultranewintegration.UltraNewIntegration.IntegrationSide.*;
import ultranewintegration.UltraNewIntegration;
import ultranewintegration.FunctionDataCurve;
import ultranewintegration.PolynomialCurve;
import ultranewintegration.SinWave;
import ultranewintegration.CosineWave;
import ultranewintegration.CompositeCurve;

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
        double[] points = new double[41]; // Intentionally smaller than ordinary x.
        double length = (1.0 / ((double) points.length - 1));
        for (int i = 0; i < points.length; i++) {
            points[i] = i * length;
        }
        
        double[] zeroOrder = {1.0};
        double[] firstOrder = {2.0, 1.0};
        double[] secondOrder = {1.5, -4.0, 1.0};
        double[] thirdOrder = {2.0, -1.0, -3.0, 1.0};
        double[] fourthOrder = {1, -4.0, -6.0, 4.0, 1.0};
        double[] fifthOrder = {2.0, 10.0, -18.0, 8.0, -5.0, 1.0};
        double[] trueVals = {1.0, 2.5, (-1.0 / 6.0), 0.75, -1.8, (13.0/6.0)};
        
        double[][] coeffs = {zeroOrder, firstOrder, secondOrder, thirdOrder, fourthOrder, fifthOrder};
        List<FunctionDataCurve> polynomials = Arrays.stream(coeffs).map((double[] coeff) -> {
            return new PolynomialCurve(points, false, coeff);
        }).collect(Collectors.toList());
        
        for (int i = 0; i < polynomials.size(); i++) {
            FunctionDataCurve pn = polynomials.get(i);
            
            double trueVal = trueVals[i];
            double analytical = pn.analyticalIntegral();
            
            double rLeft = UltraNewIntegration.rectangular(pn, LEFT);
            double rRight = UltraNewIntegration.rectangular(pn, RIGHT);
            
            double tLeft = UltraNewIntegration.trapezoidal(pn, LEFT);
            double tRight = UltraNewIntegration.trapezoidal(pn, RIGHT);
            
            double sLeft = UltraNewIntegration.simpsons(pn, LEFT);
            double sRight = UltraNewIntegration.simpsons(pn, RIGHT);
            
            System.out.println(String.format(" Integrals for polynomial of degree %d", i));
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Exact", trueVal, "Analytical", analytical));
            System.out.println(" Numerical integration errors:");
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left rectangular", rLeft - trueVal, "Right rectangular", rRight - trueVal));
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left trapezoidal", tLeft - trueVal, "Right trapezoidal", tRight - trueVal));
            System.out.println(String.format(" %-18s %9.3g  %-18s %9.3g", "Left Simpson's", sLeft - trueVal, "Right Simpson's", sRight - trueVal));
            
            if (i == 0) {
                assertToUlp(trueVal, 500.0, rLeft, rRight);
            }
            if (i <= 1) {
                assertToUlp(trueVal, 500.0, tLeft, tRight);
            }
            // Insert code to assert for Simpson's and Boole's.
            // Simpson's should be exact up to order 2, Boole's exact up to order 4.
            System.out.println();
        }
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
}
