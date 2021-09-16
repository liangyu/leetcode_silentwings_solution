package LC001_300;
import java.util.*;
public class LC224_BasicCalculator {
    /**
     * Given a string s representing an expression, implement a basic calculator to evaluate it.
     *
     * Input: s = "1 + 1"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 3 * 10^5
     * s consists of digits, '+', '-', '(', ')', and ' '.
     * s represents a valid expression.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public int calculate(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        // init
        int res = 0, sign = 1;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // case 1: digit
            if (Character.isDigit(c)) {
                int num = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    num = num * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                res += num * sign;
            } else if (c == '+') sign = 1; // case 2: '+'
            else if (c == '-') sign = -1; // case 3: '-'
            else if (c == '(') {
                stack.push(res);
                stack.push(sign);
                res = 0;
                sign = 1;
            } else if (c == ')') {
                res = stack.pop() * res + stack.pop();
            }
        }
        return res;
    }
}
/**
 * 压栈
 * -3 => +(-3)
 * 每个数都有正负号
 * 所有单项式都是做加法操作
 */
