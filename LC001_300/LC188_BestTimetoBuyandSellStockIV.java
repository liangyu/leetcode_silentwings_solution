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
    // S1: dp
    // time = O(n * k), space = O(k)
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (k >= n / 2) {
            int res = 0;
            for (int i = 0; i < n - 1; i++) {
                res += Math.max(0, prices[i + 1] - prices[i]);
            }
            return res;
        }

        int[] hold = new int[k + 1];
        int[] sold = new int[k + 1];
        Arrays.fill(hold, Integer.MIN_VALUE / 2);
        Arrays.fill(sold, Integer.MIN_VALUE / 2);
        hold[0] = 0;
        sold[0] = 0;

        for (int i = 0; i < n; i++) {
            int[] hold_tmp = hold.clone();
            int[] sold_tmp = sold.clone();
            for (int j = 1; j <= k; j++) {
                hold[j] = Math.max(hold_tmp[j], sold_tmp[j - 1] - prices[i]);
                sold[j] = Math.max(sold_tmp[j], hold_tmp[j] + prices[i]);
            }
        }

        int res = Integer.MIN_VALUE;
        for (int j = 0; j <= k; j++) res = Math.max(res, sold[j]);
        return res;
    }

    // S2: 状态机dp
    // time = O(n * k), space = O(k)
    public int maxProfit2(int k, int[] prices) {
        int n = prices.length, res = 0;
        if (k >= n / 2) { // 等价于可以交易无限次
            for (int i = 0; i < n - 1; i++) {
                if (prices[i + 1] > prices[i]) {
                    res += prices[i + 1] - prices[i];
                }
            }
            return res;
        }

        int[] f = new int[k + 1]; // sold
        int[] g = new int[k + 1]; // hold
        Arrays.fill(f, Integer.MIN_VALUE / 2);
        Arrays.fill(g, Integer.MIN_VALUE / 2);
        f[0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = k; j > 0; j--) {
                f[j] = Math.max(f[j], g[j] + prices[i - 1]);
                g[j] = Math.max(g[j], f[j - 1] - prices[i - 1]);
                res = Math.max(res, f[j]);
            }
        }
        return res;
    }

    // S3: B.S.
    // time = O(nlogp), space = O(n)
    public int maxProfit3(int k, int[] prices) {
        int max = 0;
        for (int x : prices) max = Math.max(max, x);

        int left = 0, right = max;
        while (left < right) {
            int fee = left + (right - left) / 2;
            if (maxProfitWithFree(prices, fee)[1] > k) left = fee + 1;
            else right = fee;
        }
        return maxProfitWithFree(prices, left)[0] + left * k;
    }

    private int[] maxProfitWithFree(int[] prices, int fee) {  // 返回最大值和最大值的位置
        int n = prices.length;
        int[] sold = new int[n + 1], hold = new int[n + 1];
        hold[0] = Integer.MIN_VALUE / 2;
        sold[0] = 0;
        int count_sold = 0, count_hold = 0;

        for (int i = 1; i <= n; i++) {
            if (sold[i - 1] >= hold[i - 1] + prices[i - 1]) sold[i] = sold[i - 1];
            else {
                sold[i] = hold[i - 1] + prices[i - 1];
                count_sold = count_hold;
            }

            if (hold[i - 1] >= sold[i - 1] - prices[i - 1] - fee) {
                hold[i] = hold[i - 1];
            } else {
                hold[i] = sold[i - 1] - prices[i - 1] - fee;
                count_hold = count_sold + 1; // 多做了一次交易，以买为标准算一次交易！
            }
        }
        return new int[]{sold[n], count_sold};
    }
}
/**
 * S1: dp
 * x x x x x i
 * time = O(nk)
 * for (int i = 0; i < n; i++)
 *      for (int k = 0; k <= K; k++)
 *          sold[i][k] = max{sold[i-1][k], hold[i-1][k]+prices[i]}
 *          hold[i][k] = max{hold[i-1][k], sold[i-1][k-1]-prices[i]}   注意当前交易尚未闭环(买了还没卖),所以i-1的卖还是k次操作！！！
 * max{sold[n-1][k]} for k = 0, 1, 2, ...k
 * 最后一天不可能是hold,否则肯定是亏的，还不如不买呢！所以最后一天只要看sold即可，但不一定是交易k次利润最大！
 *
 * S2:
 * time = O(nlogP)
 * 固定交易x次，最大利润是多少
 * f(x): the maximum profit with the fixed transaction number x  => 上凸的函数
 * f(k)
 * g(x) = f(x) - a*x
 * if a = 0 => g(x) = f(x)
 * if a = 1 => g(x)的峰值会向下向左移动
 *
 * g(x) = f(x) - a*x  with the peak at x = k
 * g(x)_max = f(k) - a*k
 * f(k) = g(x)_max + a*k
 * a 固定向左移动 => 二分
 * 如果k>xm，那么我们二分搜索是不会搜到峰值位置是k的，但是会最终将a的选择收敛到0，这也就达到我们的目的了。
 *
 * S3: 状态机模型dp
 * f(i,j): 已走了i条边，且已经转了j圈的最大收益 f(i,j) = max{f(i-1,j), g(i-1, j) + wi}
 * g(i,j): 已走了i条边，且正在转第j圈的最大收益 g(i,j) = max{g(i-1,j), f(i-1,j-1) - wi}
 * init: f(0,0) = 0
 */