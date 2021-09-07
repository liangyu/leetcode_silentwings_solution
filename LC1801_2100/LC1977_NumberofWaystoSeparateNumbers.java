package LC1801_2100;

public class LC1977_NumberofWaystoSeparateNumbers {
    /**
     * You wrote down many positive integers in a string called num. However, you realized that you forgot to add
     * commas to seperate the different numbers. You remember that the list of integers was non-decreasing and that
     * no integer had leading zeros.
     *
     * Return the number of possible lists of integers that you could have written down to get the string num. Since
     * the answer may be large, return it modulo 10^9 + 7.
     *
     * Input: num = "327"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= num.length <= 3500
     * num consists of digits '0' through '9'.
     * @param num
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public int numberOfCombinations(String num) {
        // corner case
        if (num == null || num.length() == 0) return 0;

        int n = num.length();
        long M = (long)(1e9 + 7);
        int[][] presum_dp = new int[n + 5][n + 5];
        int[][] lcs = new int[n + 5][n + 5]; // 双序列dp

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (num.charAt(i) == num.charAt(j)) {
                    lcs[i][j] = lcs[i + 1][j + 1] + 1;
                } else lcs[i][j] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int len = 1; len <= i + 1; len++) {
                long dp = 0;
                int j = i - len + 1;
                if (num.charAt(j) == '0') dp = 0; // 不能直接continue，否则会跳过下面dp的累加过程
                else if (len == i + 1) dp = 1;
                else {
                    int maxLen2 = Math.min(len, j);
                    if (len == maxLen2 && lcs[j - len][j] < len && num.charAt(j - len + lcs[j - len][j]) > num.charAt(j + lcs[j - len][j])) {
                        maxLen2 -= 1;
                    }
                    while (maxLen2 >= 1 && num.charAt(j - maxLen2) == '0') maxLen2 -= 1;
                    if (maxLen2 >= 1) dp = presum_dp[j - 1][maxLen2];
                }
                presum_dp[i][len] = (int)((presum_dp[i][len - 1] + dp) % M);
            }
        }
        return (int) presum_dp[n - 1][n];
    }
}
/**
 *            len2      len
 * x x x x (x x x x) [x x x x]
 *                   j      i
 * dp[i][len] = dp[j-1][1] + dp[j-1][2] + dp[j-1][3] + (dp[j-1][4])?
 * 抓最后一位
 * for (int i = 0; i < n; i++) {
 *      for (int len = 1; len <= i + 1; len++) {
 *          // dp[i][len]
 *          int maxLen2 = len - 1;
 *          if (num[j-len2:j-1] <= num[j:i])  =>如何快速判断长度相同的字符串谁打谁小 -> 比较第一个不同的字符
 *              maxLen2 += 1;
 *          for (int len2 = 1; len2 <= maxLen2; len2++) {
 *              dp[i][len] += dp[j-1][len2]  -> presum dp
 *          }
 *      }
 * }
 * return sum{dp[n-1][len]} len = 1, 2, 3, ...n  =>
 * return presum_dp[n-1][n]
 *
 * dp[i][len] = presum_dp[j-1][maxLen2];
 * presum_dp[i][len] = dp[i][1] + dp[i][2] + dp[i][3] + ... + dp[i][len]
 * presum_dp[i][len] = presum_dp[i][len-1] + dp[i][len]
 * 其实最后return的其实就是return presum_dp[n-1][n]
 * x x [x x x] x [x x x] x x x x
 *     i         j
 * lcs[i][j] = 3
 * longest common substring
 * 然后直接跳到lcs后的第一个字符，肯定是不同的，就能立刻判断两个字符串谁大谁小
 * 所以提前算个LCS就能得到。=> O(n^2)
 * 这样就能O(1)时间得到最大最小
 */
