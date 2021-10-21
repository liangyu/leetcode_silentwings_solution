package LC001_300;
import java.util.*;
public class LC123_BestTimetoBuyandSellStockIII {
    /**
     * You are given an array prices where prices[i] is the price of a given stock on the ith day.
     *
     * Find the maximum profit you can achieve. You may complete at most two transactions.
     *
     * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy
     * again).
     *
     * Input: prices = [3,3,5,0,0,3,1,4]
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= prices.length <= 10^5
     * 0 <= prices[i] <= 10^5
     * @param prices
     * @return
     */
    // S1: DP
    // time = O(n), space = O(1)
    public int maxProfit(int[] prices) {
        // corner case
        if (prices == null || prices.length == 0) return 0;

        int buy1 = Integer.MIN_VALUE, buy2 = Integer.MIN_VALUE;
        int sell1 = 0, sell2 = 0;

        for (int price : prices) {
            sell2 = Math.max(sell2, buy2 + price);
            buy2 = Math.max(buy2, sell1 - price);
            sell1 = Math.max(sell1, buy1 + price);
            buy1 = Math.max(buy1, -price);
        }
        return sell2;
    }

    // S2: dp
    // time = O(n), space = O(1)
    public int maxProfit2(int[] prices) {
        // corner case
        if (prices == null || prices.length == 0) return 0;

        int hold1 = Integer.MIN_VALUE, sold1 = 0, hold2 = Integer.MIN_VALUE, sold2 = 0;
        for (int p : prices) {
            int hold1_tmp = hold1;
            int sold1_tmp = sold1;
            int hold2_tmp = hold2;
            int sold2_tmp = sold2;

            hold1 = Math.max(0 - p, hold1_tmp);
            sold1 = Math.max(hold1_tmp + p, sold1_tmp);
            hold2 = Math.max(sold1_tmp - p, hold2_tmp);
            sold2 = Math.max(hold2_tmp + p, sold2_tmp);
        }
        return Math.max(sold1, sold2);
    }
}
/**
 * 每天就4种状态：
 * hold1[k] = max(0-price[k], hold1[k-1])  第k天买的
 * sold1[k] = max(hold1[k-1]+price[k], sold1[k-1])
 * hold2[k] = max(sold1[k-1]-price[k], hold2[k-1])
 * sold2[k] = max(hold2[k-1]+price[k], sold2[k-1])
 * => max(sold1[n-1], sold2[n-1])
 * 第k天的状态只取决于第k-1天的状态
 */