package LC001_300;
import java.util.*;
public class LC5_LongestPalindromicSubstring {
    /**
     * Given a string s, return the longest palindromic substring in s.
     *
     * Input: s = "babad"
     * Output: "bab"
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consist of only digits and English letters (lower-case and/or upper-case)
     *
     * @param s
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n^2)
    public String longestPalindrome(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        int len = s.length();
        int max = 0;
        String res = "";
        boolean[][] dp = new boolean[len][len];

        for (int j = 0; j < len; j++) {
            for (int i = 0; i <= j; i++) {
                if (s.charAt(i) == s.charAt(j) && ((j - i <= 2) || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                }
                if (dp[i][j]) {
                    if (j - i + 1 > max) {
                        max = j - i + 1;
                        res = s.substring(i, j + 1);
                    }
                }
            }
        }
        return res;
    }

    // S2: 中心扩散法 最优解！！！
    // time = O(n^2), space = O(1)
    public String longestPalindrome2(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        String res = "";
        for (int i = 0; i < s.length(); i++) { // O(n)
            res = helper(s, res, i, i);
            res = helper(s, res, i , i + 1);
        }
        return res;
    }

    private String helper(String s, String res, int left, int right) {
        int len = s.length();
        while (left >= 0 && right < len && s.charAt(left) == s.charAt(right)) { // O(n)
            left--;
            right++;
        }
        // 出loop时，left与right分别多-1和多+1，所以起点是left + 1，终点是right - 1
        String cur = s.substring(left + 1, right);
        if (cur.length() > res.length()) res = cur; // 找到更长的则更新res
        return res;
    }
}
