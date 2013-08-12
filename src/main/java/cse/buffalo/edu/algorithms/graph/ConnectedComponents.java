package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.queue.Queue;

public class ConnectedComponents {

  private boolean[] marked;
  private int[] id;              // id[v] = id of connected component containing v
  private int count = 0;         // Number of vertices connected to s
  private int[] size;            // size[id] = number of vertices in given component

  public ConnectedComponents(Graph G) {
    marked = new boolean[G.V()];
    id = new int[G.V()];
    size = new int[G.V()];
    for (int v = 0; v < G.V(); v++) {
      if (!marked[v]) {
        dfs(G, v);
        count++;
      }
    }
  }

  private void dfs(Graph G, int v) {
    marked[v] = true;
    id[v] = count;
    size[count]++;
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        dfs(G, w);
      }
    }
  }

  public int id(int v) {
    return id[v];
  }

  public int size(int v) {
    return size[id[v]];
  }

  public boolean marked(int v) {
    return marked[v];
  }

  public int count() {
    return count;
  }

  public boolean areConnected(int v, int w) {
    return id(v) == id(w);
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Graph G = new Graph(in);
    ConnectedComponents cc = new ConnectedComponents(G);

    int M = cc.count();
    StdOut.println(M + " components");

    Queue<Integer>[] components = (Queue<Integer>[]) new Queue[M];
    for (int i = 0; i < M; i++) {
      components[i] = new Queue<Integer>();
    }
    for (int v = 0; v < G.V(); v++) {
      components[cc.id(v)].enqueue(v);
    }

    for (int i = 0; i < M; i++) {
      for (int v : components[i]) {
        StdOut.print(v + " ");
      }
      StdOut.println();
    }
  }
}
