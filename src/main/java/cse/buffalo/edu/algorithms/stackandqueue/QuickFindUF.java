package cse.buffalo.edu.algorithms.stackandqueue;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

/**
 * Union-Find by using quick find method
 *
 * Data files: tinyUF.txt, mediumUF.txt, largeUF.txt
 *
 * Quick find is very slow when for large input, because
 * union() needs to scan through the whole id[] array
 * for each input pair.
 */

public class QuickFindUF {

  private int[] id;    // Access to component id
  private int count;   // Number of components

  public QuickFindUF(int N) {
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
    // Identifiy p's component.
    return id[p];
  }

  public void union(int p, int q) {
    // Put p and q into the same component.
    int pID = find(p);
    int qID = find(q);

    // p and q are already in the same component.
    if (isConnected(p, q)) return;

    // Rename p's component to q's name.
    for (int i = 0; i < id.length; i++) {
      if (id[i] == pID) id[i] = qID;
      //StdOut.println("id[" + i + "] = " + id[i]);
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
