package LC1801_2100;
import java.util.*;
public class LC1876_SubstringsofSizeThreewithDistinctCharacters {
    /**
     * A string is good if there are no repeated characters.
     *
     * Given a string s, return the number of good substrings of length three in s.
     *
     * Note that if there are multiple occurrences of the same substring, every occurrence should be counted.
     *
     * A substring is a contiguous sequence of characters in a string.
     *
     * Input: s = "xyzzaz"
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int countGoodSubstrings(String s) {
        // corner case
        if (s == null || s.length() < 3) return 0;

        int res = 0;
        for (int i = 1; i < s.length() - 1; i++) {
            if (s.charAt(i - 1) != s.charAt(i) && s.charAt(i) != s.charAt(i + 1) && s.charAt(i - 1) != s.charAt(i + 1)) {
                res++;
            }
        }
        return res;
    }
}
