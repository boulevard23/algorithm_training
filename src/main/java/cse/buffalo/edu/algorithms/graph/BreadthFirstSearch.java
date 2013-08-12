package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.queue.Queue;

public class BreadthFirstSearch {

  private boolean[] marked;
  private int count = 0;         // Number of vertices connected to s

  public BreadthFirstSearch(Graph G, int s) {
    marked = new boolean[G.V()];
    bfs(G, s);
  }

  private void bfs(Graph G, int v) {
    marked[v] = true;
    Queue<Integer> queue = new Queue<Integer>();
    queue.enqueue(v);
    while (!queue.isEmpty()) {
      count++;
      int s = queue.dequeue();
      for (int w : G.adj(s)) {
        if (!marked[w]) {
          queue.enqueue(w);
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
    BreadthFirstSearch search = new BreadthFirstSearch(G, s);
    for (int v = 0; v < G.V(); v++) {
      if (search.marked[v]) {
        StdOut.print(v + " ");
      }
    }

    StdOut.println();
    if (search.count() != G.V()) StdOut.println("NOT connected");
    else                         StdOut.println("connected");
  }
}
