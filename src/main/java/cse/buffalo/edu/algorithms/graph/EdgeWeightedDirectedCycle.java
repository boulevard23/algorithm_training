package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.StdRandom;
import cse.buffalo.edu.algorithms.graph.DirectedEdge;
import cse.buffalo.edu.algorithms.graph.EdgeWeightedDigraph;
import cse.buffalo.edu.algorithms.datastructure.stack.Stack;

public class EdgeWeightedDirectedCycle {
  private boolean[] marked;
  private DirectedEdge[] edgeTo;
  private boolean[] onStack;
  private Stack<DirectedEdge> cycle;

  public EdgeWeightedDirectedCycle(EdgeWeightedDigraph G) {
    marked = new boolean[G.V()];
    onStack = new boolean[G.V()];
    edgeTo = new DirectedEdge[G.V()];
    for (int v = 0; v < G.V(); v++) {
      if (!marked[v]) dfs(G, v);
    }
  }

  private void dfs(EdgeWeightedDigraph G, int v) {
    onStack[v] = true;
    marked[v] = true;
    for (DirectedEdge e : G.adj(v)) {
      int w = e.to();

      if (cycle != null) return;

      else if (!marked[w]) {
        edgeTo[w] = e;
        dfs(G, w);
      }

      else if (onStack[w]) {
        cycle = new Stack<DirectedEdge>();
        while (e.from() != w) {
          cycle.push(e);
          e = edgeTo[e.from()];
        }
        cycle.push(e);
      }
    }

    onStack[v] = false;
  }

  public boolean hasCycle() {
    return cycle != null;
  }

  public Iterable<DirectedEdge> cycle() {
    return cycle;
  }

  public static void main(String[] args) {

    // create random DAG with V vertices and E edges; then add F random edges
    int V = Integer.parseInt(args[0]);
    int E = Integer.parseInt(args[1]);
    int F = Integer.parseInt(args[2]);
    EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
    int[] vertices = new int[V];
    for (int i = 0; i < V; i++) vertices[i] = i;
    StdRandom.shuffle(vertices);
    for (int i = 0; i < E; i++) {
      int v, w;
      do {
        v = StdRandom.uniform(V);
        w = StdRandom.uniform(V);
      } while (v >= w);
      double weight = Math.random();
      G.addEdge(new DirectedEdge(v, w, weight));
    }

    // add F extra edges
    for (int i = 0; i < F; i++) {
      int v = (int) (Math.random() * V);
      int w = (int) (Math.random() * V);
      double weight = Math.random();
      G.addEdge(new DirectedEdge(v, w, weight));
    }

    StdOut.println(G);

    // find a directed cycle
    EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
    if (finder.hasCycle()) {
      StdOut.print("Cycle: ");
      for (DirectedEdge e : finder.cycle()) {
        StdOut.print(e + " ");
      }
      StdOut.println();
    }

    // or give topologial sort
    else {
      StdOut.println("No directed cycle");
    }
  }
}
