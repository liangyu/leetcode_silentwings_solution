package LC001_300;
import java.util.*;
public class LC121_BestTimetoBuyandSellStock {
    /**
     * You are given an array prices where prices[i] is the price of a given stock on the ith day.
     *
     * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the
     * future to sell that stock.
     *
     * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
     *
     * Input: prices = [7,1,5,3,6,4]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= prices.length <= 10^5
     * 0 <= prices[i] <= 10^4
     * @param prices
     * @return
     */
    // time = O(n), space = O(1)
    public int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE, res = 0;
        for (int x : prices) {
            res = Math.max(res, x - min);
            min = Math.min(min, x);
        }
        return res;
    }
}
