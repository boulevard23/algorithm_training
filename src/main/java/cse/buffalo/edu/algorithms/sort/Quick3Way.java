package cse.buffalo.edu.algorithms.sort;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.StdRandom;
import cse.buffalo.edu.algorithms.sort.Insertion;

public class Quick3Way {

  //private static final int CUTOFF = 10;

  private static void sort(Comparable[] a, int lo, int hi) {
    if (hi - lo <= 0) return;

    int lt = lo, gt = hi, i = lo;

    Comparable v = a[lo];
    while (i <= gt) {
      int cmp = a[i].compareTo(v);
      if (cmp < 0)        exch(a, lt++, i++);
      else if (cmp > 0 )  exch(a, i, gt--);
      else                i++;
    }

    sort(a, lo, lt - 1);
    sort(a, gt + 1, hi);
  }

  public static void sort(Comparable[] a) {
    StdRandom.shuffle(a);
    sort(a, 0, a.length - 1);
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
    Quick3Way.sort(a);
    show(a);
  }
}

