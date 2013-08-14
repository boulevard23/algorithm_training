package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.queue.Queue;
import cse.buffalo.edu.algorithms.sort.MinPQ;
import cse.buffalo.edu.algorithms.graph.Edge;
import cse.buffalo.edu.algorithms.graph.EdgeWeightedGraph;
import cse.buffalo.edu.algorithms.datastructure.unionfind.WeightedQuickUnionPathCompressionUF;

public class KruskalMST {

  private double weight;
  private Queue<Edge> mst = new Queue<Edge>();

  public KruskalMST(EdgeWeightedGraph G) {
    MinPQ<Edge> pq = new MinPQ<Edge>();
    for (Edge e : G.edges()) {
      pq.insert(e);
    }

    WeightedQuickUnionPathCompressionUF uf = new WeightedQuickUnionPathCompressionUF(G.V());
    while (!pq.isEmpty() && mst.size() < G.V() - 1) {
      Edge e = pq.delMin();
      int v = e.either();
      int w = e.other(v);
      if (!uf.isConnected(v, w)) {
        uf.union(v, w);
        mst.enqueue(e);
        weight += e.weight();
      }
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
    KruskalMST mst = new KruskalMST(G);
    for (Edge e : mst.edges()) {
      StdOut.println(e);
    }
    StdOut.printf("%.5f\n", mst.weight());
  }
}
