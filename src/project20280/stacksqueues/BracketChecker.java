package project20280.stacksqueues;

import java.util.Stack;

class BracketChecker {
    private final String input;

    public BracketChecker(String in) {
        input = in;
    }

    public void check() {
        Stack<Character> stack = new Stack<>(); // find openers

        for (int i = 0; i < input.length(); i++) { // check all
            char ch = input.charAt(i); // gets current

            if (ch == '(' || ch == '[' || ch == '{') { // remember opener
                stack.push(ch); //put on top
            } else if (ch == ')' || ch == ']' || ch == '}') { // if closer, match
                if (stack.isEmpty()) { //never opened, dont close
                    System.out.println("Error: " + ch + " at " + i);
                    return;
                }

                char top = stack.pop(); // removes the most recent opener so we can compare it

                if ((ch == ')' && top != '(') ||
                        (ch == ']' && top != '[') ||
                        (ch == '}' && top != '{')) { // check closer matches most recent opener
                    System.out.println("Error: " + ch + " at " + i);
                    return;
                }
            }
        }

        if (!stack.isEmpty()) { //remaining means opened not closed
            System.out.println("Error: missing right delimiter");
        } else { //no remains, all good
            System.out.println("Correct");
        }
    }

    public static void main(String[] args) {
        String[] inputs = {
                "[]]()()", // not correct
                "c[d]", // correct\n" +
                "a{b[c]d}e", // correct\n" +
                "a{b(c]d}e", // not correct; ] doesn't match (\n" +
                "a[b{c}d]e}", // not correct; nothing matches final }\n" +
                "a{b(c) ", // // not correct; Nothing matches opening {
        };

        for (String input : inputs) {
            BracketChecker checker = new BracketChecker(input);
            System.out.println("checking: " + input);
            checker.check();
        }
    }
}