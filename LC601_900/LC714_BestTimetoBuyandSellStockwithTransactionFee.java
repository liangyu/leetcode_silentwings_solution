package LC601_900;
import java.util.*;
public class LC714_BestTimetoBuyandSellStockwithTransactionFee {
    /**
     * You are given an array prices where prices[i] is the price of a given stock on the ith day, and an integer fee
     * representing a transaction fee.
     *
     * Find the maximum profit you can achieve. You may complete as many transactions as you like, but you need to pay
     * the transaction fee for each transaction.
     *
     * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy
     * again).
     *
     * Input: prices = [1,3,2,8,4,9], fee = 2
     * Output: 8
     *
     * Constraints:
     *
     * 1 < prices.length <= 5 * 10^4
     * 0 < prices[i], fee < 5 * 10^4
     *
     * @param prices
     * @param fee
     * @return
     */
    // S1: dp
    // time = O(n), space = O(1)
    public int maxProfit(int[] prices, int fee) {
        // corner case
        if (prices == null || prices.length == 0) return 0;

        int hold = Integer.MIN_VALUE / 2, sold = 0; // 第一天的时候不能hold一个股票，但代价为0，而应该是负数
        for (int i = 0; i < prices.length; i++) {
            int p = prices[i]; // p: 当天的价格
            int hold2 = hold, sold2 = sold; // 用上一个状态来更新现在的状态
            hold = Math.max(hold2, sold2 - p);
            sold = Math.max(sold2, hold2 + p - fee);
        }
        return sold;
    }

    // S2: state machine DP
    // time = O(n), space = O(n)
    public int maxProfit2(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n + 1][2];
        for (int i = 0; i <= n; i++) Arrays.fill(dp[i], Integer.MIN_VALUE / 2);
        dp[0][0] = 0;

        int res = 0;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i - 1]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i - 1] - fee);
            res = Math.max(res, dp[i][0]);
        }
        return res;
    }
}
/**
 * hold:
 * sold
 *
 * f(i,0): 走了i条边，处于状态0的最大价值
 * f(i,1): 走了i条边，处于状态1的最大价值
 */