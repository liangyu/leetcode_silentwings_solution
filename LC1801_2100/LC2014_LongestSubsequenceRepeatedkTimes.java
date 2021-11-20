package LC1801_2100;
import java.util.*;
public class LC2014_LongestSubsequenceRepeatedkTimes {
    /**
     * You are given a string s of length n, and an integer k. You are tasked to find the longest subsequence repeated
     * k times in string s.
     *
     * A subsequence is a string that can be derived from another string by deleting some or no characters without
     * changing the order of the remaining characters.
     *
     * A subsequence seq is repeated k times in the string s if seq * k is a subsequence of s, where seq * k represents
     * a string constructed by concatenating seq k times.
     *
     * For example, "bba" is repeated 2 times in the string "bababcba", because the string "bbabba", constructed by
     * concatenating "bba" 2 times, is a subsequence of the string "bababcba".
     * Return the longest subsequence repeated k times in string s. If multiple such subsequences are found, return
     * the lexicographically largest one. If there is no such subsequence, return an empty string.
     *
     * Input: s = "letsleetcode", k = 2
     * Output: "let"
     *
     * Constraints:
     *
     * n == s.length
     * 2 <= k <= 2000
     * 2 <= n < k * 8
     * s consists of lowercase English letters.
     * @param s
     * @param k
     * @return
     */
    // S1: DFS
    // time = O(n), space = O(n)
    private String res = "";
    public String longestSubsequenceRepeatedK(String s, int k) {
        // corner case
        if (s == null || s.length() == 0 || k < 0) return "";

        int[] freq = new int[128];
        for (char c : s.toCharArray()) freq[c]++;

        List<Character> list = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            if (freq[c] >= k) list.add(c);
        }

        dfs(s, k, "", list);
        return res;
    }

    private void dfs(String s, int k, String sub, List<Character> list) {
        // base case
        if (sub.length() > 1 && !isSub(s, sub, k)) return;

        if (sub.length() > res.length()) res = sub;

        for (int i = list.size() - 1; i >= 0; i--) { // 确保前面的string肯定比后面的lexicographically larger
            String next = sub + list.get(i);
            dfs(s, k, next, list);
        }
    }

    private boolean isSub(String s, String sub, int k) {
        int j = 0, repeat = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.length() - i < (k - repeat - 1) * sub.length()) return false;
            if (s.charAt(i) == sub.charAt(j)) {
                j++;
                j %= sub.length();
                if (j == 0) {
                    repeat++;
                    if (repeat == k) return true;
                }
            }
        }
        return false;
    }

    // S2: DFS
    // time = O(n), space = O(n)
    private String ans = "";
    public String longestSubsequenceRepeatedK2(String s, int k) {
        int[] count = new int[26];
        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        String t = "";
        for (int i = 0; i < 26; i++) {
            if (count[i] >= k) {
                t += (char)('a' + i);
            }
        }

        dfs(t, "", s, k);
        return ans;
    }

    private void dfs(String t, String temp, String s, int k) {
        // base case
        if (!checkOK(temp, s, k)) return;

        if (temp.length() > ans.length() || temp.length() == ans.length() && temp.compareTo(ans) > 0) ans = temp;

        if (temp.length() == 7) return;

        for (char ch : t.toCharArray()) {
            temp += ch;
            dfs(t, temp, s, k);
            temp = temp.substring(0, temp.length() - 1);
        }
    }

    private boolean checkOK(String temp, String s, int k) {
        if (temp.length() == 0) return true;
        int j = 0, round = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != temp.charAt(j)) continue;
            j++;
            if (j == temp.length()) {
                j = 0;
                round++;
                if (round == k) return true;
            }
        }
        return false;
    }
}
/**
 * 找循环节
 * repeating subsequence如何找？
 * 2 <= n < k * 8 = 16000，找一段subsequence显然天文数字
 * n < k * 8 => 循环节要重复k次，8k > n  => 循环节的长度 t < 8
 * 1. |t| <= 7  => C(16000, 7)
 *              => 26^7  -> 盲猜
 * 2. the number of letters whose frequency >= k is <= 7  字符种类不会超过7
 * 每一位上就没有26种选择了，只有7种选择 => 7^7 = 823543
 * 穷举循环节似乎是可行的
 * len = 7, 7^7
 * len = 6, 7^6
 * ...
 * 7^1 + ... + 7^7 = 7 * (7^7 - 1) / 6
 * 3. check O(16000)
 * ab如果不满足条件，abxxxxx肯定也不满足。先构造短的循环节，很容易可以及时刹车
 */
