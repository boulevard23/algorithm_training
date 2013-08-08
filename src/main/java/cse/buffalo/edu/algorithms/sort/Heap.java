package cse.buffalo.edu.algorithms.sort;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.StdRandom;

/**
 * Bottom-up implementation.
 *
 */
public class Heap {

  public static void sort(Comparable[] a) {
    int N = a.length;
    //StdOut.println("N = " + N);

    // Bottom-up sink
    for (int i = N/2; i >= 1; i--) {
      sink(a, i, N);
    }

    // Exchange the heap max with the last one in the array.
    //int i = 1;
    while (N > 1) {
      exch(a, 1, N--);
      sink(a, 1, N);
      //StdOut.println("N = " + N);
      //StdOut.println("Step " + i++);
      //show(a);
    }
  }

  private static void sink(Comparable[] a, int k, int N) {
    while (2 * k <= N) {
      int bigChild = 2 * k;

      // Pay attention: I spent a lot of time debugging this.
      // bigChild < N is necessary.
      // Because bigChild = 2 * k at first, which means it is always
      // the left child at first.
      // If bigChild == N, it means the right child is overflow,
      // that child is not available any more.
      if (bigChild < N && less(a, bigChild, bigChild + 1)) bigChild++;

      if (!less(a, k, bigChild)) break;

      exch(a, k, bigChild);
      k = bigChild;
    }
  }

  private static boolean less(Comparable[] a, int i, int j) {
    return (a[i - 1].compareTo(a[j - 1]) < 0);
  }

  private static boolean less(Comparable v, Comparable w) {
    return (v.compareTo(w) < 0);
  }

  private static void exch(Comparable[] a, int i, int j) {
    Comparable tmp = a[i-1];
    a[i-1] = a[j-1];
    a[j-1] = tmp;
  }

  private static void show(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      StdOut.println(a[i]);
    }
  }

  public static void main(String[] args) {
    String[] a = StdIn.readStrings();
    Heap.sort(a);
    show(a);
  }
}

