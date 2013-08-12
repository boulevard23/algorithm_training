package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.stack.Stack;

public class Bipartite {

  private boolean isBipartite;
  private boolean[] color;
  private boolean[] marked;
  private int[] edgeTo;
  private Stack<Integer> cycle;

  public Bipartite(Graph G) {
    isBipartite = true;
    color  = new boolean[G.V()];
    marked = new boolean[G.V()];
    edgeTo = new int[G.V()];

    for (int v = 0; v < G.V(); v++) {
      if (!marked[v]) {
        dfs(G, v);
      }
    }
  }

  private void dfs(Graph G, int v) {
    marked[v] = true;
    for (int w : G.adj(v)) {

      // Short circuit if odd-length cycle found
      if (cycle != null) return;

      // Found uncolored vertex, so recur
      if (!marked[w]) {
        edgeTo[w] = v;
        color[w] = !color[v];
        dfs(G, w);
      } else if (color[w] == color[v]) {
        isBipartite = false;
        cycle = new Stack<Integer>();
        cycle.push(w);
        for (int x = v; x != w; x = edgeTo[x]) {
          cycle.push(x);
        }
        cycle.push(w);
      }
    }
  }

  public boolean isBipartite() {
    return isBipartite;
  }

  public boolean color(int v) {
    return color[v];
  }

  public Iterable<Integer> cycle() {
    return cycle;
  }

  public boolean marked(int v) {
    return marked[v];
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Graph G = new Graph(in);
    Bipartite b = new Bipartite(G);
    //StdOut.println(b.isBipartite());
    if (b.isBipartite()) {
      StdOut.println("Graph is bipartite");
      for (int v = 0; v < G.V(); v++) {
        StdOut.println(v + ": " + b.color(v));
      }
    } else {
      StdOut.print("Graph has an odd-length cycle: ");
      for (int x : b.cycle()) {
        StdOut.print(x + " ");
      }
      StdOut.println();
    }
  }
}
