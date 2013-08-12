package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.stack.Stack;

public class DirectedCycle {

  private boolean[] marked;
  private int[] edgeTo;
  private boolean[] onStack;
  private Stack<Integer> cycle;

  public DirectedCycle(Digraph G) {
    marked = new boolean[G.V()];
    edgeTo = new int[G.V()];
    onStack = new boolean[G.V()];
    for (int v = 0; v < G.V(); v++) {
      if (!marked[v]) dfs(G, v);
    }
  }

  private void dfs(Digraph G, int v) {
    //StdOut.println("v: " + v);
    marked[v] = true;
    onStack[v] = true;
    for (int w : G.adj(v)) {
      //StdOut.println("v: " + v + " w: " + w);

      // At first I thought if don't write this line, it will be a dead loop.
      // That's totally wrong.
      // The program doesn't end as soon as it encounter this.
      // It is just a shortcut so that the rest of the recursion end without ge further.
      if (cycle != null) {
        //StdOut.println("Detect cycle");
        //for (int i : cycle) {
          //StdOut.print(i + " ");
        //}
        //StdOut.println();
        return;
      }

      else if (!marked[w]) {
        edgeTo[w] = v;
        dfs(G, w);
      }

      else if (onStack[w]) {
        StdOut.println("found");
        cycle = new Stack<Integer>();
        for (int x = v; x != w; x = edgeTo[x]) {
          cycle.push(x);
        }
        cycle.push(w);
        cycle.push(v);
      }
    }

    onStack[v] = false;
  }

  public boolean marked(int v) {
    return marked[v];
  }

  public boolean hasCycle() {
    return cycle != null;
  }

  public Iterable<Integer> cycle() {
    return cycle;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);

    DirectedCycle finder = new DirectedCycle(G);
    if (finder.hasCycle()) {
      StdOut.print("Cycle: ");
      for (int v : finder.cycle()) {
        StdOut.print(v + " ");
      }
      StdOut.println();
    } else {
      StdOut.println("No cycle");
    }
  }
}
