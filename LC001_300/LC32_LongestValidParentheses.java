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
        int n = s.length(), res = 0;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(') stack.push(i);
            else {
                if (!stack.isEmpty() && s.charAt(stack.peek()) == '(') {
                    stack.pop();
                    res = Math.max(res, i - (stack.isEmpty() ? -1 : stack.peek())); // 注意这里stack可能为空，从头开始到i，取-1！！！
                } else stack.push(i); // 栈顶没有左括号与之匹配，现在的右括号作为一堵墙放入栈中
            }
        }
        return res;
    }
}
/**
 * 我们考虑用一个stack来做常规的符号匹配，即
 * 遇到左括号入栈；
 * 遇到右括号，尝试用它对消栈顶的左括号，使其退栈（如果有的话）。否则的话我们就将右括号入栈。
 * 将栈顶元素j退栈“对消”之后，此时新的栈顶元素对应的位置并不一定是与j相邻的。
 * ()(****) -> 前面配对的已经在栈里没有了，对消掉了
 *   j    i
 * (*****(****)
 *  空隙 -> 曾经对消过
 * 非法字符，比如右括号 -> 像一堵墙，永远不可能再被消掉
 */