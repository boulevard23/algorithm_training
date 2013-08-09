package cse.buffalo.edu.algorithms.search;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import cse.buffalo.edu.algorithms.datastructure.queue.Queue;
import java.util.NoSuchElementException;

public class RedBlackBST<Key extends Comparable<Key>, Value> {

  private static final boolean RED   = true;
  private static final boolean BLACK = false;

  private Node root;

  private class Node {
    private Key key;
    private Value value;
    private Node left, right;
    private int N;
    private boolean color;

    public Node(Key key, Value value, boolean color, int N) {
      this.key   = key;
      this.value = value;
      this.N     = N;
      this.color = color;
    }
  }

  public boolean isRed(Node x) {
    if (x == null) return false;
    return (x.color == RED);
  }

  // This is a very concise, but tricky code.
  public void put(Key key, Value value) {
    root = put(root, key, value);
    // Root color should be black.
    root.color = BLACK;
  }

  private Node put(Node x, Key key, Value value) {
    if (x == null) return new Node(key, value, RED, 1);

    // For 2-3-4 tree, the following line goes here.
    // x.left == RED && x.right == RED simply means x.left, x and
    // x.right are in the same node. This situation is allowed in 2-3-4
    // tree, but should be split every time we encounterd on the way
    // down the tree. The reason is it allows new node insertion, 4-nodes
    // can not insert since it is already full.
    // if (isRed(x.left) && isRed(x.right))     flipColors(x);

    int cmp = key.compareTo(x.key);

    if (cmp == 0)     x.value = value;
    else if (cmp < 0) x.left  = put(x.left, key, value);
    else              x.right = put(x.right, key, value);

    // This is the most tricky part.
    // Because put() is a recursive function call, so all
    // the nodes which might be effected is in the recursive path.
    if (isRed(x.right) && !isRed(x.left))    x = rotateLeft(x);
    if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);

    // For 2-3 tree, the following line goes here.
    // x.left == RED && x.right == RED simply means x.left, x and
    // x.right are in the same node, this situation is a temporary
    // situation which should be eliminated after all the left rotations
    // and right rotations are done.
    if (isRed(x.left) && isRed(x.right))     flipColors(x);
    x.N = 1 + size(x.left) + size(x.right);

    return x;
  }

  public Value get(Key key) {
    Node tmp = root;
    while (tmp != null) {
      int cmp = key.compareTo(tmp.key);
      if (cmp == 0)     return tmp.value;
      else if (cmp < 0) tmp = tmp.left;
      else              tmp = tmp.right;
    }
    return null;
  }

  public void deleteMin() {
    if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");

    // If both children of root are black, set root to red.
    if (!isRed(root.left) && !isRed(root.right)) root.color = RED;

    root = deleteMin(root);
    if (!isEmpty()) root.color = BLACK;
  }

  // Need a little time to understand this.
  private Node deleteMin(Node x) {
    if (x.left == null) {
      return null;
    }

    if (!isRed(x.left) && !isRed(x.left.left)) {
      moveRedLeft(x);
    }

    x = deleteMin(x);

    return balance(x);
  }

  public void deleteMax() {
    if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");

    if (!isRed(root.left) && !isRed(root.right)) root.color = RED;

    root = deleteMax(root);
    if (!isEmpty()) root.color = BLACK;
  }

  private Node deleteMax(Node x) {
    if (isRed(x.left)) {
      x = rotateRight(x);
    }

    if (x.right == null) {
      return null;
    }

    if (!isRed(x.right) && !isRed(x.right.left)) {
      x = moveRedRight(x);
    }

    x.right = deleteMax(x.right);

    return balance(x);
  }

  public void delete(Key key) {
    if (!contains(key)) {
      System.err.println("Symbol table does not contain " + key);
      return;
    }

    if (!isRed(root.left) && !isRed(root.right)) {
      root.color = RED;
    }

    root = delete(root, key);
    if (!isEmpty()) root.color = BLACK;
  }

  private Node delete(Node x, Key key) {
    if (key.compareTo(x.key) < 0) {
      if (!isRed(x.left) && !isRed(x.left.left)) {
        x = moveRedLeft(x);
      }
      x.left = delete(x.left, key);
    } else {
      if (isRed(x.left)) {
        x = rotateRight(x);
      }

      // Equals, delete it.
      if (key.compareTo(x.key) == 0 && (x.right == null)) {
        return null;
      }

      if (!isRed(x.right) && !isRed(x.right.left)) {
        x = moveRedRight(x);
      }

      if (key.compareTo(x.key) == 0) {
        Node t = min(x.right);
        x.key = t.key;
        x.value = t.value;

        x.right = deleteMin(x.right);
      }
      else x.right = delete(x.right, key);
    }

    return balance(x);
  }

  public Key min() {
    if (isEmpty()) return null;
    return min(root).key;
  }

  private Node min(Node x) {
    if (x.left == null) return x;
    else               return min(x.left);
  }

  private Node moveRedLeft(Node x) {
    flipColors(x);
    if (isRed(x.right.left)) {
      x.right = rotateRight(x.right);
      x = rotateLeft(x);
      flipColors(x);
    }

    return x;
  }

  private Node moveRedRight(Node x) {
    flipColors(x);
    if (isRed(x.left.left)) {
      x = rotateRight(x);
      flipColors(x);
    }

    return x;
  }

  private Node balance(Node h) {
    if (isRed(h.right))                      h = rotateLeft(h);
    if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
    if (isRed(h.left) && isRed(h.right))     flipColors(h);

    h.N = 1 + size(h.left) + size(h.right);
    return h;
  }

  public Key max() {
    if (isEmpty()) return null;
    return max(root).key;
  }

  private Node max(Node x) {
    if (x.right == null) return x;
    else               return max(x.right);
  }

  public Value floor(Key key) {
    return floor(root, key).value;
  }

  private Node floor(Node x, Key key) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);

    if (cmp == 0)     return x;
    else if (cmp < 0) return floor(x.left, key);
    else {
      // Find the smallest one in the right section.
      Node t = floor(x.right, key);
      if (t != null)  return t;
      else            return x;
    }
  }

  public Value ceiling(Key key) {
    return ceiling(root, key).value;
  }

  private Node ceiling(Node x, Key key) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);

    if (cmp == 0)     return x;
    else if (cmp > 0) return ceiling(x.right, key);
    else {
      Node t = floor(x.left, key);
      if (t != null)  return t;
      else            return x;
    }
  }

  // Number of elements smaller than the key.
  // It is also a little bit tricky.
  public int rank(Key key) {
    return rank(root, key);
  }

  private int rank(Node x, Key key) {
    if (x == null) return 0;
    int cmp = key.compareTo(x.key);

    if (cmp == 0)     return size(x.left);
    else if (cmp < 0) return rank(x.left, key);
    else              return 1 + size(x.left) + rank(x.right, key);
  }

  // Key of given rank.
  public Key select(int k) {
    if (k < 0) return null;
    if (k >= size()) return null;
    Node x = select(root, k);
    return x.key;
  }

  private Node select(Node x, int k) {
    if (x == null) return null;

    int t = size(x.left);

    if (t == k)     return x;
    else if (t < k) return select(x.left, k);
    // The following is the tricky part.
    else            return select(x.right, k-t-1);
  }

  public int size() {
    return size(root);
  }

  private int size(Node x) {
    if (x == null) return 0;
    else           return x.N;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public boolean contains(Key key) {
    return get(key) != null;
  }

  public Iterable<Key> keys() {
    Queue<Key> q = new Queue<Key>();
    inorder(root, q);
    return q;
  }

  // Ascending order.
  private void inorder(Node x, Queue<Key> q) {
    if (x == null) return;
    inorder(x.left, q);
    q.enqueue(x.key);
    inorder(x.right, q);
  }

  private Node rotateRight(Node h) {
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    x.color = h.color;
    h.color = RED;
    return x;
  }

  private Node rotateLeft(Node h) {
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    x.color = h.color;
    h.color = RED;
    return x;
  }

  private void flipColors(Node h) {
    h.color = !h.color;
    h.left.color = !h.left.color;
    h.right.color = !h.right.color;
  }

  public static void main(String[] args) {
    RedBlackBST<String, Integer> st = new RedBlackBST<String, Integer>();
    for (int i = 0; !StdIn.isEmpty(); i++) {
      String key = StdIn.readString();
      st.put(key, i);
    }

    for (String s : st.keys()) {
      StdOut.println(s + " " + st.get(s));
    }
  }
}
