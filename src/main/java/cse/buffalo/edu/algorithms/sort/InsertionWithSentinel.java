package cse.buffalo.edu.algorithms.sort;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

/**
 * This is for exercise 2.1.24
 */

public class InsertionWithSentinel {
  public static void sort(Comparable[] a) {

    // Find the smallest sentinel and place it to the left
    int minPos = 0;
    for (int i = 1; i < a.length; i++) {
      if (less(a[i], a[minPos])) minPos = i;
    }
    if (minPos != 0) exch(a, 0, minPos);

    // Modify i from 0 to 1, this can avoid index out of bound error
    for (int i = 1; i < a.length; i++) {
      for (int j = i; less(a[j], a[j - 1]); j--) {
        exch(a, j, j - 1);
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
    InsertionWithSentinel.sort(a);
    show(a);
  }
}
