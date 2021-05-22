package LC001_300;
import java.util.*;
public class LC72_EditDistance {
    /**
     * Given two strings word1 and word2, return the minimum number of operations required to convert word1 to word2.
     *
     * You have the following three operations permitted on a word:
     *
     * Insert a character
     * Delete a character
     * Replace a character
     *
     * Input: word1 = "horse", word2 = "ros"
     * Output: 3
     *
     * Constraints:
     *
     * 0 <= word1.length, word2.length <= 500
     * word1 and word2 consist of lowercase English letters.
     * @param word1
     * @param word2
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int minDistance(String word1, String word2) {
        // corner case
        if (word1 == null || word2 == null) return 0;

        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        // dp[0][0] = 0;

        for (int i = 1; i <= m; i++) dp[i][0] = i;
        for (int j = 1; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // => Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1]);
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }
        return dp[m][n];
    }
}
