/* *****************************************************************************
 *  Name:    Eugenie Choi
 *  NetID:   eyc2
 *  Precept: P03
 *
 *  Partner Name:    Misrach Ewunetie
 *  Partner NetID:   ewunetie
 *  Partner Precept: P10
 *
 *  Description:  Creates a WordNet object that creates a digraph of nouns in
 *                synsets that point to hypernyms (ancestors) if applicable. Computes
 *                actions such as distances between nouns and the smallest common
 *                ancestor they share.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
    // Hash table with nouns (String) as keys and Queues of Integers for id values
    private final SeparateChainingHashST<String, Queue<Integer>> synsetHash;
    // Hash table with the ids (Integers) as keys
    private final SeparateChainingHashST<Integer, String> synsetHashReverse;
    // SET of nouns (Strings) in the digraph
    private final SET<String> nouns = new SET<String>();
    // ShortestCommonAncestor object that performs length, sca, etc.
    private final ShortestCommonAncestor shortestCommonAncestor;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        synsetHash = new SeparateChainingHashST<String, Queue<Integer>>();
        synsetHashReverse = new SeparateChainingHashST<Integer, String>();
        In synsetIn = new In(synsets);
        int counter = 0;
        while (!synsetIn.isEmpty()) {
            String s = synsetIn.readLine();
            String[] synsetArray = s.split(",");
            String[] synset = synsetArray[1].split(" ");
            int idSynset = Integer.parseInt(synsetArray[0]);
            synsetHashReverse.put(idSynset, synsetArray[1]);
            for (int i = 0; i < synset.length; i++) {
                nouns.add(synset[i]);
                if (synsetHash.contains(synset[i])) {
                    synsetHash.get(synset[i]).enqueue(idSynset);
                }
                else {
                    Queue<Integer> q = new Queue<Integer>();
                    q.enqueue(idSynset);
                    synsetHash.put(synset[i], q);
                }
            }
            counter++;
        }
        Digraph digraph = new Digraph(counter);
        In in = new In(hypernyms);
        while (!in.isEmpty()) {
            String h = in.readLine();
            String[] hypernymArray = h.split(",");
            int idSynset = Integer.parseInt(hypernymArray[0]);
            for (int i = 1; i < hypernymArray.length; i++) {
                int hypernym = Integer.parseInt(hypernymArray[i]);
                digraph.addEdge(idSynset, hypernym);
            }
        }
        shortestCommonAncestor = new ShortestCommonAncestor(digraph);
    }

    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        return nouns;

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns.contains(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        Queue<Integer> idNoun1 = synsetHash.get(noun1);
        Queue<Integer> idNoun2 = synsetHash.get(noun2);
        int sca = shortestCommonAncestor.ancestorSubset(idNoun1, idNoun2);
        return synsetHashReverse.get(sca);
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        return shortestCommonAncestor
                .lengthSubset(synsetHash.get(noun1), synsetHash.get(noun2));
    }

    // unit testing (required)
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        StdOut.println(wordNet.isNoun("g"));
        StdOut.println(wordNet.nouns());
        StdOut.println(wordNet.distance("a", "d"));
        StdOut.println(wordNet.sca("rare_earth", "inheritance_tax"));
    }
}
