package cse.buffalo.edu.algorithms.datastructure;

import cse.buffalo.edu.algorithms.stackandqueue.Stack;
import cse.buffalo.edu.algorithms.stdlib.StdIn;
import cse.buffalo.edu.algorithms.stdlib.StdOut;

/**
 * This class uses the stack data structure implemented in this package
 * It evaluates a simple +, -, *, / calculation
 */

public class Evaluate {

  public static void main(String[] args) {
    Stack<String> ops  = new Stack<String>();
    Stack<Double> vals = new Stack<Double>();
    while (!StdIn.isEmpty()) {
      String s = StdIn.readString();
      switch (s) {
        case "(":
          // Ignore "("
          break;
        case "+":
        case "-":
        case "*":
        case "/":
          ops.push(s);
          break;
        case ")":
          String op = ops.pop();
          if (op.equals("+")) vals.push(vals.pop() + vals.pop());
          else if (op.equals("-")) vals.push(vals.pop() - vals.pop());
          else if (op.equals("*")) vals.push(vals.pop() * vals.pop());
          else if (op.equals("/")) vals.push(vals.pop() / vals.pop());
          break;
        default:
          vals.push(Double.parseDouble(s));
          break;
      }
    }
    StdOut.println(vals.pop());
  }
}
