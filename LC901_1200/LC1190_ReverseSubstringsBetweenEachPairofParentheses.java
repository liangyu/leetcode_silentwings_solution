package LC901_1200;
import java.util.*;
public class LC1190_ReverseSubstringsBetweenEachPairofParentheses {
    /**
     * You are given a string s that consists of lower case English letters and brackets.
     *
     * Reverse the strings in each pair of matching parentheses, starting from the innermost one.
     *
     * Your result should not contain any brackets.
     *
     * Input: s = "(abcd)"
     * Output: "dcba"
     *
     * Constraints:
     *
     * 1 <= s.length <= 2000
     * s only contains lower case English characters and parentheses.
     * It is guaranteed that all parentheses are balanced.
     * @param s
     * @return
     */
    // S1
    // time = O(n^2), space = O(n)
    public String reverseParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        Stack<Integer> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) sb.append(c);
            else if (c == '(') stack.push(sb.length());
            else {
                int i = stack.pop();
                sb = reverse(sb, i, sb.length());
            }
        }
        return sb.toString();
    }

    private StringBuilder reverse(StringBuilder sb, int start, int end) {
        StringBuilder res = new StringBuilder();
        res.append(sb.subSequence(start, end));
        res.reverse();
        res.insert(0, sb.substring(0, start)).append(sb.substring(end));
        return res;
    }

    // S2
    // time = O(n), space = O(n)
    public String reverseParentheses2(String s) {
        StringBuilder sb = new StringBuilder();
        Stack<Integer> stack = new Stack<>();
        int n = s.length();
        int[] pair = new int[n];
        Arrays.fill(pair, -1);

        // document the parenthesis pair
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') stack.push(i);
            else if (s.charAt(i) == ')') {
                int j = stack.pop();
                pair[i] = j;
                pair[j] = i;
            }
        }

        int i = 0, d = 1;
        while (i < n) {
            if (Character.isLowerCase(s.charAt(i))) sb.append(s.charAt(i));
            else {
                i = pair[i];
                d = -d;
            }
            i = i + d;
        }
        return sb.toString();
    }
}
/**
 * 先脱内层的括号，全程模拟一遍
 * 看见右括号，找到一个左括号，中间反一反，
 * 返回后再放回去
 * u(love)i
 * u love
 * stack: 1
 * u(lo(v)e)i
 * u evol i -> i love u
 * stack:0,1
 * S2: 先脱外层括号
 * abc(def(ghi)k)
 * abckghifed
 * 1. enter a parenthesis pair: -> (  , ) <-
 *    go to the opposite one, flip the direction
 * 2. exit a parenthesis pair: -> )  ,  ( <-
 *    go to the opposite one, flip the direction
 */
