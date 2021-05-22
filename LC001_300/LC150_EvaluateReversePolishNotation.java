package LC001_300;
import java.util.*;
public class LC150_EvaluateReversePolishNotation {
    /**
     * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
     *
     * Valid operators are +, -, *, and /. Each operand may be an integer or another expression.
     *
     * Note that division between two integers should truncate toward zero.
     *
     * It is guaranteed that the given RPN expression is always valid. That means the expression would always evaluate
     * to a result, and there will not be any division by zero operation.
     *
     * Input: tokens = ["2","1","+","3","*"]
     * Output: 9
     *
     * Constraints:
     *
     * 1 <= tokens.length <= 10^4
     * tokens[i] is either an operator: "+", "-", "*", or "/", or an integer in the range [-200, 200].
     * @param tokens
     * @return
     */
    // time = O(n), space = O(n)
    public int evalRPN(String[] tokens) {
        // corner case
        if (tokens == null || tokens.length == 0) return 0;

        Stack<Integer> stack = new Stack<>();

        for (String s : tokens) {
            if (s.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (s.equals("-")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a - b);
            } else if (s.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (s.equals("/")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a / b);
            } else stack.push(Integer.valueOf(s));
        }
        return stack.pop();
    }
}
