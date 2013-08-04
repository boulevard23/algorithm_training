package cse.buffalo.edu.algorithms.datastructure;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {

  private Node first;
  private Node last;
  private int N;

  private class Node {
    Item item;
    Node next;
  }

  public Queue() {
    first = null;
    last  = null;
    N = 0;
  }

  public boolean isEmpty() {
    return first == null;
  }

  public int size() {
    return N;
  }

  public void enqueue(Item item) {
    Node orilast = last;
    last = new Node();
    last.item = item;
    last.next = null;

    if (isEmpty()) {
      first = last;
    } else {
      orilast.next = last;
    }

    N++;
  }

  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Queue underflow");

    Item item = first.item;
    first = first.next;
    N--;

    return item;
  }

  public Iterator<Item> iterator() {
    return new LinkedListQueueIterator();
  }

  private class LinkedListQueueIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() {
      return current != null;
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
    Queue<String> q = new Queue<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (!item.equals("-")) {
        q.enqueue(item);
      } else if (!q.isEmpty()) {
        StdOut.print(q.dequeue() + " ");
      }
    }

    StdOut.println("(" + q.size() + " left on queue)");
  }
}
