package LC1501_1800;

public class LC1614_MaximumNestingDepthoftheParentheses {
    /**
     * A string is a valid parentheses string (denoted VPS) if it meets one of the following:
     *
     * It is an empty string "", or a single character not equal to "(" or ")",
     * It can be written as AB (A concatenated with B), where A and B are VPS's, or
     * It can be written as (A), where A is a VPS.
     * We can similarly define the nesting depth depth(S) of any VPS S as follows:
     *
     * depth("") = 0
     * depth(C) = 0, where C is a string with a single character not equal to "(" or ")".
     * depth(A + B) = max(depth(A), depth(B)), where A and B are VPS's.
     * depth("(" + A + ")") = 1 + depth(A), where A is a VPS.
     * For example, "", "()()", and "()(()())" are VPS's (with nesting depths 0, 1, and 2), and ")(" and "(()" are not VPS's.
     *
     * Given a VPS represented as string s, return the nesting depth of s.
     *
     * Input: s = "(1+(2*3)+((8)/4))+1"
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s consists of digits 0-9 and characters '+', '-', '*', '/', '(', and ')'.
     * It is guaranteed that parentheses expression s is a VPS.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int maxDepth(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int delta = 0, res = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                delta++;
                res = Math.max(res, delta);
            }
            else if (c == ')') delta--;
        }
        return res;
    }
}
