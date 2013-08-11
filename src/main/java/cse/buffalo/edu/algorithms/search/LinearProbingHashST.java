package cse.buffalo.edu.algorithms.search;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.datastructure.queue.Queue;

public class LinearProbingHashST<Key, Value> {

  private static final int INIT_CAPACITY = 4;

  private int N;                   // Number of key-value pairs
  private int M;                   // Number of chains
  private Key[] keys;
  private Value[] vals;

  public LinearProbingHashST() {
    this(INIT_CAPACITY);
  }

  public LinearProbingHashST(int M) {
    this.M = M;
    keys = (Key[]) new Object[M];
    vals = (Value[]) new Object[M];
  }

  private void resize(int size) {
    LinearProbingHashST<Key, Value> tmp = new LinearProbingHashST<Key, Value>(size);
    for (int i = 0; i < M; i++) {
      if (keys[i] != null) {
        tmp.put(keys[i], vals[i]);
      }
    }
    this.M = tmp.M;
    this.keys = tmp.keys;
    this.vals = tmp.vals;
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
    if (N >= M/2) resize(2*M);

    int i;
    for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
      if (key.equals(keys[i])) {
        vals[i] = val;
        return;
      }
    }
    keys[i] = key;
    vals[i] = val;
    N++;
  }

  public Value get(Key key) {
    for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
      if (key.equals(keys[i])) {
        return vals[i];
      }
    }
    return null;
  }

  public void delete(Key key) {
    if (!contains(key)) return;

    int i;
    for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
      if (key.equals(keys[i])) {
        keys[i] = null;
        vals[i] = null;
      }
    }

    // Rehash all keys in same cluster
    // Start from the next position of the deleted item
    i = (i + 1) % M;

    while (keys[i] != null) {
      Key keyToRehash = keys[i];
      Value valToRehash = vals[i];
      keys[i] = null;
      vals[i] = null;
      N--;
      put(keyToRehash, valToRehash);
      i = (i + 1) % M;
    }

    N--;

    if (N > 0 && N <= M/8) resize(M/2);
  }

  public Iterable<Key> keys() {
    Queue<Key> queue = new Queue<Key>();
    for (int i = 0; i < M; i++) {
      if (keys[i] != null) {
        queue.enqueue(keys[i]);
      }
    }
    return queue;
  }

  public static void main(String[] args) {
    LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>(10);
    for (int i = 0; !StdIn.isEmpty(); i++) {
      String key = StdIn.readString();
      st.put(key, i);
    }

    for (String s : st.keys()) {
      StdOut.println(s + " " + st.get(s));
    }
  }
}
