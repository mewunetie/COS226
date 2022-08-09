/* *****************************************************************************
 *  Name:    Eugenie Choi
 *  NetID:   eyc2
 *  Precept: P03
 *
 *  Partner Name:    Misrach Ewunetie
 *  Partner NetID:   ewunetie
 *  Partner Precept: P10
 *
 *  Description:  Performs BurrowsWheeler transform and inverse transform,
 *                transform outputs the last column of characters and the row
 *                of the original input, inverse transform takes that information
 *                and outputs the original string
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String input = BinaryStdIn.readString();
        CircularSuffixArray cSA = new CircularSuffixArray(input);
        char[] t = new char[input.length()];
        int firstIndex = 0;
        for (int i = 0; i < cSA.length(); i++) {
            if (cSA.index(i) == 0) {
                firstIndex = i;
                t[i] = input.charAt(cSA.length() - 1);
            }
            else {
                t[i] = input.charAt(cSA.index(i) - 1);
            }
        }
        BinaryStdOut.write(firstIndex);
        for (int i = 0; i < t.length; i++) {
            BinaryStdOut.write(t[i]);
        }
        BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String transform = BinaryStdIn.readString();
        int n = transform.length();
        final int R = 256;
        int[] count = new int[R + 1];
        int[] next = new int[n];
        for (int i = 0; i < n; i++)
            count[transform.charAt(i) + 1]++;
        for (int r = 0; r < R; r++)
            count[r + 1] += count[r];
        for (int i = 0; i < n; i++) {
            count[transform.charAt(i)]++;
            char last = transform.charAt(i);
            int index = count[last];
            next[index - 1] = i;
        }
        int current = first;
        for (int i = 0; i < transform.length(); i++) {
            BinaryStdOut.write(transform.charAt(next[current]));
            current = next[current];
        }
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        }
        if (args[0].equals("+")) {
            inverseTransform();
        }
    }

}
