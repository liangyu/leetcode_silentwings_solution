package LC1201_1500;

public class LC1420_BuildArrayWhereYouCanFindTheMaximumExactlyKComparisons {
    /**
     * You are given three integers n, m and k. Consider the following algorithm to find the maximum element of an array
     * of positive integers:
     *
     *
     * You should build the array arr which has the following properties:
     *
     * arr has exactly n integers.
     * 1 <= arr[i] <= m where (0 <= i < n).
     * After applying the mentioned algorithm to arr, the value search_cost is equal to k.
     * Return the number of ways to build the array arr under the mentioned conditions. As the answer may grow large,
     * the answer must be computed modulo 10^9 + 7.
     *
     * Input: n = 2, m = 3, k = 1
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= n <= 50
     * 1 <= m <= 100
     * 0 <= k <= n
     * @param n
     * @param m
     * @param k
     * @return
     */
    // time = O(n * m^2 * k), space = O(n * m * k)
    public int numOfArrays(int n, int m, int k) {
        long[][][] dp = new long[n][m + 1][k + 1];
        long M = (long)(1e9 + 7);

        // init
        for (int j = 1; j <= m; j++) dp[0][j][1] = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= m; j++) {
                for (int s = 1; s <= k; s++) {
                    for (int t = 1; t <= j - 1; t++) {
                        dp[i][j][s] = (dp[i][j][s] + dp[i - 1][t][s - 1]) % M;
                    }
                    dp[i][j][s] = (dp[i][j][s] +  dp[i - 1][j][s] * j) % M;
                }
            }
        }

        long res = 0;
        for (int j = 1; j <= m; j++) {
            res = (res + dp[n - 1][j][k]) % M;
        }
        return (int) res;
    }
}
/**
 * 如何设计状态变量
 * dp[i][j][k]: the number of ways to build the array [1:i] and max(arr[1:i]) = j and search cost is k
 * if arr[i] is the largest among arr[1:i] => arr[i] = j
 * dp[i][j][k] += dp[i-1][1..j-1][k-1]
 *
 * if arr[i] is not the largest among arr[1:i] => arr[i] <= j
 * dp[i][j][k] += dp[i-1][j][k] * j
 */
