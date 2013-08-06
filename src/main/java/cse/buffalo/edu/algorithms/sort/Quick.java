package cse.buffalo.edu.algorithms.sort;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.StdRandom;

public class Quick {

  private static void sort(Comparable[] a, int lo, int hi) {
    if (lo >= hi) return;

    // mid here is the right position for a[lo]
    int mid = partition(a, lo, hi);

    // The following 2 sort should not include a[mid]
    // because it is in the right position.
    sort(a, lo, mid - 1);
    sort(a, mid + 1, hi);
  }

  public static void sort(Comparable[] a) {
    StdRandom.shuffle(a);
    sort(a, 0, a.length - 1);
  }

  private static int partition(Comparable[] a, int lo, int hi) {
    // j should be hi + 1
    // because the second nested while use a[--j]
    // the first j we use should be hi if j = hi + 1
    int i = lo, j = hi + 1;

    while (true) {
      while (less(a[++i], a[lo])) {
        if (i == hi) break;
      }

      while (less(a[lo], a[--j])) {
        if (j == lo) break;
      }

      if (i >= j) break;
      exch(a, i, j);
    }

    exch(a, lo, j);
    return j;
  }

  private static boolean less(Comparable v, Comparable w) {
    return (v.compareTo(w) < 0);
  }

  private static void exch(Comparable[] a, int i, int j) {
    Comparable tmp = a[i];
    a[i] = a[j];
    a[j] = tmp;
  }

  private static void show(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      StdOut.println(a[i]);
    }
  }

  public static void main(String[] args) {
    String[] a = StdIn.readStrings();
    Quick.sort(a);
    show(a);
  }
}

