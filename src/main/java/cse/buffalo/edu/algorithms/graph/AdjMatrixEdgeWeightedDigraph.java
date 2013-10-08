package cse.buffalo.edu.algorithms.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;
import cse.buffalo.edu.algorithms.graph.DirectedEdge;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

public class AdjMatrixEdgeWeightedDigraph {
  private int V;
  private int E;
  private DirectedEdge[][] adj;

  // Empty graph with V vertices
  public AdjMatrixEdgeWeightedDigraph(int V) {
    if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
    this.V = V;
    this.E = 0;
    this.adj = new DirectedEdge[V][V];
  }

  // Random graph with V vertices and E edges
  public AdjMatrixEdgeWeightedDigraph(int V, int E) {
    this(V);
    if (E < 0) throw new RuntimeException("Number of edges must be nonnegative");
    if (E > V*V) throw new RuntimeException("Too many edges");

    while (this.E != E) {
      int v = (int) (V * Math.random());
      int w = (int) (V * Math.random());
      double weight = Math.round(100 * Math.random()) / 100.0;
      addEdge(new DirectedEdge(v, w, weight));
    }
  }

  public int V() {
    return V;
  }

  public int E() {
    return E;
  }

  public void addEdge(DirectedEdge e) {
    int v = e.from();
    int w = e.to();
    if (adj[v][w] == null) {
      E++;
      adj[v][w] = e;
    }
  }

  public Iterable<DirectedEdge> adj(int v) {
    return new AdjIterator(v);
  }

  private class AdjIterator implements Iterator<DirectedEdge>, Iterable<DirectedEdge> {
    private int v, w = 0;
    public AdjIterator(int v) {
      this.v = v;
    }

    public Iterator<DirectedEdge> iterator() {
      return this;
    }

    public boolean hasNext() {
      while (w < V) {
        if (adj[v][w] != null) return true;
        w++;
      }
      return false;
    }

    public DirectedEdge next() {
      if (hasNext()) {
        return adj[v][w++];
      } else {
        throw new NoSuchElementException();
      }
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  public String toString() {
    String NEWLINE = System.getProperty("line.separator");
    StringBuilder s = new StringBuilder();
    s.append(V + " " + E + NEWLINE);
    for (int v = 0; v < V; v++) {
      s.append(v + ": ");
      for (DirectedEdge e : adj(v)) {
        s.append(e + " ");
      }
      s.append(NEWLINE);
    }
    return s.toString();
  }

  public static void main(String[] args) {
    int V = Integer.parseInt(args[0]);
    int E = Integer.parseInt(args[1]);
    AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(V, E);
    StdOut.println(G);
  }
}
