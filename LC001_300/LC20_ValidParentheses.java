package LC001_300;
import java.util.*;
public class LC20_ValidParentheses {
    /**
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']',
     * determine if the input string is valid.
     *
     * An input string is valid if:
     *
     * Open brackets must be closed by the same type of brackets.
     * Open brackets must be closed in the correct order.
     *
     * Input: s = "([)]"
     * Output: false
     *
     * Input: s = "{[]}"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 104
     * s consists of parentheses only '()[]{}'.
     *
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public boolean isValid(String s) {
        // corner case
        if (s == null || s.length() == 0) return false;

        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (c == '(') stack.push(')'); // trick: 如果是左括号，则压入其对应的右括号以便下面直接比较是否相等来判断！
            else if (c == '[') stack.push(']');
            else if (c == '{') stack.push('}');
            else {
                if (stack.isEmpty() || stack.pop() != c) return false;
            }
        }
        return stack.isEmpty(); // 注意最后要check stack是否为空！！！
    }
}
