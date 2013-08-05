package cse.buffalo.edu.algorithms.sort;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

public class Bubble {
  public static void sort(Comparable[] a) {
    // Pay attention to the bound situations.
    for (int i = a.length - 1; i >= 0; i--) {
      for (int j = 1; j < a.length; j++) {
        if (less(a[j], a[j - 1])) exch(a, j, j - 1);
      }
    }
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
    Bubble.sort(a);
    show(a);
  }
}
