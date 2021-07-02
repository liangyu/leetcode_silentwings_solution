package LC901_1200;

public class LC1092_ShortestCommonSupersequence {
    /**
     * Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences.
     * If multiple answers exist, you may return any of them.
     *
     * (A string S is a subsequence of string T if deleting some number of characters from T (possibly 0, and the
     * characters are chosen anywhere from T) results in the string S.)
     *
     * Input: str1 = "abac", str2 = "cab"
     * Output: "cabac"
     *
     * Note:
     *
     * 1 <= str1.length, str2.length <= 1000
     * str1 and str2 consist of lowercase English letters.
     * @param str1
     * @param str2
     * @return
     */
    // S1: SCS
    // time = O(m * n), space = O(m * n)
    public String shortestCommonSupersequence(String str1, String str2) {
        int m = str1.length(), n = str2.length();
        str1 = "#" + str1;
        str2 = "#" + str2;

        int[][] dp = new int[m + 1][n + 1];

        // init
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        // LCS
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i) == str2.charAt(j)) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
            }
        }
        // dp[m][n]
        int i = m, j = n;
        StringBuilder sb = new StringBuilder();
        while (i > 0 && j > 0) {
            if (str1.charAt(i) == str2.charAt(j)) {
                sb.append(str1.charAt(i));
                i--;
                j--;
            } else if (dp[i][j] == dp[i - 1][j] + 1) sb.append(str1.charAt(i--));
            else sb.append(str2.charAt(j--));
        }
        while (i > 0) sb.append(str1.charAt(i--));
        while (j > 0) sb.append(str2.charAt(j--));
        return sb.reverse().toString();
    }

    // S2: LCS
    // time = O(m * n), space = O(m * n)
    public String shortestCommonSupersequence2(String str1, String str2) {
        int m = str1.length(), n = str2.length();
        str1 = "#" + str1;
        str2 = "#" + str2;

        int[][] dp = new int[m + 1][n + 1];

        // LCS
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i) == str2.charAt(j)) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        // dp[m][n]
        int i = m, j = n;
        StringBuilder sb = new StringBuilder();
        while (i > 0 && j > 0) {
            if (str1.charAt(i) == str2.charAt(j)) {
                sb.append(str1.charAt(i));
                i--;
                j--;
            } else if (dp[i][j] == dp[i - 1][j]) sb.append(str1.charAt(i--));
            else sb.append(str2.charAt(j--));
        }
        while (i > 0) sb.append(str1.charAt(i--));
        while (j > 0) sb.append(str2.charAt(j--));
        return sb.reverse().toString();
    }
}
/**
 * 双序列dp
 * 套路：二维dp
 * dp[i][j]: the length of Shortest Common Supersequence for str1[0:i] and str2[0:j]
 * dp[i-1][j], dp[i][j-1], dp[i-1][j-1]
 *
 * ZZZZZZZZZZZZZZ a
 * xxxxxxxxx a
 * yyyyy b
 *
 * if (str1[i] == str2[j]) dp[i][j] = dp[i-1][j-1]+1
 * else: dp[i][j] = min{dp[i-1][j]+1, dp[i][j-1]+1}
 *
 * Longest Common Subsequence
 * 如何重构出来？
 * 1 => 2 3 4
 * 2 => 3 8
 * dp[j] <=  min(dp[i0], dp[i1], dp[i2])
 * prev[j] => i2  回溯
 *
 * x x x x x x x x
 * y y y y y y
 *
 *            c y y x x x x
 * x a x x x b x (c x x x x)
 * y y a y b y (c y y)
 */