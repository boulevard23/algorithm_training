package cse.buffalo.edu.algorithms.stackandqueue;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item> {

  private Node first;
  private int N;

  private class Node {
    Item item;
    Node next;
  }

  public Bag() {
    first = null;
    N = 0;
  }

  public boolean isEmpty() {
    return first == null;
  }

  public int size() {
    return N;
  }

  public void add(Item item) {
    Node orifirst = first;
    first = new Node();
    first.item = item;
    first.next = orifirst;
    N++;
  }

  public Iterator<Item> iterator() {
    return new LinkedListBagIterator();
  }

  private class LinkedListBagIterator implements Iterator<Item> {
    Node current = first;

    public boolean hasNext() {
      return first == null;
    }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }

    public void remove() {
      // Pass
    }
  }


  public static void main(String[] args) {
    Bag<String> bag = new Bag<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      bag.add(item);
    }

    StdOut.println("size of bag = " + bag.size());
    for (String s : bag) {
      StdOut.println(s);
    }
  }
}
