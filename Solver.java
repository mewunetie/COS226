/* *****************************************************************************
 *  Name:    Eugenie Choi
 *  NetID:   eyc2
 *  Precept: P03
 *
 *  Partner Name:    Misrach Ewunetie
 *  Partner NetID:   ewunetie
 *  Partner Precept: P10
 *
 *  Description:  Creates a Solver object that iterates through the
 *                quickest solution and returns the number of moves
 *                needed to solve a board.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // arraylist containing the boards dequeued for fastest solution
    private final Stack<Board> stack = new Stack<Board>();
    // node for the goal board
    private final Node goalNode;

    private static class Node implements Comparable<Node> {
        // variable containing board
        private Board board;
        // node pointing back to previous/parent node
        private Node previous;
        // number of moves made in current board
        private int moves;

        // compares boards in minPQ and finds the minimum based on manhattan priority
        public int compareTo(Node node) {
            return Integer.compare(this.board.manhattan() + this.moves,
                                   node.board.manhattan() + node.moves);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null || !initial.isSolvable()) {
            throw new IllegalArgumentException();
        }
        // minimum priority queue containing nodes for each board
        MinPQ<Node> minPQ = new MinPQ<Node>();
        Node initialNode = new Node();
        initialNode.board = initial;
        initialNode.moves = 0;
        initialNode.previous = null;
        minPQ.insert(initialNode);
        while (!minPQ.min().board.isGoal()) {
            Node current = minPQ.delMin();
            Node currentNeighbors;
            for (Board b : current.board.neighbors()) {
                currentNeighbors = new Node();
                currentNeighbors.board = b;
                if (current.board.equals(initialNode.board)) {
                    currentNeighbors.previous = current;
                    currentNeighbors.moves = current.moves + 1;
                    minPQ.insert(currentNeighbors);
                }
                else {
                    if (!currentNeighbors.board.equals(current.previous.board)) {
                        currentNeighbors.previous = current;
                        currentNeighbors.moves = current.moves + 1;
                        minPQ.insert(currentNeighbors);
                    }
                }
            }
        }
        goalNode = minPQ.min();
        minPQ.delMin();

    }

    // min number of moves to solve initial board
    public int moves() {
        return goalNode.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Node current = goalNode;
        stack.push(goalNode.board);
        while (current.previous != null) {
            stack.push(current.previous.board);
            current = current.previous;
        }
        return stack;
    }

    // test client (see below)
    public static void main(String[] args) {
        String inputName = args[0];
        In in = new In(inputName);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        if (!board.isSolvable()) {
            StdOut.println("Unsolvable puzzle");
        }
        else {
            Solver solver = new Solver(board);
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board b : solver.solution()) {
                StdOut.println(b.toString());
            }
        }
    }
}

