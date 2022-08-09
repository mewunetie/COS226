/* *****************************************************************************
 *  Name:    Eugenie Choi
 *  NetID:   eyc2
 *  Precept: P03
 *
 *  Partner Name:    Misrach Ewunetie
 *  Partner NetID:   ewunetie
 *  Partner Precept: P10
 *
 *  Description:  CircularSuffixArray object that contains CircularSuffix objects,
 *                forms circular suffixes of an input string
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class CircularSuffixArray {
    // String containing input
    private final String s;
    // CircularSuffix array object
    private final CircularSuffix[] suffixArray;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("s is null");
        }
        this.s = s;
        suffixArray = new CircularSuffix[s.length()];
        for (int i = 0; i < s.length(); i++) {
            CircularSuffix circularSuffix = new CircularSuffix(s, i);
            suffixArray[i] = circularSuffix;
        }
        Arrays.sort(suffixArray);
    }

    private class CircularSuffix implements Comparable<CircularSuffix> {
        // String containing input text
        private final String text;
        // int denoting offset of input text (where circular suffix starts)
        private final int offset;
        // length of String text
        private final int length;

        // Circular Suffix object in CircularSuffixArray
        public CircularSuffix(String text, int offset) {
            this.text = text;
            this.offset = offset;
            this.length = text.length();
        }

        // ith character of Circular Suffix object text
        public char charAt(int i) {
            return text.charAt((offset + i) % length);
        }

        // compareTo method for comparing CircularSuffix texts
        public int compareTo(CircularSuffix that) {
            for (int i = 0; i < length(); i++) {
                int last = Character.compare(this.charAt(i), (that.charAt(i)));
                if (last != 0) {
                    return last;
                }
            }
            return 0;
        }
    }

    // length of s
    public int length() {
        return this.s.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > (length() - 1)) {
            throw new IllegalArgumentException("index out of bounds");
        }
        return suffixArray[i].offset;
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray cSA = new CircularSuffixArray("ABA");
        BinaryStdOut.write(cSA.index(0));
        BinaryStdOut.write(cSA.length());
        BinaryStdOut.flush();
    }
}
