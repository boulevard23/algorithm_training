package cse.buffalo.edu.algorithms.stack;

import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;
import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {

  private Node first;
  private int N;

  private class Node {
    Item item;
    Node next;
  }

  public Stack() {
    first = null;
    N = 0;
  }

  public boolean isEmpty() {
    return first == null;
  }

  public int size() {
    return N;
  }

  public void push(Item item) {
    // Add item to top of stack
    Node orifirst = first;
    first = new Node();
    first.item = item;
    first.next = orifirst;
    N++;
  }

  public Item pop() {
    // Remove item from top of stack
    Item item = first.item;
    first = first.next;
    N--;
    return item;
  }

  public Iterator<Item> iterator() {
    return new LinkedListStackIterator();
  }

  private class LinkedListStackIterator implements Iterator<Item> {
    Node head = first;

    public boolean hasNext() {
      return head != null;
    }

    public Item next() {
      Item item = head.item;
      head = head.next;
      return item;
    }

    public void remove() {
      // Pass
    }
  }

  public static void main(String[] args) {
    Stack<String> s = new Stack<String>();
    while(!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (!item.equals("-")) {
        s.push(item);
      } else if (!s.isEmpty()) {
        StdOut.print(s.pop() + " ");
      }
    }
    StdOut.println("(" + s.size() + " left on stack)");
  }
}
