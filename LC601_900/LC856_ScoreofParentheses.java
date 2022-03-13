package LC601_900;
import java.util.*;
public class LC856_ScoreofParentheses {
    /**
     * Given a balanced parentheses string s, return the score of the string.
     *
     * The score of a balanced parentheses string is based on the following rule:
     *
     * "()" has score 1.
     * AB has score A + B, where A and B are balanced parentheses strings.
     * (A) has score 2 * A, where A is a balanced parentheses string.
     *
     * Input: s = "()"
     * Output: 1
     *
     * Constraints:
     *
     * 2 <= s.length <= 50
     * s consists of only '(' and ')'.
     * s is a balanced parentheses string.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int cur = 0;

        for (char c : s.toCharArray()) {
            if (c == ')') {  // need to pop
                if (cur != 0) cur *= 2;
                else cur = 1;
                cur += stack.peek();
                stack.pop();
            } else {
                stack.push(cur);
                cur = 0; // 进入下一层，reset cur = 0;
            }
        }
        return cur;
    }
}
/**
 * use stack
 * 带有层级的特征
 * 括号的规则稍微有些不同，对同一个层级，结束之后，脱括号的时候里面数值翻一番
 * 如果里面是空的，就不是x2，脱出来是1
 * (C(A)) -> (C 2A)
 */