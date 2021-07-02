package LC301_600;
import java.util.*;
public class LC584_SplitArraywithEqualSum {
    /**
     * Given an integer array nums of length n, return true if there is a triplet (i, j, k) which satisfies the
     * following conditions:
     *
     * 0 < i, i + 1 < j, j + 1 < k < n - 1
     * The sum of subarrays (0, i - 1), (i + 1, j - 1), (j + 1, k - 1) and (k + 1, n - 1) is equal.
     * A subarray (l, r) represents a slice of the original array starting from the element indexed l to the element
     * indexed r.
     *
     * Input: nums = [1,2,1,2,1,2,1]
     * Output: true
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 2000
     * -10^6 <= nums[i] <= 10^6
     * @param nums
     * @return
     */
    // S1: prefix sum
    // time = O(n^2), space = O(n)
    public boolean splitArray(int[] nums) {
        // corner case
        if (nums == null || nums.length < 7) return false;

        int n = nums.length;
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + nums[i - 1];

        for (int j = 3; j <= n - 4; j++) {
            HashSet<Integer> set = new HashSet<>();
            for (int i = 1; i <= n - 6; i++) {
                int v1 = presum[i], v2 = presum[j] - presum[i + 1];
                if (v1 == v2) set.add(v1);
            }
            for (int k = j + 2; k <= n - 2; k++) {
                int v3 = presum[k] - presum[j + 1];
                int v4 = presum[n] - presum[k + 1];
                if (v3 == v4 && set.contains(v3)) return true;
            }
        }
        return false;
    }

    // S2: DFS
    // time = O(n^2), space = O(n)
    public boolean splitArray2(int[] nums) {
        // corner case
        if (nums == null || nums.length < 7) return false;

        int sum = 0, n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) continue;
            sum += nums[i];
            if (dfs(nums, i + 1, 3, sum)) return true;
        }
        return false;
    }

    private boolean dfs(int[] nums, int idx, int slice, int sum) {
        // base case - success
        if (slice == 0) return idx == nums.length;

        // base case - fail
        if (idx >= nums.length) return false;

        int curSum = 0;
        for (int i = idx + 1; i < nums.length; i++) {
            curSum += nums[i];
            if (curSum == sum) {
                if (dfs(nums, i + 1, slice - 1, sum)) return true;
            }
        }
        return false;
    }
}
/**
 * k = n - 2
 * 1,[2],1,2,[1],2,[1],2
 *
 */
