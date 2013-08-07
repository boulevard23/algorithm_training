package cse.buffalo.edu.algorithms.sort;

import java.util.NoSuchElementException;
import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

public class MaxPQ<Key extends Comparable<Key>> {

  private Key[] pq;
  private int N;

  public MaxPQ(int capacity) {
    // Because pq[0] isn't used, we must extend capacity by 1.
    pq = (Key[]) new Comparable[capacity + 1];
    N = 0;
  }

  public MaxPQ() {
    this(1);
  }

  private void resize(int capacity) {
    Key[] tmp = (Key[]) new Comparable[capacity];
    for (int i = 0; i < N; i++) {
      tmp[i] = pq[i];
    }
    pq = tmp;
  }

  public void insert(Key v) {
    if (N >= pq.length - 1) resize(2 * pq.length);
    pq[++N] = v;
    swim(N);
  }

  public Key delMax() {
    if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
    Key max = pq[1];
    exch(1, N--);
    sink(1);
    pq[N+1] = null; // To avoid loitering

    if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length / 2);

    return max;
  }

  public boolean isEmpty() {
    return N == 0;
  }

  public int size() {
    return N;
  }

  private void sink(int k) {
    // Use 2 * k <= N, not k <= N
    // Because if 2 * k > N, it means this k has no child.
    while (2 * k <= N) {
      int bigChild = 2 * k;

      // Pick the bigger one of two children.
      if (less(bigChild, bigChild + 1)) bigChild++;

      // Do nothing if the bigger child is smaller than the parent.
      if (!less(k, bigChild)) break;

      exch(k, bigChild);
      k = bigChild;
    }
  }

  private void swim(int k) {
    while (k > 1) {
      if (less(k/2, k)) exch(k, k/2);
      k = k/2;
    }
  }

  private boolean less(int i, int j) {
    return pq[i].compareTo(pq[j]) < 0;
  }

  private void exch(int i, int j) {
    Key tmp = pq[i];
    pq[i] = pq[j];
    pq[j] = tmp;
  }

  public static void main(String[] args) {
    MaxPQ<String> pq = new MaxPQ<String>(20);
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (!item.equals("-")) pq.insert(item);
      else if (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
    }
    StdOut.println("(" + pq.size() + " left on pq)");
  }
}
