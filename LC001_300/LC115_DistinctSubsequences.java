package LC001_300;
import java.util.*;
public class LC115_DistinctSubsequences {
    /**
     * Given two strings s and t, return the number of distinct subsequences of s which equals t.
     *
     * A string's subsequence is a new string formed from the original string by deleting some (can be none) of the
     * characters without disturbing the remaining characters' relative positions. (i.e., "ACE" is a subsequence of
     * "ABCDE" while "AEC" is not).
     *
     * It is guaranteed the answer fits on a 32-bit signed integer.
     *
     * Input: s = "rabbbit", t = "rabbit"
     * Output: 3
     * Constraints:
     *
     * 1 <= s.length, t.length <= 1000
     * s and t consist of English letters.
     * @param s
     * @param t
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int numDistinct(String s, String t) {
        // corner case
        if (s == null || t == null) return 0;

        int m = s.length(), n = t.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = 1;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i - 1][j];
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }
}
