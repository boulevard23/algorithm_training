package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

public class DirectedEdge implements Comparable<DirectedEdge> {

  private final int v;
  private final int w;
  private final double weight;

  public DirectedEdge(int v, int w, double weight) {
    this.v = v;
    this.w = w;
    this.weight = weight;
  }

  public double weight() {
    return this.weight;
  }

  public int from() {
    return this.v;
  }

  public int to() {
    return this.w;
  }

  public int compareTo(DirectedEdge that) {
    if      (this.weight() < that.weight()) return -1;
    else if (this.weight() > that.weight()) return +1;
    else                                    return  0;
  }

  public String toString() {
    return v + " -> " + w + " " + String.format("%5.2f",  weight);
  }

  public static void main(String[] args) {
    DirectedEdge e = new DirectedEdge(12, 23, 3.14);
    StdOut.println(e);
  }
}
