package cse.buffalo.edu.algorithms.stackandqueue;

public class LinkedListReverse {

  public static void main(String[] args) {
    Node a = new Node("A");
    Node b = new Node("B");
    Node c = new Node("C");
    Node d = new Node("D");
    Node e = new Node("E");

    a.next = b;
    b.next = c;
    c.next = d;
    d.next = e;

    traverseNodes(a);
    Node head = reverse(a);
    traverseNodes(head);
  }

  public static Node reverse(Node node) {
    if (node == null) {
      return node;
    }

    Node head = node;
    Node prev = null;
    Node oriNext = head.next;

    while(oriNext != null) {
      head.next = prev;
      prev = head;
      head = oriNext;
      oriNext = head.next;
    }

    head.next = prev;
    return head;
  }

  public static void traverseNodes(Node node) {
    for (; node != null; node = node.next) {
      System.out.println(node.name);
    }
  }

  static class Node {
    String name;
    Node next;

    Node(String name) {
      this.name = name;
    }
  }
}
