/* *****************************************************************************
 *  Name:    Misrach Ewunetie
 *  NetID:   ewunetie
 *  Precept: P10
 *
 * Description: This code creates a doubly linked list that is able to add and
 * remove items from both ends of the list. It is then able to iterate over the
 * given items and return them.
 *
 * @citation Adapted from https://edstem.org/us/courses/3294/lessons/9115/slides
 * /45557. Accessed 02/15/2021.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first; // Node to track first node
    private Node<Item> last; // Node to track last node
    private int n; // size of items in deque

    private static class Node<Item> {
        private Item item; // item Object
        private Node<Item> next = null; // Indicates next node
        private Node<Item> previous = null; // Indicates previous node
    }

    // construct an empty deque
    public Deque() {
        first = null;
        n = 0;
    }

    // writes exception if item is null
    private void exception(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null argument");
        }
    }

    // no such argument exception if stack is empty
    private void noSuchArgument() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
    }


    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        exception(item);
        if (first == null) {
            first = new Node<Item>();
            first.item = item;
            last = first;

        }
        else {
            Node<Item> oldFirst = first;
            first = new Node<Item>();
            first.item = item;
            oldFirst.previous = first;
            first.next = oldFirst;
        }

        n++;


        /*
        if (first == null) {
            first = new Node<Item>();
        }
        else {
            first.next = new Node<Item>();
            first.next.previous = first;
            first = first.next
        }
        first.item = item
        */
    }

    // add the item to the back
    public void addLast(Item item) {
        exception(item);
        if (last == null) {
            // null
            last = new Node<Item>();
            last.item = item;
            first = last;

        }
        else {
            Node<Item> oldLast = last;
            last = new Node<Item>();
            last.item = item;
            oldLast.next = last;
            last.previous = oldLast;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        noSuchArgument();
        Item item = first.item;
        if (first.next == null) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.previous = null;
        }
        n--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        noSuchArgument();
        Item item = last.item;
        if (first.next == null) {
            first = null;
            last = null;
        }
        else {
            last = last.previous;
            last.next = null;
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();

    }

    private class LinkedIterator implements Iterator<Item> {

        private Node<Item> tracker; // keeps track of where node is

        // Linked Iterator constructor
        public LinkedIterator() {
            tracker = first;
        }

        public boolean hasNext() {
            return tracker != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item i = tracker.item;
            tracker = tracker.next;
            return i;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        StdOut.println(deque.isEmpty());
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(5);
        deque.addLast(3);
        deque.addLast(4);
        deque.removeFirst();
        deque.removeLast();

        for (int s : deque) {
            StdOut.println(s);
        }

        StdOut.println(deque.size());
        StdOut.println(deque.isEmpty());

    }

}
