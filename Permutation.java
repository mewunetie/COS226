/* *****************************************************************************
 *  Name:    Misrach Ewunetie
 *  NetID:   ewunetie
 *  Precept: P10
 *
 * Description: This is a client program that reads from a .txt file and
 * enqueues read items. It then is able to print k items from the list randomly.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        // create new RandomizedQueue and take in command-line argument k
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);

        // read from StdIn and enqueue strings
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        // iterate through and dequeue the strings while printing them out k times
        for (int i = 0; i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}
