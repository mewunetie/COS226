/* *****************************************************************************
 *  Name:    Eugenie Choi
 *  NetID:   eyc2
 *  Precept: P03
 *
 *  Partner Name:    Misrach Ewunetie
 *  Partner NetID:   ewunetie
 *  Partner Precept: P10
 *
 *  Description:  Creates a ShortestCommonAncestor object that computes the
 *                shortest ancestral path between two vertices and the
 *                nearest common ancestor.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class ShortestCommonAncestor {
    // Defensive copy of digraph containing vertices and edges
    private Digraph digraph;

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("G is null");
        }
        digraph = new Digraph(G);
        DirectedCycle directedCycle = new DirectedCycle(G);
        int counter = 0;
        if (directedCycle.hasCycle()) {
            throw new IllegalArgumentException("G is cyclic");
        }
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) {
                counter++;
            }
            if (counter > 1) {
                throw new IllegalArgumentException("more than 1 root");
            }
        }
    }

    // Computes the shortest ancestral path between two vertices
    private int smallestSum(BreadthFirstDirectedPaths bfdp, BreadthFirstDirectedPaths bfdp1) {
        int vertices = digraph.V();
        int smallestSum = Integer.MAX_VALUE;
        for (int i = 0; i < vertices; i++) {
            if (bfdp.hasPathTo(i) && bfdp1.hasPathTo(i)) {
                int currentSum = bfdp.distTo(i) + bfdp1.distTo(i);
                if (currentSum < smallestSum) {
                    smallestSum = currentSum;
                }
            }
        }
        return smallestSum;
    }

    // Computes the closest ancestor between two vertices
    private int smallestAncestor(BreadthFirstDirectedPaths bfdp, BreadthFirstDirectedPaths bfdp1) {
        int vertices = digraph.V();
        int smallestSum = Integer.MAX_VALUE;
        int smallestAncestor = 0;
        for (int i = 0; i < vertices; i++) {
            if (bfdp.hasPathTo(i) && bfdp1.hasPathTo(i)) {
                int currentSum = bfdp.distTo(i) + bfdp1.distTo(i);
                if (currentSum < smallestSum) {
                    smallestSum = currentSum;
                    smallestAncestor = i;
                }
            }
        }
        return smallestAncestor;
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfdp1 = new BreadthFirstDirectedPaths(digraph, w);
        return smallestSum(bfdp, bfdp1);
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfdp1 = new BreadthFirstDirectedPaths(digraph, w);
        return smallestAncestor(bfdp, bfdp1);
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        BreadthFirstDirectedPaths bfdpMulti;
        bfdpMulti = new BreadthFirstDirectedPaths(digraph, subsetA);
        BreadthFirstDirectedPaths bfdpMulti1;
        bfdpMulti1 = new BreadthFirstDirectedPaths(digraph, subsetB);
        return smallestSum(bfdpMulti, bfdpMulti1);
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        BreadthFirstDirectedPaths bfdpMulti;
        bfdpMulti = new BreadthFirstDirectedPaths(digraph, subsetA);
        BreadthFirstDirectedPaths bfdpMulti1;
        bfdpMulti1 = new BreadthFirstDirectedPaths(digraph, subsetB);
        return smallestAncestor(bfdpMulti, bfdpMulti1);
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
        ArrayList<Integer> subsetA = new ArrayList<Integer>();
        ArrayList<Integer> subsetB = new ArrayList<Integer>();
        subsetA.add(10);
        subsetA.add(16);
        subsetA.add(20);
        subsetB.add(11);
        subsetB.add(36);
        subsetB.add(21);
        int lengthSubset = sca.lengthSubset(subsetA, subsetB);
        int ancestorSubset = sca.ancestorSubset(subsetA, subsetB);
        StdOut.printf("length = %d, ancestor = %d\n", lengthSubset, ancestorSubset);
    }
}
