package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.stack.Stack;

public class DepthFirstSearchNonRecursive {

  private boolean[] marked;
  private int count = 0;         // Number of vertices connected to s

  public DepthFirstSearchNonRecursive(Graph G, int s) {
    marked = new boolean[G.V()];
    dfs(G, s);
  }

  private void dfs(Graph G, int v) {
    Stack<Integer> stack = new Stack<Integer>();
    stack.push(v);
    marked[v] = true;
    while (!stack.isEmpty()) {
      count++;
      int s = stack.pop();
      for (int w : G.adj(s)) {
        if (!marked[w]) {
          stack.push(w);
          marked[w] = true;
        }
      }
    }
  }

  public boolean marked(int v) {
    return marked[v];
  }

  public int count() {
    return count;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Graph G = new Graph(in);
    int s = Integer.parseInt(args[1]);
    DepthFirstSearchNonRecursive search = new DepthFirstSearchNonRecursive(G, s);
    for (int v = 0; v < G.V(); v++) {
      if (search.marked[v]) {
        StdOut.print(v + " ");
      }
    }

    StdOut.println();
    StdOut.println("Count: " + search.count());
    if (search.count() != G.V()) StdOut.println("NOT connected");
    else                         StdOut.println("connected");
  }
}
