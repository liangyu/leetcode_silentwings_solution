package LC001_300;
import java.util.*;
public class LC159_LongestSubstringwithAtMostTwoDistinctCharacters {
    /**
     * Given a string s, return the length of the longest substring that contains at most two distinct characters.
     *
     * Input: s = "eceba"
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^4
     * s consists of English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int[] counter = new int[128];
        int start = 0, curK = 0, res = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (counter[c]++ == 0) curK++;
            while (curK > 2) {
                if (--counter[s.charAt(start++)] == 0) curK--;
            }
            res = Math.max(res, i - start + 1);
        }
        return res;
    }
}
