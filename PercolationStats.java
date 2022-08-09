/* *****************************************************************************
 *  Name:    Misrach Ewunetie
 *  NetID:   ewunetie
 *  Precept: P10
 *
 *  Description:  This code attempts to estimate the percolation threshold by
 * performing trials on percolation objects t times. Random sites are opened and
 * the number of sites it takes to have the object percolate is found and divided
 * by the total number of sites to find one percolation threshold.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // number used in confidence calculations
    private static final double CONFIDENCE = 1.96;
    // number of trials
    private final int t;
    // number of cells open divided by total cells
    private final double[] fractionOpen;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("out of bounds");
        }
        // size of axis for number-by-number grid
        t = trials;
        fractionOpen = new double[t];

        // Runs t trials to check how many cells must be opened before system
        // percolates
        for (int i = 0; i < t; i++) {
            Percolation percolation = new Percolation(n);
            int counter = 0;
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    counter++;
                }
            }
            fractionOpen[i] = counter / Math.pow(n, 2);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractionOpen);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractionOpen);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - ((CONFIDENCE * stddev()) / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + ((CONFIDENCE * stddev()) / Math.sqrt(t));
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean() =  " + stats.mean());
        StdOut.println("stddev() =  " + stats.stddev());
        StdOut.println("confidenceLow() =  " + stats.confidenceLow());
        StdOut.println("confidenceHigh() =  " + stats.confidenceHigh());
        StdOut.println("elapsed time =   " + stopwatch.elapsedTime());

    }

}
