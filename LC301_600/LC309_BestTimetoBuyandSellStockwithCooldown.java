package LC301_600;

public class LC309_BestTimetoBuyandSellStockwithCooldown {
    /**
     * You are given an array prices where prices[i] is the price of a given stock on the ith day.
     *
     * Find the maximum profit you can achieve. You may complete as many transactions as you like (i.e., buy one and
     * sell one share of the stock multiple times) with the following restrictions:
     *
     * After you sell your stock, you cannot buy stock on the next day (i.e., cooldown one day).
     * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy
     * again).
     *
     * Input: prices = [1,2,3,0,2]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= prices.length <= 5000
     * 0 <= prices[i] <= 1000
     * @param prices
     * @return
     */
    // S1: dp
    // time = O(n), space = O(1)
    public int maxProfit(int[] prices) {
        // corner case
        if (prices == null || prices.length == 0) return 0;

        // init
        int hold = Integer.MIN_VALUE, sold = 0, cooled = 0;
        int n = prices.length;
        for (int i = 0; i < n; i++) {
            int hold2 = hold, sold2 = sold, cooled2 = cooled;
            hold = Math.max(hold2, cooled2 - prices[i]); // buy in
            sold = hold2 + prices[i]; // sold out, 如果hold初始设为0的话，第一天sold = prices[i]就有问题了
            cooled = Math.max(cooled2, sold2); // last day is cool too or sold out
        }
        return Math.max(sold, cooled);
    }

    // S2: state machine dp
    // time = O(n), space = O(n)
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][3]; // 0: cold, 1: hold, 2: sold
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            dp[i][2] = dp[i - 1][1] + prices[i];
        }
        return Math.max(dp[n - 1][0], Math.max(dp[n - 1][1], dp[n - 1][2]));
    }

    // S2.1: state machine dp
    // time = O(n), space = O(1)
    public int maxProfit3(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[2][3]; // 0: cold, 1: hold, 2：sell
        dp[0][1] = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            dp[i & 1][0] = Math.max(dp[i - 1 & 1][0], dp[i - 1 & 1][2]);
            dp[i & 1][1] = Math.max(dp[i - 1 & 1][1], dp[i - 1 & 1][0] - prices[i - 1]);
            dp[i & 1][2] = dp[i - 1 & 1][1] + prices[i - 1];
        }
        return Math.max(dp[n & 1][0], dp[n & 1][2]);
    }
}
/**
 * hold = max(hold2, cooled2-p)
 * sold = hold2+p
 * cooled = max(cooled2, sold2)
 * 昨天的状态和今天的状态相互交错，看起来有点复杂
 * 干脆都备份下
 * 最后在hold, sold和cooled之间取最大值
 * 但我们一般不考虑hold,因为hold的话手头有股票，肯定是亏钱，还不如当初不买
 * 所以肯定在sold和cooled里面选个最大的
 */
