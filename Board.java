/* *****************************************************************************
 *  Name:    Eugenie Choi
 *  NetID:   eyc2
 *  Precept: P03
 *
 *  Partner Name:    Misrach Ewunetie
 *  Partner NetID:   ewunetie
 *  Partner Precept: P10
 *
 *  Description:  Creates a n by n Board object of tiles.
 *                Returns neighbors, manhattan and hamming distances,
 *                and whether it is solvable or not.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    // 2D int array containing tiles in row-major order for a single board
    private int[][] board;
    // length of the array
    private int n;
    // counter for hamming distance
    private int hammingCounter;
    // sum for manhattan distance
    private int manhattanSum;

    // creates a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        int[][] copy = new int[n][n];
        hammingCounter = 0;
        manhattanSum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = tiles[i][j];
                if (copy[i][j] != n * i + j + 1 && copy[i][j] != 0) {
                    hammingCounter++;
                }
                int num = copy[i][j];
                if (num != 0) {
                    int row = ((num - 1) / n);
                    int col = ((num - 1) % n);
                    manhattanSum += Math.abs(row - i) + Math.abs(col - j);
                }
            }
        }
        this.board = copy;
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringBuilder.append(board[i][j]);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        String s = stringBuilder.toString();
        return s;
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        if ((row < 0 || row > n - 1) || (col < 0 || col > n - 1)) {
            throw new IllegalArgumentException("row or col out of bounds");
        }
        return board[row][col];
    }

    // board size n
    public int size() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hammingCounter;
    }

    // sum of Manhattan distance between tiles and goal
    public int manhattan() {
        return manhattanSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (manhattan() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return (Arrays.deepEquals(this.board, that.board)) && (this.n == that.n);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> array = new ArrayList<Board>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    if ((i + 1) <= n - 1) {
                        board[i][j] = board[i + 1][j];
                        board[i + 1][j] = 0;
                        Board downBoard = new Board(board);
                        array.add(downBoard);
                        board[i + 1][j] = board[i][j];
                        board[i][j] = 0;
                    }
                    if ((i - 1) >= 0) {
                        board[i][j] = board[i - 1][j];
                        board[i - 1][j] = 0;
                        Board upBoard = new Board(board);
                        array.add(upBoard);
                        board[i - 1][j] = board[i][j];
                        board[i][j] = 0;
                    }
                    if ((j + 1) <= n - 1) {
                        board[i][j] = board[i][j + 1];
                        board[i][j + 1] = 0;
                        Board rightBoard = new Board(board);
                        array.add(rightBoard);
                        board[i][j + 1] = board[i][j];
                        board[i][j] = 0;
                    }
                    if ((j - 1) >= 0) {
                        board[i][j] = board[i][j - 1];
                        board[i][j - 1] = 0;
                        Board leftBoard = new Board(board);
                        array.add(leftBoard);
                        board[i][j - 1] = board[i][j];
                        board[i][j] = 0;
                    }
                }
            }
        }
        return array;
    }

    // is this board solvable?
    public boolean isSolvable() {
        int inversions = 0;
        int zeroRow = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int row = 0; row < n; row++) {
                    for (int col = 0; col < n; col++) {
                        if (board[i][j] == 0) {
                            zeroRow = i;
                        }
                        if (board[i][j] < board[row][col] && row == i && col < j
                                && board[i][j] != 0) {
                            inversions++;
                        }
                        boolean iLessThanJ = board[i][j] < board[row][col];
                        if (iLessThanJ && (row < i) && board[i][j] != 0) {
                            inversions++;
                        }
                    }
                }
            }
        }
        if (n % 2 == 1) {
            return (inversions % 2 == 0);
        }
        else {
            return (inversions + zeroRow) % 2 == 1;
        }
    }

    // unit testing
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = StdIn.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.print(board.toString());
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        int[][] tilesY = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tilesY[i][j] = tiles[i][j];
            }
        }
        Board boardY = new Board(tilesY);
        boardY.n = board.n;
        StdOut.println(board.equals(boardY));
        StdOut.println(board.neighbors());
        StdOut.println(board.isGoal());
        StdOut.println(board.isSolvable());
        StdOut.println(board.size());
        StdOut.println(board.tileAt(0, 0));
    }
}
