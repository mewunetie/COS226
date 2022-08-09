/* *****************************************************************************
 *  Name:    Eugenie Choi
 *  NetID:   eyc2
 *  Precept: P03
 *
 *  Partner Name:    Misrach Ewunetie
 *  Partner NetID:   ewunetie
 *  Partner Precept: P10
 *
 *  Description:  Calculates an outcast between two subsets and returns it.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    // creates a defensive copy for WordNet
    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = null;
        int greatestDistance = 0;
        for (int i = 0; i < nouns.length; i++) {
            int totalDistance = 0;
            for (int j = 0; j < nouns.length; j++) {
                int currentDistance = wordNet.distance(nouns[i], nouns[j]);
                totalDistance += currentDistance;
            }
            if (totalDistance > greatestDistance) {
                greatestDistance = totalDistance;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
