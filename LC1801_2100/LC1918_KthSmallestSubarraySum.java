package LC1801_2100;
import java.util.*;
public class LC1918_KthSmallestSubarraySum {
    /**
     * Given an integer array nums of length n and an integer k, return the kth smallest subarray sum.
     *
     * A subarray is defined as a non-empty contiguous sequence of elements in an array. A subarray sum is the sum of
     * all elements in the subarray.
     *
     * Input: nums = [2,1,3], k = 4
     * Output: 3
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 2 * 10^4
     * 1 <= nums[i] <= 5 * 10^4
     * 1 <= k <= n * (n + 1) / 2
     * @param nums
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int kthSmallestSubarraySum(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return 0;

        int left = 1, right = 20000 * 50000;
        while (left < right) { // O(log(n^2) = O(logn)
            int mid = left + (right - left) / 2;
            if (helper(nums, mid) < k) left = mid + 1; // 得到的和在前k小里面，代表当前mid代表的sum太小了，要往增加和的方向移动！
            else right = mid; // mid代表的sum太大了 -> decrease the mid
        }
        return left;
    }

    // sliding window 求 subarray sum
    private int helper(int[] nums, int target) {
        int n = nums.length, start = 0, count = 0, sum = 0;
        for (int end = 0; end < n; end++) {
            sum += nums[end];
            while (sum > target) {
                sum -= nums[start];
                start++;
            }
            count += end - start + 1;
        }
        return count;
    }
}
