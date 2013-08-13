package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.stack.Stack;
import cse.buffalo.edu.algorithms.datastructure.queue.Queue;

public class StrongComponents {

  private boolean[] marked;
  private int[] id;
  private int count = 0;

  public StrongComponents(Digraph G) {
    DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());

    marked = new boolean[G.V()];
    id = new int[G.V()];
    for (int v : dfs.reversePost()) {
      if (!marked[v]) {
        dfs(G, v);
        count++;
      }
    }
  }

  private void dfs(Digraph G, int v) {
    marked[v] = true;
    id[v] = count;
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        dfs(G, w);
      }
    }
  }

  public boolean marked(int v) {
    return marked[v];
  }

  public int count() {
    return count;
  }

  public boolean stronglyConnected(int v, int w) {
    return id[v] == id[w];
  }

  public int id(int v) {
    return id[v];
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);

    StrongComponents sc = new StrongComponents(G);

    int M = sc.count();
    StdOut.println(M + " components");

    Queue<Integer>[] components = (Queue<Integer>[]) new Queue[M];
    for (int i = 0; i < M; i++) {
      components[i] = new Queue<Integer>();
    }

    for (int v = 0; v < G.V(); v++) {
      components[sc.id(v)].enqueue(v);
    }

    for (int i = 0; i < M; i++) {
      for (int v : components[i]) {
        StdOut.print(v + " ");
      }
      StdOut.println();
    }
  }
}
