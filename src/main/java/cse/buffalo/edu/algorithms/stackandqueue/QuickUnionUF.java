package cse.buffalo.edu.algorithms.stackandqueue;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

/**
 * Union-Find by using quick union method
 *
 * Data files: tinyUF.txt, mediumUF.txt, largeUF.txt
 *
 * The find() operation for this algorithm is certainly
 * quick, but it is still not useful for large input,
 * because union() needs to scan through the whole id[]
 * array for each input pair.
 *
 * The time complexity is O(N ^ 2)
 */

public class QuickUnionUF {

  private int[] id;    // Access to component id
  private int count;   // Number of components

  public QuickUnionUF(int N) {
    // Initialize component id array
    count = N;
    id = new int[N];
    for (int i = 0; i < N; i++) {
      id[i] = i;
    }
  }

  public int count() {
    // Number of components.
    return count;
  }

  public boolean isConnected(int p, int q) {
    return find(p) == find(q);
  }

  public int find(int p) {
    // Chase parent pointers until reach root
    while (p != id[p]) p = id[p];
    return p;
  }

  public void union(int p, int q) {
    // Find the roots of p and q
    int i = find(p);
    int j = find(q);

    // p and q are already in the same component.
    if (i == j) return;

    // Set p's root to q's root
    id[i] = j;
    count--;
  }

  public static void main(String[] args) {
    // Solve dynamic connectivity problem
    int N = StdIn.readInt();
    QuickFindUF uf = new QuickFindUF(N);
    while (!StdIn.isEmpty()) {
      int p = StdIn.readInt();
      int q = StdIn.readInt();
      if (uf.isConnected(p, q)) continue;
      uf.union(p, q);
      StdOut.println(p + " " + q);
    }
    StdOut.println(uf.count() + " components");
  }
}
