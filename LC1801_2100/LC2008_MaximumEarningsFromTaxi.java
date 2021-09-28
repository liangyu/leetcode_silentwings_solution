package LC1801_2100;
import java.util.*;
public class LC2008_MaximumEarningsFromTaxi {
    /**
     * There are n points on a road you are driving your taxi on. The n points on the road are labeled from 1 to n in
     * the direction you are going, and you want to drive from point 1 to point n to make money by picking up passengers.
     * You cannot change the direction of the taxi.
     *
     * The passengers are represented by a 0-indexed 2D integer array rides, where rides[i] = [starti, endi, tipi]
     * denotes the ith passenger requesting a ride from point starti to point endi who is willing to give a tipi dollar
     * tip.
     *
     * For each passenger i you pick up, you earn endi - starti + tipi dollars. You may only drive at most one passenger
     * at a time.
     *
     * Given n and rides, return the maximum number of dollars you can earn by picking up the passengers optimally.
     *
     * Note: You may drop off a passenger and pick up a different passenger at the same point.
     *
     * Input: n = 5, rides = [[2,5,4],[1,5,1]]
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * 1 <= rides.length <= 3 * 10^4
     * rides[i].length == 3
     * 1 <= starti < endi <= n
     * 1 <= tipi <= 10^5
     * @param n
     * @param rides
     * @return
     */
    // S1: TreeMap
    // time = O(mlogm), space = O(m)
    public long maxTaxiEarnings(int n, int[][] rides) {
        // corner case
        if (rides == null || rides.length == 0) return 0;

        Arrays.sort(rides, (o1, o2) -> o1[1] - o2[1]);

        TreeMap<Integer, Long> map = new TreeMap<>();
        long res = 0;
        for (int i = 0; i < rides.length; i++) {
            Integer fk = map.floorKey(rides[i][0]);
            res = Math.max(res, (fk == null ? 0 : map.get(fk)) + rides[i][1] - rides[i][0] + rides[i][2]);
            map.put(rides[i][1], res);
        }
        return res;
    }

    // S2: DP
    // time = O(mlogm), space = O(m)
    public long maxTaxiEarnings2(int n, int[][] rides) {
        int m = rides.length;
        Arrays.sort(rides, (o1, o2) -> o1[1] - o2[1]);

        int[] endPoints = new int[m];
        for (int i = 0; i < m; i++) {
            endPoints[i] = rides[i][1];
        }

        long[] dp = new long[m + 1];
        for (int i = 1; i <= m; i++) {
            dp[i] = dp[i - 1];
            int s = rides[i - 1][0];
            int j = upperBound(endPoints, s);
            dp[i] = Math.max(dp[i], dp[j + 1] + rides[i - 1][1] - rides[i - 1][0] + rides[i - 1][2]);
        }
        return dp[m];
    }

    private int upperBound(int[] nums, int t) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (nums[mid] <= t) left = mid;
            else right = mid - 1;
        }
        return nums[left] <= t ? left : left - 1;
    }
}
/**
 * the maximum non-overlapping intervals
 * the minimum intervals that cover the whole range
 * 权重不是1，而是totally random，没办法贪心，只能用动态规划 => O(n^2)
 * 贪心：没有权重
 * dp[i] = max{dp[i-1], dp[j]+g}
 *
 * 比较感兴趣的是下客的点，其他点不会产生收益的变化，只会在订单结束位置才会有变化。
 * 看下上个结束点dp值是多少，dp[i] = dp[j] 躺平；也可以选择做这单生意 dp[k]
 * 不需要给n开dp，只需要给rides来开
 */
