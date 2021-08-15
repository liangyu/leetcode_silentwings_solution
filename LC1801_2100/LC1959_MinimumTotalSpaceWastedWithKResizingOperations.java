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
}
/**
 * f[i][k]: the minumum space wasted if we can resize k times of nums[i:n-1]
 * when we do use a resize option, we need to pick the size = max{nums[i:j]}
 * so wasted space = max{nums[i:j]} * (j-i+1)-sum{nums[i:j]}
 */
