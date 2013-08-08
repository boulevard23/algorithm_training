package cse.buffalo.edu.algorithms.search;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.sort.Quick;

/**
 * Non-recursive version
 *
 */
public class BinarySearch {

  private static int search(Comparable[] a, Comparable target, int lo, int hi) {
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2;
      if (target.compareTo(a[mid]) < 0)      hi = mid;
      else if (target.compareTo(a[mid]) > 0) lo = mid + 1;
      else                                   return mid;
    }

    return -1;
  }

  public static int search(Comparable[] a, Comparable target) {
    return search(a, target, 0, a.length - 1);
  }

  public static void main(String[] args) {
    String[] a = StdIn.readStrings();
    Quick.sort(a);
    StdOut.println("a[6]: " + a[6]);
    StdOut.println(BinarySearch.search(a, a[6]));
  }
}
