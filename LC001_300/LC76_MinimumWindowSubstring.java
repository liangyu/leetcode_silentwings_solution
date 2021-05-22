package LC001_300;
import java.util.*;
public class LC76_MinimumWindowSubstring {
    /**
     * Given two strings s and t, return the minimum window in s which will contain all the characters in t. If there
     * is no such window in s that covers all characters in t, return the empty string "".
     *
     * Note that If there is such a window, it is guaranteed that there will always be only one unique minimum window
     * in s.
     *
     * Input: s = "ADOBECODEBANC", t = "ABC"
     * Output: "BANC"
     *
     * Constraints:
     *
     * 1 <= s.length, t.length <= 105
     * s and t consist of English letters.
     *
     * @param s
     * @param t
     * @return
     */
    // S1: int[128] + Sliding Window
    // time = O(n), space = O(1)
    public String minWindow(String s, String t) {
        // corner case
        if (s == null || s.length() == 0 || t == null || t.length() == 0) return "";

        int[] dict = new int[128]; // ASCII
        for (int i = 0; i < t.length(); i++) dict[t.charAt(i)]++; // 记录target string进入dict

        int start = 0, total = t.length();
        int min = Integer.MAX_VALUE;
        int j = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (dict[c]-- > 0) total--; // 注意这里无论如何，target[c]都需要--而变化，哪怕if条件不满足导致total不变！！！
            while (total == 0) {
                if (i - j + 1 < min) {
                    min = i - j + 1;
                    start = j;
                }
                // 访问过的char由于左端窗口收缩，又都在收缩的同时在target里加了回来
                if (++dict[s.charAt(j++)] > 0) total++;
            }
        }
        return min == Integer.MAX_VALUE ? "" : s.substring(start, start + min);
    }
}
