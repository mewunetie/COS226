/* *****************************************************************************
 *  Name:    Misrach Ewunetie
 *  NetID:   ewunetie
 *  Precept: P10
 *
 *  Partner Name:    Eugenie Choi
 *  Partner NetID:   eyc2
 *  Partner Precept: P03
 *
 * Description:  This code creates a new SeamCarver object based on a picture,
 * and then finds the energy of all of the pixels. After doing this, it is able
 * to find the minimum energy path vertically and horizontally and return those
 * column or row indices in an int array.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.awt.Color;

public class SeamCarver {
    private Picture pictureOne; // new defensive copy for picture
    private double[][] distTo; // array with distTo values for each index
    private int[][] edgeTo; // array containing parent column for minimum distTo

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("argument is null");
        }
        pictureOne = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return pictureOne;
    }

    // width of current picture
    public int width() {
        return pictureOne.width();
    }

    // height of current picture
    public int height() {
        return pictureOne.height();
    }

    // helper method to calculate energy of given pixel using surrounding
    // pixels
    private double energyCalculator(int x, int y, int xOne, int yOne) {
        if (x == -1) x = width() - 1;
        if (y == -1) y = height() - 1;
        if (xOne == width()) xOne = 0;
        if (yOne == height()) yOne = 0;

        Color color = pictureOne.get(x, y);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        Color colorOne = pictureOne.get(xOne, yOne);
        int redOne = colorOne.getRed();
        int greenOne = colorOne.getGreen();
        int blueOne = colorOne.getBlue();

        int redCalc = redOne - red;
        int greenCalc = greenOne - green;
        int blueCalc = blueOne - blue;
        double calculation = Math.pow(redCalc, 2) +
                Math.pow(greenCalc, 2) + Math.pow(blueCalc, 2);
        return calculation;

    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= width() || y >= height()) {
            throw new IllegalArgumentException("outside of prescribed range");
        }
        double xDelta = energyCalculator(x - 1, y, x + 1, y);
        double yDelta = energyCalculator(x, y - 1, x, y + 1);
        double energyCalc = Math.sqrt(xDelta + yDelta);
        return energyCalc;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        Picture transposedPicture = new Picture(height(), width());
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                Color color = pictureOne.get(col, row);
                transposedPicture.set(row, col, color);
            }
        }
        pictureOne = transposedPicture;

        int[] hSeam = findVerticalSeam();

        transposedPicture = new Picture(height(), width());
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                Color color = pictureOne.get(col, row);
                transposedPicture.set(row, col, color);
            }
        }
        pictureOne = transposedPicture;
        return hSeam;

    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        distTo = new double[height()][width()];
        edgeTo = new int[height()][width()];
        seamHelper();
        int[] vSeam = new int[height()];
        double champion = distTo[height() - 1][0];
        int colChampion = 0;

        for (int col = 0; col < width(); col++) {
            if (distTo[height() - 1][col] < champion) {
                champion = distTo[height() - 1][col];
                colChampion = col;

            }
        }

        int prevCol = colChampion;
        int colBefore = prevCol;
        if (height() != 1) {
            for (int row = height() - 1; row >= 0; row--) {
                if (row == height() - 1) {
                    vSeam[height() - 1] = colChampion;
                    colBefore = edgeTo[row][prevCol];
                }
                else {
                    prevCol = edgeTo[row][colBefore];
                    vSeam[row] = colBefore;
                    colBefore = prevCol;
                }
            }
        }
        else {
            vSeam[0] = colChampion;
        }
        return vSeam;
    }


    // helper method to calculate the distTo values for bottom three indices of
    // the parent
    private void seamHelper() {
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                if (row == 0) {
                    distTo[row][col] = energy(col, row);
                }
                else {
                    distTo[row][col] = Double.POSITIVE_INFINITY;
                }
            }
        }

        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                if (row == height() - 1) {
                    return;
                }
                else if (width() == 1) {
                    double i = energy(col, row + 1) + distTo[row][col];
                    distTo[row + 1][col] = i;
                    edgeTo[row + 1][col] = col;
                }
                else if (height() == 1) {
                    return;
                }
                else if (col == 0) {
                    double i = energy(col, row + 1) + distTo[row][col];
                    double j = energy(col + 1, row + 1) + distTo[row][col];
                    if (i < distTo[row + 1][col]) {
                        distTo[row + 1][col] = i;
                        edgeTo[row + 1][col] = col;
                    }
                    if (j < distTo[row + 1][col + 1]) {
                        distTo[row + 1][col + 1] = j;
                        edgeTo[row + 1][col + 1] = col;
                    }
                }
                else if (col == width() - 1) {

                    double i = energy(col - 1, row + 1) + distTo[row][col];
                    double j = energy(col, row + 1) + distTo[row][col];
                    if (i < distTo[row + 1][col - 1]) {
                        distTo[row + 1][col - 1] = i;
                        edgeTo[row + 1][col - 1] = col;
                    }
                    if (j < distTo[row + 1][col]) {
                        distTo[row + 1][col] = j;
                        edgeTo[row + 1][col] = col;
                    }
                }
                else {
                    double i = energy(col - 1, row + 1) + distTo[row][col];
                    double j = energy(col, row + 1) + distTo[row][col];
                    double k = energy(col + 1, row + 1) + distTo[row][col];
                    if (i < distTo[row + 1][col - 1]) {
                        distTo[row + 1][col - 1] = i;
                        edgeTo[row + 1][col - 1] = col;
                    }
                    if (j < distTo[row + 1][col]) {
                        distTo[row + 1][col] = j;
                        edgeTo[row + 1][col] = col;
                    }
                    if (k < distTo[row + 1][col + 1]) {
                        distTo[row + 1][col + 1] = k;
                        edgeTo[row + 1][col + 1] = col;
                    }
                }
            }
        }
    }


    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        Picture transposedPicture = new Picture(height(), width());
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                Color color = pictureOne.get(col, row);
                transposedPicture.set(row, col, color);
            }
        }
        pictureOne = transposedPicture;

        removeVerticalSeam(seam);

        transposedPicture = new Picture(height(), width());
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                Color color = pictureOne.get(col, row);
                transposedPicture.set(row, col, color);
            }
        }
        pictureOne = transposedPicture;


    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        seamChecker(seam);
        if (seam.length != height()) {
            throw new IllegalArgumentException("argument of wrong length");
        }
        if (width() == 1) {
            throw new IllegalArgumentException("width is equal to one");
        }
        Picture newPicture = new Picture(width() - 1, height());
        int i = 0;
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width() - 1; col++) {
                if (col < seam[i] && row == i) {
                    Color color = pictureOne.get(col, row);
                    newPicture.set(col, row, color);
                }
                if (col >= seam[i] && row == i) {
                    Color color = pictureOne.get(col + 1, row);
                    newPicture.set(col, row, color);
                }
            }
            i++;
        }
        pictureOne = newPicture;
    }

    // checks if seam is valid
    private void seamChecker(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("null argument or width/height is one");
        }
        // int previousSeam = seam[0];
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i + 1] - seam[i]) > 1) {
                throw new IllegalArgumentException("not a valid seam");
            }

        }
    }

    //  unit testing (required)
    public static void main(String[] args) {
        Picture picture = SCUtility.randomPicture(8, 1);


        SeamCarver seamCarver = new SeamCarver(picture);
        // seamCarver.energy(4, 10);
        seamCarver.picture().show();
        int[] vSeam = seamCarver.findVerticalSeam();
        int[] hSeam = seamCarver.findHorizontalSeam();

        Stopwatch stopwatch = new Stopwatch();


        StdOut.println(seamCarver.height());
        StdOut.println(seamCarver.width());
        StdOut.println(vSeam.length);
        StdOut.println(hSeam.length);
        seamCarver.removeVerticalSeam(vSeam);
        seamCarver.removeHorizontalSeam(hSeam);


    }

}
