package cse.buffalo.edu.algorithms.search;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.sort.Quick;

public class BinarySearchRecursive {

  private static int search(Comparable[] a, Comparable target, int lo, int hi) {
    // Find nothing.
    if (lo > hi) return -1;

    //StdOut.println("low: " + lo + " high: " + hi);
    int mid = lo + (hi - lo) / 2;
    if (target.compareTo(a[mid]) < 0)      return search(a, target, lo, mid);
    else if (target.compareTo(a[mid]) > 0) return search(a, target, mid + 1, hi);
    else                                   return mid;
  }

  public static int search(Comparable[] a, Comparable target) {
    return search(a, target, 0, a.length - 1);
  }

  public static void main(String[] args) {
    String[] a = StdIn.readStrings();
    Quick.sort(a);
    StdOut.println("a[6]: " + a[6]);
    StdOut.println(BinarySearchRecursive.search(a, a[6]));
  }
}
