package LC001_300;
import java.util.*;
public class LC227_BasicCalculatorII {
    /**
     * Given a string s which represents an expression, evaluate this expression and return its value.
     *
     * The integer division should truncate toward zero.
     *
     * Input: s = "3+2*2"
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= s.length <= 3 * 10^5
     * s consists of integers and operators ('+', '-', '*', '/') separated by some number of spaces.
     * s represents a valid expression.
     * All the integers in the expression are non-negative integers in the range [0, 2^31 - 1].
     * The answer is guaranteed to fit in a 32-bit integer.
     * @param s
     * @return
     */
    // S1: stack
    // time = O(n), space = O(n)
    public int calculate(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        Stack<Integer> stack = new Stack<>();
        int res = 0, num = 0;
        char sign = '+';

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    num = num * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
            }
            if (!Character.isDigit((c)) && c != ' ' || i == s.length() - 1) {
                if (sign == '+') stack.push(num);
                if (sign == '-') stack.push(-num);
                if (sign == '*') stack.push(stack.pop() * num);
                if (sign == '/') stack.push(stack.pop() / num);
                sign = s.charAt(i);
                num = 0;
            }
        }
        while (!stack.isEmpty()) res += stack.pop();
        return res;
    }

    // S2: rolling matrix
    // time = O(n), space = O(1)
    public int calculate2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        s = s.trim().replaceAll(" +", "");
        int res = 0, prev = 0, i = 0;
        char sign = '+';


        while (i < s.length()) {
            int cur = 0;
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                cur = cur * 10 + s.charAt(i) - '0';
                i++;
            }

            if (sign == '+') {
                res += prev;
                prev = cur;
            } else if (sign == '-') {
                res += prev;
                prev = -cur;
            } else if (sign == '*') {
                prev = prev * cur;
            } else if (sign == '/') {
                prev = prev / cur;
            }

            if (i < s.length()) {
                sign = s.charAt(i);
                i++;
            }
        }

        res += prev;
        return res;
    }
}
