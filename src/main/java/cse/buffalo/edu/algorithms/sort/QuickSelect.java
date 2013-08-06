package cse.buffalo.edu.algorithms.sort;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.StdRandom;

public class QuickSelect {

  public static Comparable select(Comparable[] a, int k) {
    StdRandom.shuffle(a);
    int lo = 0, hi = a.length - 1;

    while (hi > lo) {
      int j = partition(a, lo, hi);

      if              (j < k) lo = j + 1;
      else if (j > k) hi = j - 1;
      else            return a[k];
    }
    return a[k];
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
        if (j == lo) break; // This line is redundant, because when j = lo, a[lo] = a[j]
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
    Comparable b = QuickSelect.select(a, 2);
    show(a);
    StdOut.println("#2: " + b);
  }
}

