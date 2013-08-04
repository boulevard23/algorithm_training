package cse.buffalo.edu.algorithms.datastructure;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

/**
 * Union-Find by using weighted quick union method
 *
 * Data files: tinyUF.txt, mediumUF.txt, largeUF.txt
 *
 * Improving the union() operation so that the tree
 * will be more balance now.
 */

public class WeightedQuickUnionUF {

  private int[] id;    // Access to component id
  private int[] sz;    // Store tree size
  private int count;   // Number of components

  public WeightedQuickUnionUF(int N) {
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
    while (p != id[p]) p = id[p];
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
      StdOut.println(p + " " + q);
    }
    StdOut.println(uf.count() + " components");
  }
}
