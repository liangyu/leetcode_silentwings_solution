package LC1801_2100;
import java.util.*;
public class LC2067_NumberofEqualCountSubstrings {
    /**
     * You are given a 0-indexed string s consisting of only lowercase English letters, and an integer count.
     * A substring of s is said to be an equal count substring if, for each unique letter in the substring, it appears
     * exactly count times in the substring.
     *
     * Return the number of equal count substrings in s.
     *
     * A substring is a contiguous non-empty sequence of characters within a string.
     *
     * Input: s = "aaabcbbcc", count = 3
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= s.length <= 3 * 10^4
     * 1 <= count <= 3 * 10^4
     * s consists only of lowercase English letters.
     * @param s
     * @param count
     * @return
     */
    // time = O(26n), space = O(1)
    public int equalCountSubstrings(String s, int count) {
        int res = 0;
        for (int m = 1; m <= Math.min(s.length(), 26); m++) {
            res += helper(s, m, count);
        }
        return res;
    }

    private int helper(String s, int m, int k) {
        int n = s.length(), res = 0, j = 0, count = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            boolean flag = false;
            while (j < n && map.size() <= m) {
                char c = s.charAt(j);
                if (map.getOrDefault(c, 0) == k) break;
                map.put(c, map.getOrDefault(c, 0) + 1);
                j++;
                if (map.get(c) == k) count++;
                if (map.size() == m && count == m) {
                    flag = true;
                    break;
                }
            }
            if (flag) res++;
            else if (j == n && map.size() == m && count == m) res++;
            char c = s.charAt(i);
            map.put(c, map.get(c) - 1);
            if (map.get(c) == k - 1) count--;
            if (map.get(c) == 0) map.remove(c);
        }
        return res;
    }
}
