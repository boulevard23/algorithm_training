package cse.buffalo.edu.algorithms.sort;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

public class Shell {
  public static void sort(Comparable[] a) {
    int N = a.length;
    int h = 1;
    while (h < N / 3) {
      h = 3 * h + 1;    // 1, 4, 13, 40, 121
    }
    while (h >= 1) {
      // h-sort the array.
      for (int i = h; i < N; i++) {
        // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]...
        for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
          exch(a, j, j-h);
        }
      }
      h = h/3;
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
    Insertion.sort(a);
    show(a);
  }
}
