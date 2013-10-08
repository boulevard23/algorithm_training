package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.graph.AdjMatrixEdgeWeightedDigraph;
import cse.buffalo.edu.algorithms.graph.DirectedEdge;
import cse.buffalo.edu.algorithms.graph.EdgeWeightedDirectedCycle;
import cse.buffalo.edu.algorithms.graph.EdgeWeightedDigraph;
import cse.buffalo.edu.algorithms.datastructure.stack.Stack;

public class FloydWarshall {
  private double[][] distTo;  // distTo[v][w] = length of shortest v->w path
  private DirectedEdge[][] edgeTo; // edgeTo[v][w] = last edge on shorest v->w path

  public FloydWarshall(AdjMatrixEdgeWeightedDigraph G) {
    int V = G.V();
    distTo = new double[V][V];
    edgeTo = new DirectedEdge[V][V];

    // Initialize distances to infinity
    for (int v = 0; v < V; v++) {
      for (int w = 0; w < V; w++) {
        distTo[v][w] = Double.POSITIVE_INFINITY;
      }
    }

    // Initialize distances using edge-weighted digraph's
    for (int v = 0; v < V; v++) {
      for (DirectedEdge e : G.adj(v)) {
        distTo[e.from()][e.to()] = e.weight();
        edgeTo[e.from()][e.to()] = e;
      }

      // In case of self-loops
      if (distTo[v][v] >= 0.0) {
        distTo[v][v] = 0.0;
        edgeTo[v][v] = null;
      }
    }

    // Floyd-Warshall updates
    for (int i = 0; i < V; i++) {
      for (int v = 0; v < V; v++) {
        if (edgeTo[v][i] == null) continue;
        for (int w = 0; w < V; w++) {
          if (distTo[v][w] > distTo[v][i] + distTo[i][w]) {
            distTo[v][w] = distTo[v][i] + distTo[i][w];
            edgeTo[v][w] = edgeTo[i][w];
          }
        }
        if (distTo[v][v] < 0.0) return;  // negative cycle
      }
    }
  }

  public boolean hasNegativeCycle() {
    for (int v = 0; v < distTo.length; v++) {
      if (distTo[v][v] < 0.0) return true;
    }
    return false;
  }

  public Iterable<DirectedEdge> negativeCycle() {
    for (int v = 0; v < distTo.length; v++) {
      if (distTo[v][v] < 0.0) {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int w = 0; w < V; w++) {
          if (edgeTo[v][w] != null) {
            spt.addEdge(edgeTo[v][w]);
          }
        }
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
        return finder.cycle();
      }
    }
    return null;
  }

  public boolean hasPath(int v, int w) {
    return distTo[v][w] < Double.POSITIVE_INFINITY;
  }

  public double dist(int v, int w) {
    return distTo[v][w];
  }

  public Iterable<DirectedEdge> path(int v, int w) {
    if (!hasPath(v, w) || hasNegativeCycle()) return null;
    Stack<DirectedEdge> path = new Stack<DirectedEdge>();
    for (DirectedEdge e = edgeTo[v][w]; e != null; e = edgeTo[v][e.from()]) {
      path.push(e);
    }
    return path;
  }

  public static void main(String[] args) {

    // random graph with V vertices and E edges, parallel edges allowed
    int V = Integer.parseInt(args[0]);
    int E = Integer.parseInt(args[1]);
    AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(V);
    for (int i = 0; i < E; i++) {
      int v = (int) (V * Math.random());
      int w = (int) (V * Math.random());
      double weight = Math.round(100 * (Math.random() - 0.15)) / 100.0;
      if (v == w) G.addEdge(new DirectedEdge(v, w, Math.abs(weight)));
      else        G.addEdge(new DirectedEdge(v, w, weight));
    }

    StdOut.println(G);

    // run Floyd-Warshall algorithm
    FloydWarshall spt = new FloydWarshall(G);

    // print all-pairs shortest path distances
    StdOut.printf("     ");
    for (int v = 0; v < G.V(); v++) {
      StdOut.printf("%6d ", v);
    }
    StdOut.println();
    for (int v = 0; v < G.V(); v++) {
      StdOut.printf("%3d: ", v);
      for (int w = 0; w < G.V(); w++) {
        if (spt.hasPath(v, w)) StdOut.printf("%6.2f ", spt.dist(v, w));
        else                   StdOut.printf("   Inf ");
      }
      StdOut.println();
    }

    // print negative cycle
    if (spt.hasNegativeCycle()) {
      StdOut.println("Negative cost cycle:");
      for (DirectedEdge e : spt.negativeCycle())
        StdOut.println(e);
      StdOut.println();
    }

    // print all-pairs shortest paths
    else {
      for (int v = 0; v < G.V(); v++) {
        for (int w = 0; w < G.V(); w++) {
          if (spt.hasPath(v, w)) {
            StdOut.printf("%d to %d (%5.2f)  ", v, w, spt.dist(v, w));
            for (DirectedEdge e : spt.path(v, w))
              StdOut.print(e + "  ");
            StdOut.println();
          }
          else {
            StdOut.printf("%d to %d          no path\n", v, w);
          }
        }
      }
    }
  }
}
