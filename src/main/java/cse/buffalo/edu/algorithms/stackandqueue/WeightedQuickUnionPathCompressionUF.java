package cse.buffalo.edu.algorithms.stackandqueue;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

/**
 * Union-Find by using weighted quick union method
 * and path compression
 *
 * Data files: tinyUF.txt, mediumUF.txt, largeUF.txt
 *
 * Improving the find() operation further so that the
 * every other node in path point to its grandparent.
 * Thereby we can halve the path length!
 */

public class WeightedQuickUnionPathCompressionUF {

  private int[] id;    // Access to component id
  private int[] sz;    // Store tree size
  private int count;   // Number of components

  public WeightedQuickUnionPathCompressionUF(int N) {
    // Initialize component id array
    // Initialize tree size sz array
    count = N;
    id = new int[N];
    sz = new int[N];
    for (int i = 0; i < N; i++) {
      id[i] = i;
      sz[i] = 1;
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
    while (p != id[p]) {
      // Make this node point to its grandparent
      id[p] = id[id[p]];
      p = id[p];
    }
    return p;
  }

  public void union(int p, int q) {
    // Find the roots of p and q
    int i = find(p);
    int j = find(q);

    // p and q are already in the same component.
    if (i == j) return;

    // The one with more children will be the new root.
    if (sz[i] < sz[j]) {
      id[i] = j;
      sz[j] += sz[i];
    } else {
      id[j] = i;
      sz[i] += sz[j];
    }
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
      //StdOut.println(p + " " + q);
    }
    StdOut.println(uf.count() + " components");
  }
}
