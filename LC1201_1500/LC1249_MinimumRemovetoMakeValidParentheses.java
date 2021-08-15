package LC1201_1500;
import java.util.*;
public class LC1249_MinimumRemovetoMakeValidParentheses {
    /**
     * Given a string s of '(' , ')' and lowercase English characters.
     *
     * Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting
     * parentheses string is valid and return any valid string.
     *
     * Formally, a parentheses string is valid if and only if:
     *
     * It is the empty string, contains only lowercase characters, or
     * It can be written as AB (A concatenated with B), where A and B are valid strings, or
     * It can be written as (A), where A is a valid string.
     *
     * Input: s = "lee(t(c)o)de)"
     * Output: "lee(t(c)o)de"
     *
     * Input: s = "a)b(c)d"
     * Output: "ab(c)d"
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s[i] is one of  '(' , ')' and lowercase English letters.
     *
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String minRemoveToMakeValid(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        Stack<Integer> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                stack.push(i);
            } else if (chars[i] == ')') {
                if (!stack.isEmpty()) stack.pop();
                else chars[i] = '\0';
            }
        }
        while (!stack.isEmpty()) chars[stack.pop()] = '\0'; // 剩下没匹配完的左括号必须删除

        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c != '\0') sb.append(c);
        }
        return sb.toString();
    }
}
/**
 * ref：LC921: # of minimum remove minimum # of parentheses
 *      LC301: all valid strings by removing minimum # of parentheses => dfs
 *      LC1249: any valid strings by removing minimum # of parentheses
 * 括号：Stack: 轻而易举的找到括号的匹配
 *      Greedy: # of unmatched left parenthesis
 *             when count < 0
 *      (( )) ) ... 要至少删掉一个右括号，标记下要删的就是这个右括号，输出一种怎么方便怎么来 => 删掉最后一个
 *      贪心思想变现 => 删掉unmatched左括号
 */