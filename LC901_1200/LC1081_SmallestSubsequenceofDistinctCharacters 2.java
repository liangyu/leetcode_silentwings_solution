package LC901_1200;
import java.util.*;
public class LC1081_SmallestSubsequenceofDistinctCharacters {
    /**
     * Given a string s, return the lexicographically smallest subsequence of s that contains all the distinct
     * characters of s exactly once.
     *
     * Input: s = "bcabc"
     * Output: "abc"
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consists of lowercase English letters.
     *
     * Note: This question is the same as 316: https://leetcode.com/problems/remove-duplicate-letters/
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String smallestSubsequence(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        HashMap<Character, Integer> map = new HashMap<>();
        HashSet<Character> set = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);

        for (char c : s.toCharArray()) {
            if (set.contains(c)) map.put(c, map.get(c) - 1);
            else {
                while (sb.length() > 0 && sb.charAt(sb.length() - 1) > c && map.get(sb.charAt(sb.length() - 1)) > 0) {
                    set.remove(sb.charAt(sb.length() - 1));
                    sb.setLength(sb.length() - 1);
                }
                sb.append(c);
                map.put(c, map.get(c) - 1);
                set.add(c);
            }
        }
        return sb.toString();
    }
}
/**
 * same as LC316
 * 单调栈经典应用！！！
 */