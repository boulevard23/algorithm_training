package cse.buffalo.edu.algorithms.datastructure;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayQueue<Item> implements Iterable<Item> {

  private Item[] q;
  private int N = 0;
  private int first = 0;
  private int last = N;

  public ResizingArrayQueue() {
    q = (Item[]) new Object[1];
  }

  private void resize(int max) {
    Item[] temp = (Item[]) new Object[max];

    for (int i = 0; i < N; i++) {
      temp[i] = q[(first + i) % q.length];     // Consider the wrap-around situation
    }

    q = temp;
    first = 0;
    last = N;
  }

  public boolean isEmpty() {
    return N == 0;
  }

  public int size() {
    return N;
  }

  public void enqueue(Item item) {
    if (N == q.length) {
      resize(2 * q.length);
    }

    q[last++] = item;

    if (last == q.length) last++;        // Wrap-around
    N++;
  }

  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException();
    Item item = q[first];
    q[first] = null;                     // Avoid loitering
    N--;
    first++;

    if (first == q.length) first = 0;    // Wrap-around
    if (N > 0 && N == q.length / 4) resize(q.length / 2);
    return item;
  }

  public Iterator<Item> iterator() {
    return new ArrayIterator();
  }

  private class ArrayIterator implements Iterator<Item> {

    private int i = 0;

    public boolean hasNext() {
      return i < N;
    }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();

      Item item = q[(first + i) % q.length];
      i++;
      return item;
    }

    public void remove() {
      // Pass
    }
  }

  public static void main(String[] args) {
    ResizingArrayQueue<String> q = new ResizingArrayQueue<String>();
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
