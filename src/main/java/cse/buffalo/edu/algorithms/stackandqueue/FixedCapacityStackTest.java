package cse.buffalo.edu.algorithms.stackandqueue;

import cse.buffalo.edu.algorithms.stdlib.*;

public class FixedCapacityStackTest {
  public static void main(String[] args) {
      FixedCapacityStack<String> s;
      s = new FixedCapacityStack<String>(100);

      while(!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                    s.push(item);
                  } else if (!s.isEmpty()) {
                          StdOut.print(s.pop() +  " ");
                        }
          }

      StdOut.println("(" + s.size() + " left on stack)");
    }
}
