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

    // S2: HashMap + two pointers
    // time = O(n), space = O(1)
    public String minWindow2(String s, String t) {
        HashMap<Character, Integer> hs = new HashMap<>();
        HashMap<Character, Integer> ht = new HashMap<>();

        for (char c : t.toCharArray()) ht.put(c, ht.getOrDefault(c, 0) + 1);

        String res = "";
        int m = s.length(), n = t.length();
        int cnt = 0, j = 0;
        for (int i = 0; i < m; i++) { // i: right pointer
            char c = s.charAt(i);
            hs.put(c, hs.getOrDefault(c, 0) + 1);
            if (hs.get(c) <= ht.getOrDefault(c, 0)) cnt++;

            while (j < m && hs.getOrDefault(s.charAt(j), 0) > ht.getOrDefault(s.charAt(j), 0)) {
                c = s.charAt(j);
                hs.put(c, hs.get(c) - 1);
                j++;
            }
            if (cnt == n) {
                if (res.length() == 0 || i - j + 1 < res.length()) res = s.substring(j, i + 1);
            }
        }
        return res;
    }
}
