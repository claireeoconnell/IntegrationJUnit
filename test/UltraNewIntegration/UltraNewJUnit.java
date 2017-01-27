/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UltraNewIntegration;

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

        double y[] = new double[202];

        for (int i = 0; i < 202; i++) {
            y[i] = j * Math.sin(j * x[i]);
        }

        return y;
    }

    public static double[] generateReverseData(double[] data) {
        double reverseData[] = new double[202];

        for (int i = 0; i < 202; i++) {
            reverseData[201 - i] = data[i];
        }

        return reverseData;
    }

    private double analyticIntegral(int j) {
        double val = 0;
        val = -Math.cos(j) + 1;
        return val;
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
            double trueVal = analyticIntegral(i);
            double[] testData = generateTestData(i);

            calculatedIntegral[0] = trapInputLeft(testData);
            calculatedIntegral[1] = simpsonsLeft(testData) + HalfBinComposite(testData, IntegrationType.SIMPSONS, IntegrationSide.LEFT);
            calculatedIntegral[2] = booleLeft(testData) + HalfBinComposite(testData, IntegrationType.BOOLE, IntegrationSide.LEFT);
            //calculatedIntegral[3] = rectangularMethodLeft(testData);

            calculatedIntegral[3] = trapInputRight(testData);
            calculatedIntegral[4] = simpsonsRight(testData) + HalfBinComposite(testData, IntegrationType.SIMPSONS, IntegrationSide.RIGHT);
            calculatedIntegral[5] = booleRight(testData) + HalfBinComposite(testData, IntegrationType.BOOLE, IntegrationSide.RIGHT);
            //calculatedIntegral[7] = rectangularMethodRight(testData);

            // Flip the data, input to alternate methods.
            /*calculatedIntegral[0] = trapInputRight(testData);
            calculatedIntegral[1] = simpsonsRight(testData)+HalfBinComposite(testData, IntegrationType.SIMPSONS,IntegrationSide.RIGHT);
            calculatedIntegral[2] = booleRight(testData)+HalfBinComposite(testData, IntegrationType.BOOLE, IntegrationSide.RIGHT);
            calculatedIntegral[3] = trapInputLeft(testData);
            calculatedIntegral[4] = simpsonsLeft(testData)+HalfBinComposite(testData, IntegrationType.SIMPSONS, IntegrationSide.LEFT);
            calculatedIntegral[5] = booleLeft(testData)+HalfBinComposite(testData, IntegrationType.BOOLE, IntegrationSide.LEFT);*/
            
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
    }
}
