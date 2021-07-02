package LC901_1200;
import java.util.*;
public class LC983_MinimumCostForTickets {
    /**
     * You have planned some train traveling one year in advance. The days of the year in which you will travel are
     * given as an integer array days. Each day is an integer from 1 to 365.
     *
     * Train tickets are sold in three different ways:
     *
     * a 1-day pass is sold for costs[0] dollars,
     * a 7-day pass is sold for costs[1] dollars, and
     * a 30-day pass is sold for costs[2] dollars.
     * The passes allow that many days of consecutive travel.
     *
     * For example, if we get a 7-day pass on day 2, then we can travel for 7 days: 2, 3, 4, 5, 6, 7, and 8.
     * Return the minimum number of dollars you need to travel every day in the given list of days.
     *
     * Input: days = [1,4,6,7,8,20], costs = [2,7,15]
     * Output: 11
     *
     * Constraints:
     *
     * 1 <= days.length <= 365
     * 1 <= days[i] <= 365
     * days is in strictly increasing order.
     * costs.length == 3
     * 1 <= costs[i] <= 1000
     * @param days
     * @param costs
     * @return
     */
    // time = O(n), space = O(n)
    public int mincostTickets(int[] days, int[] costs) {
        int[] dp = new int[366];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        HashSet<Integer> set = new HashSet<>();
        for (int d : days) set.add(d);

        int n = days.length;
        for (int i = 1; i <= days[n - 1]; i++) {
            if (!set.contains(i)) {
                dp[i] = dp[i - 1];
                continue;
            }
            int a = dp[Math.max(0, i - 1)] + costs[0]; // 最早也得从第0天开始买三种票，所以遇到出界的都从0这个下边界开始计算!
            int b = dp[Math.max(0, i - 7)] + costs[1];
            int c = dp[Math.max(0, i - 30)] + costs[2];
            dp[i] = Math.min(a, Math.min(b, c));
        }
        return  dp[days[n - 1]];
    }
}
/**
 * dp[i]: the cost for the first i days you can travel
 * dp[8] = 9
 * dp[9] = dp[8]  不用上班，i不在这个date里，那么就直接继承上一个即可
 * dp[10] = dp[9]
 *
 * if (i not in days) dp[i] = dp[i-1]
 * 第i天要上班，
 * dp[i] = min{dp[i-1]+costs[0], dp[i-7]+costs[1], dp[i-30]+costs[2]}
 * why not dp[i-2] + costs[1] ?
 * 对任何的 j < i, dp[j] <= dp[i] 恒成立
 * => 同样买周票，7天前买一定 <= 2天前买的花费，同理对应其他日票和月票，所以以上方案一定是最优解，其他情况不会比它更优！
 * dp无后效性，这里有点贪心的思想
 */
