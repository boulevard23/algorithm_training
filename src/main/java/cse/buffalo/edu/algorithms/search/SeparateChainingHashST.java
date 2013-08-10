package cse.buffalo.edu.algorithms.search;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.datastructure.queue.Queue;

public class SeparateChainingHashST<Key, Value> {

  private static final int INIT_CAPACITY = 4;

  private int N;                   // Number of key-value pairs
  private int M;                   // Number of chains
  private Node[] st; // Array of linked-list symbol tables

  private static class Node {
    private Object key;
    private Object val;
    private Node next;

    public Node(Object key, Object val, Node next) {
      this.key  = key;
      this.val  = val;
      this.next = next;
    }
  }

  public SeparateChainingHashST() {
    this(INIT_CAPACITY);
  }

  public SeparateChainingHashST(int M) {
    st = new Node[M];
    this.M = M;
  }

  public int size() {
    return N;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public boolean contains(Key key) {
    return get(key) != null;
  }

  private int hash(Key key) {
    return (key.hashCode() & 0x7fffffff) % M;
  }

  public void put(Key key, Value val) {
    int i = hash(key);
    for (Node x = st[i]; x != null; x = x.next) {
      if (key.equals(x.key)) {
        x.val = val;
        return;
      }
    }
    st[i] = new Node(key, val, st[i]);
  }

  public Value get(Key key) {
    int i = hash(key);
    for (Node x = st[i]; x != null; x = x.next) {
      if (key.equals(x.key)) {
        return (Value) x.val;
      }
    }
    return null;
  }

  public Iterable<Key> keys() {
    Queue<Key> queue = new Queue<Key>();
    for (int i = 0; i < M; i++) {
      if (st[i] != null) {
        queue.enqueue((Key) st[i].key);
      }
    }
    return queue;
  }

  public static void main(String[] args) {
    SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>(10);
    for (int i = 0; !StdIn.isEmpty(); i++) {
      String key = StdIn.readString();
      st.put(key, i);
    }

    for (String s : st.keys()) {
      StdOut.println(s + " " + st.get(s));
    }
  }
}
