package LC001_300;
import java.util.*;
public class LC188_BestTimetoBuyandSellStockIV {
    /**
     * You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an
     * integer k.
     *
     * Find the maximum profit you can achieve. You may complete at most k transactions.
     *
     * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you
     * buy again).
     *
     * Input: k = 2, prices = [2,4,1]
     * Output: 2
     *
     * Constraints:
     *
     * 0 <= k <= 100
     * 0 <= prices.length <= 1000
     * 0 <= prices[i] <= 1000
     * @param k
     * @param prices
     * @return
     */
    // time = O(n * k), space = (n * k)
    public int maxProfit(int k, int[] prices) {
        // corner case
        if (prices == null || prices.length == 0 || k <= 0) return 0;

        int n = prices.length;
        if (k >= n / 2) return helper(prices);

        int[][] dp = new int[k + 1][n];
        for (int i = 1; i <= k; i++) {
            int max = -prices[0];
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + max);
                max = Math.max(max, dp[i - 1][j - 1] - prices[j]);
            }
        }
        return dp[k][n - 1];
    }

    private int helper(int[] prices) {
        int n = prices.length, res = 0;
        for (int i = 1; i < n; i++) {
            if (prices[i] > prices[i - 1]) {
                res += prices[i] - prices[i - 1];
            }
        }
        return res;
    }
}
