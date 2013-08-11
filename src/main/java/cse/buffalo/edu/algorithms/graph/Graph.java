package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.bag.Bag;
import cse.buffalo.edu.algorithms.datastructure.stack.Stack;

public class Graph {

  private final int V;         // Number of vertices
  private int E;               // Number of edges
  private Bag<Integer>[] adj;

  // Create an empty graph with V vertices.
  public Graph(int V) {
    if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonegative");
    this.V = V;
    this.E = 0;
    adj = (Bag<Integer>[]) new Bag[V];
    for (int i = 0; i < V; i++) {
      adj[i] = new Bag<Integer>();
    }
  }

  // Create a random graph with V vertices and E edges.
  public Graph(int V, int E) {
    this(V);
    if (E < 0) throw new IllegalArgumentException("Number of edges must be nonegative");
    for (int i = 0; i < E; i++) {
      int v = (int) (Math.random() * V);
      int w = (int) (Math.random() * V);
      addEdge(v, w);
    }
  }

  // Create a digraph from input stream.
  public Graph(In in) {
    this(in.readInt());
    int E = in.readInt();
    for (int i = 0; i < E; i++) {
      int v = in.readInt();
      int w = in.readInt();
      addEdge(v, w);
    }
  }

  // Copy constructor.
  public Graph(Graph G) {
    this(G.V());
    this.E = G.E();
    for (int v = 0; v < G.V(); v++) {
      // Reverse so that adjacency list is in same order as original
      Stack<Integer> reverse = new Stack<Integer>();
      for (int w : G.adj[v]) {
        reverse.push(w);
      }
      for (int w : reverse) {
        adj[v].add(w);
      }
    }
  }

  public int V() {
    return V;
  }

  public int E() {
    return E;
  }

  public void addEdge(int v, int w) {
    if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
    if (w < 0 || w >= V) throw new IndexOutOfBoundsException();
    E++;
    adj[v].add(w);
    adj[w].add(v);
  }

  public Iterable<Integer> adj(int v) {
    if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
    return adj[v];
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    String NEWLINE = System.getProperty("line.separator");
    s.append(V + " vertices, " + E + " edges " + NEWLINE);
    for (int v = 0; v < V; v++) {
      s.append(v + ": ");
      for (int w : adj[v]) {
        s.append(w + " ");
      }
      s.append(NEWLINE);
    }
    return s.toString();
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Graph G = new Graph(in);
    StdOut.println(G);
  }
}
