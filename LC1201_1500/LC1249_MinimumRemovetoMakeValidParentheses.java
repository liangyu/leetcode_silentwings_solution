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
    // time = O(n), space = O(n）
    public String minRemoveToMakeValid(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        Stack<Integer> stack = new Stack<>(); // 存放左括号来和右括号去配对check，一旦出现则互相抵消，剩下的就是要被删除的
        HashSet<Integer> set = new HashSet<>(); // 存放所有要被删除的元素index

        for (int i = 0; i < s.length();i++) { // O(n)
            char c = s.charAt(i);
            if (c == '(') stack.push(i); // 遇到左括号依然按照传统套路去压栈
            else if (c == ')') {
                if (stack.isEmpty()) set.add(i); // 无对应左括号，则右括号要放入要被删除元素的set里
                else stack.pop(); // 遇到右括号如果有对应的左括号，则是合法成对，右括号可无视，左括号出栈
            }
        }

        // stack里剩下的左括号都是没有对应右括号匹配的，因此也要加入到被删除的set里
        while (!stack.isEmpty()) set.add(stack.pop());
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < s.length(); i++) { // O(n)
            // 除去set里都是要被删除的元素，其他可以无脑加入StringBuilder出答案
            if (!set.contains(i)) sb.append(s.charAt(i));
        }
        return sb.toString();
    }
}
