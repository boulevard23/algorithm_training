package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.queue.Queue;
import cse.buffalo.edu.algorithms.graph.EdgeWeightedDigraph;
import cse.buffalo.edu.algorithms.graph.DirectedEdge;
import cse.buffalo.edu.algorithms.datastructure.stack.Stack;
import cse.buffalo.edu.algorithms.sort.IndexMinPQ;

public class DijkstraSP {
  private double[] distTo;
  private DirectedEdge[] edgeTo;
  private IndexMinPQ<Double> pq;

  public DijkstraSP(EdgeWeightedDigraph G, int s) {
    //for (DirectedEdge e : G.edges()) {
      //if (e.weight() < 0) {
        //throw new IllegalArgumentException("edge " + e + " has negative weight");
      //}
    //}

    distTo = new double[G.V()];
    edgeTo = new DirectedEdge[G.V()];
    for (int v = 0; v < G.V(); v++) {
      distTo[v] = Double.POSITIVE_INFINITY;
    }
    distTo[s] = 0.0;

    // Relax vertices in order of distance from s
    pq = new IndexMinPQ<Double>(G.V());
    pq.insert(s, distTo[s]);
    while (!pq.isEmpty()) {
      int v = pq.delMin();
      StdOut.print("delete: " + v + "\n");
      for (DirectedEdge e : G.adj(v)) {
        StdOut.print("relax: " + e.from() + " -> " + e.to() + "\n");
        relax(e);
      }
    }
  }

  private void relax(DirectedEdge e) {
    int v = e.from();
    int w = e.to();
    if (distTo[w] > distTo[v] + e.weight()) {
      distTo[w] = distTo[v] + e.weight();
      edgeTo[w] = e;
      if (pq.contains(w)) {
        StdOut.print("decrease: " + w + "\n");
        pq.decreaseKey(w, distTo[w]);
      } else {
        StdOut.print("insert: " + w + "\n");
        pq.insert(w, distTo[w]);
      }
    }
  }

  public double distTo(int v) {
    return distTo[v];
  }

  public boolean hasPathTo(int v) {
    return distTo[v] < Double.POSITIVE_INFINITY;
  }

  public Iterable<DirectedEdge> pathTo(int v) {
    if (!hasPathTo(v)) return null;
    Stack<DirectedEdge> path = new Stack<DirectedEdge>();
    for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
      path.push(e);
    }
    return path;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
    int s = Integer.parseInt(args[1]);

    DijkstraSP sp = new DijkstraSP(G, s);

    for (int t = 0; t < G.V(); t++) {
      if (sp.hasPathTo(t)) {
        StdOut.printf("%d to %d (%.2f) ", s, t, sp.distTo(t));
        if (sp.hasPathTo(t)) {
          for (DirectedEdge e : sp.pathTo(t)) {
            StdOut.print(e + " ");
          }
        }
        StdOut.println();
      } else {
        StdOut.printf("%d to %d    no path\n", s, t);
      }
    }
  }
}
