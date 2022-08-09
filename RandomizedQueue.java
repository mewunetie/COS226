/* *****************************************************************************
 *  Name:    Misrach Ewunetie
 *  NetID:   ewunetie
 *  Precept: P10
 *
 *  Description: This code creates an array to store items which it enqueues.
 *  It is also able to randomly dequeue items from the array and return them.
 *  Finally, it can iterate through the shuffled version of the array.
 *
 * @citation Adapted from https://edstem.org/us/courses/3294/lessons/9115/slides
 * /45556. Accessed 02/15/2021.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a; // array of Items
    private int n; // size tracker for array


    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    // throws
    private void noException() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
    }

    // creates new array with capacity given and copies non-null elements into it
    // initializes original array to new copy
    private void resize(int capacity) {
        Item[] copyOf = (Item[]) new Object[capacity];
        int i = 0;
        for (Item item : a) {
            if (item != null) {
                copyOf[i] = item;
                i++;
            }
        }
        a = copyOf;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == a.length) {
            resize(2 * a.length);
        }
        a[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        noException();

        // shrink size of array if necessary
        if (n > 0 && n <= a.length / 4) resize(a.length / 2);

        int random = StdRandom.uniform(n);
        Item item = a[random];

        // remove item and avoid loitering
        a[random] = a[n - 1];
        a[n - 1] = null;
        n--;

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        noException();
        int i = StdRandom.uniform(n);
        return a[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        // keeps track of items
        private int tracker;
        // creates an Item array with n items
        private final Item[] copy = (Item[]) new Object[n];

        // Randomize the array and iterate over all items
        public RandomArrayIterator() {
            tracker = 0;
            for (int i = 0; i < n; i++) {
                copy[i] = a[i];
            }
            StdRandom.shuffle(copy);
        }


        // returns false if next() would go out of bounds
        public boolean hasNext() {
            return tracker < n;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy[tracker++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();

        StdOut.println(randomQueue.isEmpty());

        StdOut.println(randomQueue.size());

        randomQueue.enqueue("0");
        randomQueue.enqueue("1");
        randomQueue.enqueue("2");
        randomQueue.enqueue("3");
        randomQueue.enqueue("4");
        randomQueue.enqueue("5");

        // iterates through randomQueue
        for (String s : randomQueue) {
            StdOut.println(s);
        }

        StdOut.println(randomQueue.size());

        StdOut.println(randomQueue.dequeue());
        StdOut.println(randomQueue.dequeue());
        StdOut.println(randomQueue.dequeue());
        StdOut.println(randomQueue.dequeue());

        StdOut.println(randomQueue.sample());

        StdOut.println(randomQueue.size());


    }

}
