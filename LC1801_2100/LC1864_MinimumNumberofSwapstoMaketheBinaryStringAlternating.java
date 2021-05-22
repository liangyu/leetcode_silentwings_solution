package LC1801_2100;
import java.util.*;
public class LC1864_MinimumNumberofSwapstoMaketheBinaryStringAlternating {
    /**
     * Given a binary string s, return the minimum number of character swaps to make it alternating, or -1 if it is
     * impossible.
     *
     * The string is called alternating if no two adjacent characters are equal. For example, the strings "010" and
     * "1010" are alternating, while the string "0100" is not.
     *
     * Any two characters may be swapped, even if they are not adjacent.
     *
     * Input: s = "111000"
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s[i] is either '0' or '1'.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int minSwaps(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int zero = 0, one = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '0') zero++;
            if (c == '1') one++;
        }
        if (Math.abs(zero - one) > 1) return -1;

        if (zero > one) return helper(s, '0');
        if (zero < one) return helper(s, '1');
        return Math.min(helper(s, '0'), helper(s, '1'));
    }

    private int helper(String s, char ch) {
        int count = 0, n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (i % 2 == 0 && c != ch) count++;
            if (i % 2 == 1 && c == ch) count++;
        }
        return count / 2;
    }
}
