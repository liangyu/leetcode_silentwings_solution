package LC2101_2400;

public class LC2291_MaximumProfitFromTradingStocks {
    /**
     * You are given two 0-indexed integer arrays of the same length present and future where present[i] is the current
     * price of the ith stock and future[i] is the price of the ith stock a year in the future. You may buy each stock
     * at most once. You are also given an integer budget representing the amount of money you currently have.
     *
     * Return the maximum amount of profit you can make.
     *
     * Input: present = [5,4,6,2,3], future = [8,5,4,3,5], budget = 10
     * Output: 6
     *
     * Constraints:
     *
     * n == present.length == future.length
     * 1 <= n <= 1000
     * 0 <= present[i], future[i] <= 100
     * 0 <= budget <= 1000
     * @param present
     * @param future
     * @param budget
     * @return
     */
    // S1: 2D dp
    // time = O(n * budget), space = O(n * budget)
    public int maximumProfit(int[] present, int[] future, int budget) {
        int n = present.length;
        int[][] dp = new int[n + 1][budget + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= budget; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= present[i - 1]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - present[i - 1]] + future[i - 1] - present[i - 1]);
                }
            }
        }
        return dp[n][budget];
    }

    // S2: 1D dp
    // time = O(n * budget), space = O(budget)
    public int maximumProfit2(int[] present, int[] future, int budget) {
        int n = present.length, m = budget;
        int[] dp = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = m; j >= present[i - 1]; j--) {
                dp[j] = Math.max(dp[j], dp[j - present[i - 1]] + future[i - 1] - present[i - 1]);
            }
        }
        return dp[m];
    }
}
