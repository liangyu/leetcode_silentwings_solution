package LC601_900;
import java.util.*;
public class LC647_PalindromicSubstrings {
    /**
     * Given a string, your task is to count how many palindromic substrings in this string.
     *
     * The substrings with different start indexes or end indexes are counted as different substrings even they consist
     * of same characters.
     *
     * Input: "abc"
     * Output: 3
     *
     * Note:
     *
     * The input string length won't exceed 1000.
     * @param s
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n^2)
    public int countSubstrings(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int res = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(j) == s.charAt(i) && (i - j < 2 || dp[j + 1][i - 1])) {
                    dp[j][i] = true;
                    res++;
                }
            }
        }
        return res;
    }

    // S2: 中心扩散法
    // time = O(n^2), space = O(1)
    public int countSubstrings2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int res = 0;
        int n = s.length();

        for (int center = 0; center < 2 * n - 1; center++) {
            int left = center / 2, right = left + center % 2;
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                res++;
                left--;
                right++;
            }
        }
        return res;
    }
}
