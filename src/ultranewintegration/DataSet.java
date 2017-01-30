/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultranewintegration;

/**
 * A DataSet represents a set of points along a single dimension, and is able
 * to be numerically integrated.
 * @author Jacob M. Litman
 */
public interface DataSet {
    /**
     * Lower bound of the points along x.
     * @return 
     */
    public abstract double lowerBound();
    /**
     * Upper bound of the points along x.
     * @return 
     */
    public abstract double upperBound();
    /**
     * Number of points along x.
     * @return 
     */
    public abstract int numPoints();
    /**
     * Separation between points along x; should be uniform.
     * @return 
     */
    public abstract double binWidth();
    /**
     * Point at index.
     * @param index
     * @return 
     */
    public abstract double getPoint(int index);
    /**
     * Returns copy of the array of points to integrate.
     * @return 
     */
    public abstract double[] getAllPoints();
    /**
     * Does this data set have half-width start/end bins. Intended for OSRW,
     * where the first and last bins are half the regular width.
     * @return 
     */
    public abstract boolean halfWidthEnds();
}
