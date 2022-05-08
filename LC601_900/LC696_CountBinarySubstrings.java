package LC601_900;
import java.util.*;
public class LC696_CountBinarySubstrings {
    /**
     * Give a string s, count the number of non-empty (contiguous) substrings that have the same number of 0's and 1's, and all the 0's and all the 1's in these substrings are grouped consecutively.
     *
     * Substrings that occur multiple times are counted the number of times they occur.
     *
     * Input: "00110011"
     * Output: 6
     *
     * Note:
     *
     * s.length will be between 1 and 50,000.
     * s will only consist of "0" or "1" characters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int countBinarySubstrings(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int cur = 1, prev = 0, res = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) cur++;
            else {
                res += Math.min(prev, cur);
                prev = cur;
                cur = 1;
            }
        }
        return res + Math.min(prev, cur);
    }
}
/**
 * First, I countu the number of 1 or 0 grouped consectively.
 * For example "0110001111" will be [1, 2, 3, 4].
 *
 * Second, for any possible substrings with 1 and 0 grouped consecutively, the number of valid substring will be the
 * minimum number of 0 and 1.
 * For example "0001111", will be min(3, 4) = 3, ("01", "0011", "000111")
 */
