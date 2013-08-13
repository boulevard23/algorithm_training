package cse.buffalo.edu.algorithms.graph;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.stdlib.In;
import cse.buffalo.edu.algorithms.datastructure.stack.Stack;
import cse.buffalo.edu.algorithms.datastructure.queue.Queue;

public class DepthFirstOrder {

  private boolean[] marked;
  private int[] pre;                 // pre[v]  = preorder number of v
  private int[] post;                // post[v] = postorder number of v
  private Queue<Integer> preorder;   // Vertices in preorder
  private Queue<Integer> postorder;  // Vertices in postorder
  private int preCounter;            // Counter for preorder numbering
  private int postCounter;           // Counter for postorder numbering

  public DepthFirstOrder(Digraph G) {
    marked = new boolean[G.V()];
    pre = new int[G.V()];
    post = new int[G.V()];
    preorder = new Queue<Integer>();
    postorder = new Queue<Integer>();
    for (int i = 0; i < G.V(); i++) {
      if (!marked[i]) {
        dfs(G, i);
      }
    }
  }

  private void dfs(Digraph G, int v) {
    marked[v] = true;
    pre[v] = preCounter++;
    preorder.enqueue(v);
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        dfs(G, w);
      }
    }
    post[v] = postCounter++;
    postorder.enqueue(v);
  }

  public boolean marked(int v) {
    return marked[v];
  }

  public int pre(int v) {
    return pre[v];
  }

  public int post(int v) {
    return post[v];
  }

  public Iterable<Integer> post() {
    return postorder;
  }

  public Iterable<Integer> pre() {
    return preorder;
  }

  public Iterable<Integer> reversePost() {
    Stack<Integer> reverse = new Stack<Integer>();
    for (int v : postorder) {
      reverse.push(v);
    }
    return reverse;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);

    DepthFirstOrder dfs = new DepthFirstOrder(G);
    StdOut.println("    v pre post");
    StdOut.println("--------------");
    for (int v = 0; v < G.V(); v++) {
      StdOut.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
    }

    StdOut.print("Preorder: ");
    for (int v : dfs.pre()) {
      StdOut.print(v + " ");
    }
    StdOut.println();

    StdOut.print("Postorder: ");
    for (int v : dfs.post()) {
      StdOut.print(v + " ");
    }
    StdOut.println();

    StdOut.print("Reverse postorder: ");
    for (int v : dfs.reversePost()) {
      StdOut.print(v + " ");
    }
    StdOut.println();
  }
}
