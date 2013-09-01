package cse.buffalo.edu.algorithms.string;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

public class LSD {

  // W is the digits of the string
  public static void sort(String[] a, int W) {
    int N = a.length;
    int R = 256;
    String[] aux = new String[N];

    for (int d = W - 1; d >= 0; d--) {

      // Compute frequency counts
      int[] count = new int[R+1];
      for (int i = 0; i < N; i++) {
        count[a[i].charAt(d) + 1]++;
      }

      // Compute cumulates
      for (int r = 0; r < R; r++) {
        count[r+1] += count[r];
      }

      // Move data
      for (int i = 0; i < N; i++) {
        aux[count[a[i].charAt(d)]++] = a[i];
      }

      // Copy back
      for (int i = 0; i < N; i++) {
        a[i] = aux[i];
      }
    }
  }

  public static void main(String[] args) {
    String[] a = StdIn.readStrings();
    int N = a.length;

    int W = a[0].length();
    sort(a, W);

    for (int i = 0; i < N; i++) {
      StdOut.println(a[i]);
    }
  }
}
