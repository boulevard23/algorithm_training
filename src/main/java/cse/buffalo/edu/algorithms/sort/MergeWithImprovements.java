package cse.buffalo.edu.algorithms.sort;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.sort.Insertion;

/**
 * Top-down merge sort with 2 improvements.
 *
 * Actually there is one more improvement,
 * you can eliminate the copy to the auxiliary
 * array by switching the role of the input
 * and auxiliary array in each recursive call.
 * I didn't implement this.
 *
 */
public class MergeWithImprovements {

  // Since merge sort has too much overhead for tiny subarrays,
  // we make a cutoff to insertion sort for 7 items.
  private static final int CUTOFF = 7;

  // Stably merge a[lo .. mid] with a[mid+1 .. hi] using aux[lo .. hi]
  // a[] and aux[] remains the same size of the original array. What changes is just
  // the lo, mid and hi position!
  private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

    // Copy to aux[]
    for (int i = lo; i <= hi; i++) {
      aux[i] = a[i];
    }

    // Merge back to a[]
    int i = lo, j = mid + 1;
    for (int k = lo; k <= hi; k++) {
      if      (i > mid)              a[k] = aux[j++];
      else if (j > hi)               a[k] = aux[i++];

      // Pay attention to the next line:
      // We need to merge back to a[]
      // So what we compare is aux[], not a[]
      else if (less(aux[i], aux[j])) a[k] = aux[i++];
      else                           a[k] = aux[j++];
    }
  }

  private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {

    // #1 improvement
    // Stop condition for this recursion.
    // This time we add a CUTOFF, when the items in array
    // is less than 7, we will use insertion sort.
    if (hi <= lo + CUTOFF - 1) {
      Insertion.sort(a, lo, hi);
      return;
    }

    int mid = lo + (hi - lo) / 2;
    sort(a, aux, lo, mid);
    sort(a, aux, mid + 1, hi);

    // #2 improvement
    // Before merging, check if the biggest item in the
    // first half <= smallest item in the second half.
    if (!less(a[mid+1], a[mid])) return;
    merge(a, aux, lo, mid, hi);
  }

  public static void sort(Comparable[] a) {
    Comparable[] aux = new Comparable[a.length];
    sort(a, aux, 0, a.length - 1);
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
    MergeWithImprovements.sort(a);
    show(a);
  }
}

