package LC001_300;
import java.util.*;
public class LC32_LongestValidParentheses {
    /**
     * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed)
     * parentheses substring.
     *
     * Input: s = "(()"
     * Output: 2
     *
     * Constraints:
     *
     * 0 <= s.length <= 3 * 10^4
     * s[i] is '(', or ')'.
     * @param s
     * @return
     */
    // S1: Stack
    // time = O(n), space = O(n)
    public int longestValidParentheses(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        Stack<Integer> stack = new Stack<>();
        int start = -1, res = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') stack.push(i);
            else {
               if (stack.isEmpty()) start = i;
               else {
                   stack.pop();
                   if (stack.isEmpty()) res = Math.max(res, i - start);
                   else res = Math.max(res, i - stack.peek());
               }
            }
        }
        return res;
    }
}
