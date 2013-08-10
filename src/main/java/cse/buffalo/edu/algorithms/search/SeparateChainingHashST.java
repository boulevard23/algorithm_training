package cse.buffalo.edu.algorithms.search;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.datastructure.queue.Queue;
import cse.buffalo.edu.algorithms.search.SequentialSearchST;

public class SeparateChainingHashST<Key, Value> {

  private static final int INIT_CAPACITY = 4;

  private int N;                   // Number of key-value pairs
  private int M;                   // Number of chains
  private SequentialSearchST<Key, Value>[] st; // Array of linked-list symbol tables

  public SeparateChainingHashST() {
    this(INIT_CAPACITY);
  }

  public SeparateChainingHashST(int M) {
    st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
    for (int i = 0; i < M; i++) {
      st[i] = new SequentialSearchST<Key, Value>();
    }
    this.M = M;
  }

  private void resize(int size) {
    SeparateChainingHashST<Key, Value> tmp = new SeparateChainingHashST<Key, Value>(size);
    for (int i = 0; i < M; i++) {
      for (Key key : st[i].keys()) {
        tmp.put(key, st[i].get(key));
      }
    }
    this.M = tmp.M;
    this.N = tmp.N;
    this.st = tmp.st;
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
    if (val == null) {
      delete(key);
      return;
    }

    // Double table size if average length of list >= 10
    if (N >= 10 * M) resize(2 * M);

    int i = hash(key);
    if (!st[i].contains(key)) N++;
    st[i].put(key, val);
  }

  public Value get(Key key) {
    int i = hash(key);
    return st[i].get(key);
  }

  public void delete(Key key) {
    int i = hash(key);
    if (st[i].contains(key)) N--;
    st[i].delete(key);

    if (M > INIT_CAPACITY && N <= 2*M) resize(M/2);
  }

  public Iterable<Key> keys() {
    Queue<Key> queue = new Queue<Key>();
    for (int i = 0; i < M; i++) {
      for (Key key : st[i].keys()) {
        queue.enqueue(key);
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
