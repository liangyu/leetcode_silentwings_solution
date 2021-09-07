package LC1201_1500;
import java.util.*;
public class LC1235_MaximumProfitinJobScheduling {
    /**
     * We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], obtaining a profit of
     * profit[i].
     *
     * You're given the startTime, endTime and profit arrays, return the maximum profit you can take such that there are
     * no two jobs in the subset with overlapping time range.
     *
     * If you choose a job that ends at time X you will be able to start another job that starts at time X.
     *
     * Input: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]
     * Output: 120
     *
     * Constraints:
     *
     * 1 <= startTime.length == endTime.length == profit.length <= 5 * 10^4
     * 1 <= startTime[i] < endTime[i] <= 10^9
     * 1 <= profit[i] <= 10^4
     * @param startTime
     * @param endTime
     * @param profit
     * @return
     */
    // S1: DP
    // time = O(nlogn), space = O(n)
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];

        for (int i = 0; i < n; i++) { // O(n)
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }

        Arrays.sort(jobs, (o1, o2) -> o1[1] - o2[1]); // O(nlogn)

        TreeMap<Integer, Integer> map = new TreeMap<>(); // dp[t] -> val
        map.put(-1, 0); // 加入一个虚拟结点，来避免下面fk可能出现null的情况，保证一定能找到一个
        int res = 0;
        for (int i = 0; i < n; i++) { // O(n)
            int cur = res; // choose not to -> directly inherit from the previous res
            Integer fk = map.floorKey(jobs[i][0]); // O(logn)
            cur = Math.max(cur, map.get(fk) + jobs[i][2]);
            map.put(jobs[i][1], cur);
            res = Math.max(res, cur);
        }
        return res;
    }

    // S1.2: DP2
    // time = O(nlogn), space = O(n)
    public int jobScheduling2(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];

        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }

        Arrays.sort(jobs, (o1, o2) -> o1[1] - o2[1]);

        List<int[]> dp = new ArrayList<>(); // {endTime, profit}
        dp.add(new int[]{-1, 0});
        int res = 0;
        for (int i = 0; i < n; i++) {
            int cur = res; // choose not to -> directly inherit from the previous res
            int idx = lowerBound(dp, jobs[i][0]); // 直接用二分法，之前jobs已经排过序了
            cur = Math.max(cur, dp.get(idx)[1] + jobs[i][2]);
            dp.add(new int[]{jobs[i][1], cur});
            res = Math.max(res, cur);
        }
        return res;
    }

    private int lowerBound(List<int[]> dp, int t) {
        int left = 0, right = dp.size() - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (dp.get(mid)[0] <= t) left = mid;
            else right = mid - 1;
        }
        return dp.get(left)[0] <= t ? left : left - 1;
    }
}
/**
 * ref: 435
 * 贪心策略：
 * sort by ending points: maximum number of non-overlapping intervals
 * LC435选第一个，因为它的ending point 比较早，有更多的机会不会干扰后面区间，它会减少取更多区间的可能 (greedy)
 * 而这里是取Profit最大的 -> 挑profit最大的，但是可能duration比较大
 * => 对重合区间没有一个贪心策略来决定取谁
 * 用O(n^2)的dp解来粗暴解决
 * dp[i] = max{dp[j] + val[i]} for j = ...
 * dp[i] = max{dp[j] + val[i], dp[i - 1]}  (截止到i这个时刻，你能得到的最大任务，这里j是指最后一个比i start point早的ending point，
 * 但这并不意味着i一定会取,不取的话直接取dp[i - 1]）
 * dp一定是单调递增的，这里i, j都是表示时刻
 * dp[t]: by the time of t, the maximum profit
 * t 是离散来看的，都是每个ending point
 * 如何找到这个t呢？=> 恰好比start point小的t => 二分 (因为是递增的)
 */
