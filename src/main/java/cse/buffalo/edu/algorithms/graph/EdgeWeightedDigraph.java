package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.bag.Bag;
import cse.buffalo.edu.algorithms.datastructure.stack.Stack;
import cse.buffalo.edu.algorithms.graph.DirectedEdge;

public class EdgeWeightedDigraph {

  private final int V;
  private int E;
  private Bag<DirectedEdge>[] adj;

  // Create an empty edge-weighted graph with V vertices.
  public EdgeWeightedDigraph(int V) {
    if (V < 0) throw new IllegalArgumentException("Number of vertices in a Graph must be nonnegative");
    this.V = V;
    this.E = 0;
    adj = (Bag<DirectedEdge>[]) new Bag[V];
    for (int v = 0; v < V; v++) {
      adj[v] = new Bag<DirectedEdge>();
    }
  }

  // Create a random edge-weighted graph with V vertices and E edges.
  public EdgeWeightedDigraph(int V, int E) {
    this(V);
    if (E < 0) throw new IllegalArgumentException("Number of edges in a Graph must be nonnegative");
    for (int i = 0; i < E; i++) {
      int v = (int) (Math.random() * V);
      int w = (int) (Math.random() * V);
      double weight = Math.round(100 * Math.random()) / 100.0;
      DirectedEdge e = new DirectedEdge(v, w, weight);
      addEdge(e);
    }
  }

  // Create a weighted graph from input stream.
  public EdgeWeightedDigraph(In in) {
    this(in.readInt()) ;
    int E = in.readInt();
    for (int i = 0; i < E; i++) {
      int v = in.readInt();
      int w = in.readInt();
      double weight = in.readDouble();
      DirectedEdge e = new DirectedEdge(v, w, weight);
      addEdge(e);
    }
  }

  // Copy constructor.
  public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
    this(G.V());
    this.E = G.E();
    for (int v = 0; v < G.V(); v++) {
      Stack<DirectedEdge> reverse = new Stack<DirectedEdge>();
      for (DirectedEdge e : G.adj[v]) {
        reverse.push(e);
      }
      for (DirectedEdge e : reverse) {
        adj[v].add(e);
      }
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
    adj[v].add(e);
    E++;
  }

  public Iterable<DirectedEdge> adj(int v) {
    return adj[v];
  }

  // Return all edges in this graph as an Interable.
  public Iterable<DirectedEdge> edges() {
    Bag<DirectedEdge> list = new Bag<DirectedEdge>();
    for (int v = 0; v < V; v++) {
      for (DirectedEdge e : adj(v)) {
        list.add(e);
      }
    }
    return list;
  }

  public String toString() {
    String NEWLINE = System.getProperty("line.separator");
    StringBuilder s = new StringBuilder();
    s.append(V + " " + E + NEWLINE);
    for (int v = 0; v < V; v++) {
      s.append(v + ": ");
      for (DirectedEdge e : adj[v]) {
        s.append(e + "  ");
      }
      s.append(NEWLINE);
    }
    return s.toString();
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
    StdOut.println(G);
  }
}
