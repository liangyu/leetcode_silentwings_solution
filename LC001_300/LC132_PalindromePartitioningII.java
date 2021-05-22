package LC001_300;
import java.util.*;
public class LC132_PalindromePartitioningII {
    /**
     * Given a string s, partition s such that every substring of the partition is a palindrome.
     *
     * Return the minimum cuts needed for a palindrome partitioning of s.
     *
     * Input: s = "aab"
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= s.length <= 2000
     * s consists of lower-case English letters only.
     *
     * @param s
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public int minCut(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 0;
        boolean[][] isPalin = getIsPalin(s);

        for (int i = 1; i <= n; i++) {
            dp[i] = i;
            for (int j = 0; j < i; j++) {
                if (isPalin[j][i - 1]) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[n] - 1;
    }

    private boolean[][] getIsPalin(String s) {
        int n = s.length();
        boolean[][] isPalin = new boolean[n][n];

        for (int i = 0; i < n; i++) isPalin[i][i] = true;
        for (int i = 0; i < n - 1; i++) isPalin[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        for (int i = 2; i < n; i++) {
            for (int j = 0; j + i < n; j++) {
                isPalin[j][j + i] = isPalin[j + 1][j + i - 1] && s.charAt(j) == s.charAt(j + i);
            }
        }
        return isPalin;
    }
    // S2
    // time = O(n^2), space = O(n^2)
    public int minCut2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int len = s.length();
        int[] dp = new int[len + 1];
        boolean[][] isP = new boolean[len][len];

        for (int i = len - 1; i >= 0; i--) {
            dp[i] = len - i;
            for (int j = i; j < len; j++) {
                if (i == j || (s.charAt(i) == s.charAt(j) && (i + 1 == j || isP[i + 1][j - 1]))) {
                    isP[i][j] = true;
                    dp[i] = Math.min(dp[i], dp[j + 1] + 1);
                }
            }
        }
        return dp[0] - 1;
    }

    // S3
    // time = O(n^2), space = O(n^2)
    public int minCut3(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int[] dp = new int[n];
        boolean[][] isPalin = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == s.charAt(j) && (i - j < 2 || isPalin[j + 1][i - 1])) {
                    isPalin[j][i] = true;
                    min = j == 0 ? 0 : Math.min(min, dp[j - 1] + 1);
                }
            }
            dp[i] = min;
        }
        return dp[n - 1];
    }
}