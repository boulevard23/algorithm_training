package cse.buffalo.edu.algorithms.sort;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

public class Selection {
  public static void sort(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      int minPos = i;

      // Find the min element of the right part.
      for (int j = i + 1; j < a.length; j++) {
        if (less(a[j], a[minPos])) minPos = j;
      }
      exch(a, i, minPos);
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
    Selection.sort(a);
    show(a);
  }
}
