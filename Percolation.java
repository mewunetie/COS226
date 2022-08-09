/* *****************************************************************************
 *  Name:    Misrach Ewunetie
 *  NetID:   ewunetie
 *  Precept: P10
 *
 *  Description: This code shows whether an n-by-n grid is connected from top
 * to bottom through a series of unions; it also opens the cells of
 * given indices.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int number; // size "n" for n-by-n grid
    private final int virtualTop; // index for virtual top cells;
    private final int virtualBottom; // index for virtual bottom cells;
    private int openSites; // number of open cells;
    private boolean[][] grid; // n-by-n array that shows which sites are blocked
    private final WeightedQuickUnionUF tree; // weighted quick-union object

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("cannot create grid with this value");
        }
        number = n;
        virtualTop = (number) * (number);
        virtualBottom = (number) * (number) + 1;
        openSites = 0;
        grid = new boolean[number][number];
        tree = new WeightedQuickUnionUF(number * number + 2);
    }

    // throws an illegal argument exception if index is out of range
    private void inBounds(int row, int col) {
        if (!inRange(row, col)) {
            throw new IllegalArgumentException("outside of prescribed range");
        }
    }

    // returns the index of the row and column given
    private int gridIndex(int row, int col) {
        return number * row + col;
    }

    // returns a boolean whether the given index is in the range of the bounds
    private boolean inRange(int row, int col) {
        return (row < number && row >= 0) && (col < number && col >= 0);
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        inBounds(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSites++;
        }
        if (row == 0) {
            tree.union(virtualTop, gridIndex(row, col));
        }
        if (row == number - 1) {
            tree.union(virtualBottom, gridIndex(row, col));
        }
        // creates unions with cells surrounding current cell
        if (inRange(row + 1, col) && isOpen(row + 1, col)) {
            tree.union(gridIndex(row, col), gridIndex(row + 1, col));
        }
        if (inRange(row - 1, col) && isOpen(row - 1, col)) {
            tree.union(gridIndex(row, col), gridIndex(row - 1, col));
        }
        if (inRange(row, col + 1) && isOpen(row, col + 1)) {
            tree.union(gridIndex(row, col), gridIndex(row, col + 1));
        }
        if (inRange(row, col - 1) && isOpen(row, col - 1)) {
            tree.union(gridIndex(row, col), gridIndex(row, col - 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        inBounds(row, col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    // if virtual top cell finds the index, then return true
    public boolean isFull(int row, int col) {
        inBounds(row, col);
        return tree.find(gridIndex(row, col)) == tree.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return tree.find(virtualTop) == tree.find(virtualBottom);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(StdIn.readInt());
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            percolation.open(row, col);
            StdOut.println(percolation.isFull(row, col));
            StdOut.println(percolation.isOpen(row, col));
            StdOut.println(percolation.numberOfOpenSites());
            StdOut.println(percolation.percolates());

        }
    }
}
