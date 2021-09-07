package LC1801_2100;
import java.util.*;
public class LC1959_MinimumTotalSpaceWastedWithKResizingOperations {
    /**
     * You are currently designing a dynamic array. You are given a 0-indexed integer array nums, where nums[i] is the
     * number of elements that will be in the array at time i. In addition, you are given an integer k, the maximum
     * number of times you can resize the array (to any size).
     *
     * The size of the array at time t, sizet, must be at least nums[t] because there needs to be enough space in the
     * array to hold all the elements. The space wasted at time t is defined as sizet - nums[t], and the total space
     * wasted is the sum of the space wasted across every time t where 0 <= t < nums.length.
     *
     * Return the minimum total space wasted if you can resize the array at most k times.
     *
     * Note: The array can have any size at the start and does not count towards the number of resizing operations.
     *
     * Input: nums = [10,20], k = 0
     * Output: 10
     *
     * Constraints:
     *
     * 1 <= nums.length <= 200
     * 1 <= nums[i] <= 10^6
     * 0 <= k <= nums.length - 1
     * @param nums
     * @param k
     * @return
     */
    // time = O(n * k), space = O(n * k)
    public int minSpaceWastedKResizing(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k < 0) return 0;

        int n = nums.length;
        long[][] f = new long[n][k + 1];
        for (int i = 0; i < n; i++) Arrays.fill(f[i], -1);
        return (int)dfs(nums, k, 0, f);
    }

    private long dfs(int[] nums, int k, int start, long[][] f) {
        // base case
        if (start == nums.length) return 0;
        if (k == -1) return Integer.MAX_VALUE;
        if (f[start][k] != -1) return f[start][k];

        int n = nums.length;
        long res = Long.MAX_VALUE;
        int max = nums[start], sum = 0;

        for (int i = start; i < n; i++) {
            max = Math.max(nums[i], max);
            sum += nums[i];
            res = Math.min(res, max * (i - start + 1) - sum + dfs(nums, k - 1, i + 1, f));
        }
        f[start][k] = res;
        return f[start][k];
    }

    // S2 DP
    // time = O(n^2 * k), space = O(n * k)
    public int minSpaceWastedKResizing2(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k < 0) return 0;

        int n = nums.length;
        int[][] dp = new int[n][k + 1];
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], Integer.MAX_VALUE / 2);

        int max = 0, sum = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, nums[i]);
            sum += nums[i];
            dp[i][0] = max * (i + 1) - sum;
        }

        for (int i = 1; i < n; i++) { // i 在s后面，s >= 1 -> i >= 1
            // if refine the search range, then initialization should do out of the loop!!!
            for (int j = 1; j <= Math.min(i, k); j++) { // j <= k 也可以！
                //update dp[i][j]
                max = 0;
                int intervalSum = 0;
                for (int s = i; s >= 1; s--) {
                    max = Math.max(max, nums[s]);
                    intervalSum += nums[s];
                    dp[i][j] = Math.min(dp[i][j], dp[s - 1][j - 1] + max * (i - s + 1) - intervalSum);
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int j = 0; j <= k; j++) {
            res = Math.min(res, dp[n - 1][j]);
        }
        return res;
    }
}
/**
 * ref: LC1043 and LC1105
 * f[i][k]: the minumum space wasted if we can resize k times of nums[i:n-1]
 * when we do use a resize option, we need to pick the size = max{nums[i:j]}
 * so wasted space = max{nums[i:j]} * (j-i+1)-sum{nums[i:j]}
 *        size
 * x x x x [x x i]
 *          s
 * 这个size什么时候确定的
 * 这道题的突破口在于你最后一次调整这个size是在什么地方
 * 遍历s的位置
 * dp[i][j]: for the first i elements, we used j operations, the minimum total space wasted
 * 如果给你行使权利，这个下标大概率描述你使用了多少次这种权利
 * 第二类基本型，找最后一个区间
 * for s = 0, 1, 2, ... i
 *      dp[i][j] = min{dp[s-1][j-1]+wasteSpace[s:i]}
 * 要知道size调整为多少，然后用size * 个数 - 实际的数目 = 这段区间浪费了多少
 * size必须要调整到这里面的最大值，一定要 >= 里面的每个数
 * dp[i][j] = min{dp[s-1][j-1]+max[s:i]*(i-s+1)-intervalSum[s:i]}
 * rolling max -> O(1)
 * for i:
 *      for j:
 *          for s = i, i-1,... 0
 *              mx =
 *              intervalSum =
 *              dp[i][j] = min{dp[s-1][j-1]+max[s:i]*(i-s+1)-intervalSum[s:i]}
 *
 * time = O(n*k*n)
 */
