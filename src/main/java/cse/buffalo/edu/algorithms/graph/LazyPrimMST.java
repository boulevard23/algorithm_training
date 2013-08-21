package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.queue.Queue;
import cse.buffalo.edu.algorithms.sort.MinPQ;
import cse.buffalo.edu.algorithms.graph.Edge;
import cse.buffalo.edu.algorithms.graph.EdgeWeightedGraph;
import cse.buffalo.edu.algorithms.datastructure.unionfind.WeightedQuickUnionPathCompressionUF;

public class LazyPrimMST {

  private double weight;
  private Queue<Edge> mst;
  private boolean[] marked;
  private MinPQ<Edge> pq;

  public LazyPrimMST(EdgeWeightedGraph G) {
    mst = new Queue<Edge>();
    pq = new MinPQ<Edge>();
    marked = new boolean[G.V()];

    // Run Prim from all vertices to get a minimum spanning forest.
    for (int v = 0; v < G.V(); v++) {
      if (!marked[v]) prim(G, v);
    }
  }

  private void prim(EdgeWeightedGraph G, int s) {
    scan(G, s);
    while (!pq.isEmpty()) {
      Edge e = pq.delMin();
      int v = e.either();
      int w = e.other(v);
      if (marked[v] && marked[w]) continue;   // Lazy, both v and w already scanned
      mst.enqueue(e);
      weight += e.weight();
      if (!marked[v]) scan(G, v);
      if (!marked[w]) scan(G, w);
    }
  }

  private void scan(EdgeWeightedGraph G, int v) {
    marked[v] = true;
    for (Edge e : G.adj(v)) {
      if (!marked[e.other(v)]) pq.insert(e);
    }
  }

  public double weight() {
    return this.weight;
  }

  public Iterable<Edge> edges() {
    return mst;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    EdgeWeightedGraph G = new EdgeWeightedGraph(in);
    LazyPrimMST mst = new LazyPrimMST(G);
    for (Edge e : mst.edges()) {
      StdOut.println(e);
    }
    StdOut.printf("%.5f\n", mst.weight());
  }
}
