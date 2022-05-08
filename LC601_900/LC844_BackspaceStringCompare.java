package LC601_900;
import java.util.*;
public class LC844_BackspaceStringCompare {
    /**
     * Given two strings s and t, return true if they are equal when both are typed into empty text editors. '#' means
     * a backspace character.
     *
     * Note that after backspacing an empty text, the text will continue empty.
     *
     * Input: s = "ab#c", t = "ad#c"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length, t.length <= 200
     * s and t only contain lowercase letters and '#' characters.
     *
     * Follow up: Can you solve it in O(n) time and O(1) space?
     * @param s
     * @param t
     * @return
     */
    // S1: StringBuilder
    // time = O(m + n), space = O(m + n)
    public boolean backspaceCompare(String s, String t) {
        s = helper(s);
        t = helper(t);
        return s.equals(t);
    }

    private String helper(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c != '#') sb.append(c);
            else {
                if (sb.length() > 0) sb.setLength(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    // S2: two pointers
    public boolean backspaceCompare2(String s, String t) {
        int m = s.length(), n = t.length();
        int i = m - 1, j = n - 1;

        while (i >= 0 || j >= 0) {
            i = helper(i, s);
            j = helper(j, t);
            if (i >= 0 && j >= 0 && s.charAt(i) != t.charAt(j)) return false;
            if ((i >= 0) != (j >= 0)) return false;
            i--;
            j--;
        }
        return true;
    }

    private int helper(int p, String s) {
        int skip = 0;
        while (p >= 0) {
            if (s.charAt(p) == '#') {
                p--;
                skip++;
            } else if (skip > 0) {
                p--;
                skip--;
            } else break;
        }
        return p;
    }
}
/**
 *
 */
