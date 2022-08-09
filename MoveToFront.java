/* *****************************************************************************
 *  Name:    Eugenie Choi
 *  NetID:   eyc2
 *  Precept: P03
 *
 *  Partner Name:    Misrach Ewunetie
 *  Partner NetID:   ewunetie
 *  Partner Precept: P10
 *
 *  Description:  Performs MoveToFront encoding and decoding using an input of
 *                a series of characters and decoding
 *
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;

public class MoveToFront {
    // number of ASCII characters
    private static final int EXTENDED_ASCII = 256;

    // apply move-to-front encoding, reading from stdin and writing to stdout
    public static void encode() {
        ArrayList<Character> arrayList = new ArrayList<Character>();
        for (char c = 0; c < EXTENDED_ASCII; c++) {
            arrayList.add(c);
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int index = arrayList.indexOf(c);
            BinaryStdOut.write(index, 8);
            arrayList.remove(index);
            arrayList.add(0, c);
        }
        BinaryStdOut.flush();
    }

    // apply move-to-front decoding, reading from stdin and writing to stdout
    public static void decode() {
        ArrayList<Character> arrayList = new ArrayList<Character>();
        for (char c = 0; c < EXTENDED_ASCII; c++) {
            arrayList.add(c);
        }
        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readChar();
            char character = arrayList.get(i);
            BinaryStdOut.write(character);
            arrayList.remove(i);
            arrayList.add(0, character);
        }
        BinaryStdOut.flush();

    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }
        if (args[0].equals("+")) {
            decode();
        }
    }
}
