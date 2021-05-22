package LC601_900;
import java.util.*;
public class LC816_AmbiguousCoordinates {
    /**
     * We had some 2-dimensional coordinates, like "(1, 3)" or "(2, 0.5)".  Then, we removed all commas, decimal points,
     * and spaces, and ended up with the string s.  Return a list of strings representing all possibilities for what
     * our original coordinates could have been.
     *
     * Our original representation never had extraneous zeroes, so we never started with numbers like "00", "0.0",
     * "0.00", "1.0", "001", "00.01", or any other number that can be represented with less digits.  Also, a decimal
     * point within a number never occurs without at least one digit occuring before it, so we never started with
     * numbers like ".1".
     *
     * The final answer list can be returned in any order.  Also note that all coordinates in the final answer have
     * exactly one space between them (occurring after the comma.)
     *
     * Input: s = "(123)"
     * Output: ["(1, 23)", "(12, 3)", "(1.2, 3)", "(1, 2.3)"]
     *
     * Note:
     *
     * 4 <= s.length <= 12.
     * s[0] = "(", s[s.length - 1] = ")", and the other elements in s are digits.
     * @param s
     * @return
     */
    // time = O(n^3), space = O(n)
    public List<String> ambiguousCoordinates(String s) {
        List<String> res = new ArrayList<>();
        // corner case
        if (s == null || s.length() == 0) return res;

        int n = s.length();
        for (int i = 1; i < n - 2; i++) {
            List<String> s1 = helper(s.substring(1, i + 1));
            List<String> s2 = helper(s.substring(i + 1, n - 1));
            for (String str1 : s1) {
                for (String str2 : s2) {
                    res.add("(" + str1 + ", " + str2 + ")");
                }
            }
        }
        return res;
    }

    private List<String> helper(String s) {
        int n = s.length();
        List<String> res = new ArrayList<>();

        // case 1: illegal
        if (n == 0 || (n > 1 && s.charAt(0) == '0' && s.charAt(n - 1) == '0')) return res;

        // case 2: 0.xxxx
        if (n > 1 && s.charAt(0) == '0') {
            res.add("0." + s.substring(1));
            return res;
        }

        res.add(s);
        // case 3: '0' -> above res has add '0' to itself -> directly return res here
        if (n == 1 || s.charAt(n - 1) == '0') return res;

        // case 4: all other normal cases
        for (int i = 1; i < n; i++) res.add(s.substring(0, i) + '.' + s.substring(i));
        return res;
    }
}
