package LC901_1200;
import java.util.*;
public class LC921_MinimumAddtoMakeParenthesesValid {
    /**
     * Given a string s of '(' and ')' parentheses, we add the minimum number of parentheses ( '(' or ')', and in any
     * positions ) so that the resulting parentheses string is valid.
     *
     * Formally, a parentheses string is valid if and only if:
     *
     * It is the empty string, or
     * It can be written as AB (A concatenated with B), where A and B are valid strings, or
     * It can be written as (A), where A is a valid string.
     * Given a parentheses string, return the minimum number of parentheses we must add to make the resulting string
     * valid.
     *
     * Input: s = "())"
     * Output: 1
     *
     * Note:
     *
     * s.length <= 1000
     * s only consists of '(' and ')' characters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int minAddToMakeValid(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int delta = 0, count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') delta++;
            else delta--;
            if (delta < 0) {
                count++;
                delta = 0;
            }
        }
        if (delta > 0) count += delta;
        return count;
    }
}
