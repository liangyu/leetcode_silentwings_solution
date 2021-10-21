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
    // S1: BS
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

    // S2: BS
    // time = O(nlogn), space = O(n)
    public int kthSmallestSubarraySum2(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return 0;

        int n = nums.length;
        int[] presum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            presum[i + 1] = presum[i] + nums[i];
        }

        int left = 0, right = Integer.MAX_VALUE;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int count = countSmallerOrEqual(presum, mid); // # of diff value <= mid
            if (count < k) left = mid + 1; // mid 太小
            else right = mid;
        }
        return left;
    }

    private int countSmallerOrEqual(int[] presum, int mid) { // presum都是递增的 O(n)
        int res = 0, j = 0, n = presum.length;
        for (int i = 0; i < n; i++) {
            while (j < n && presum[j] - presum[i] <= mid) j++; // j是单调排的
            res += j - (i + 1);
        }
        return res;
    }

//    private int countSmallerOrEqual(int[] presum, int mid) { // presum都是递增的 O(nlogn) -> 太高
//        int n = presum.length, res = 0;
//        for (int i = 0; i < n; i++) {
//            int idx = upperBound(presum, mid + presum[i]);
//            res += idx - i;
//        }
//        return res;
//    }

//    private int upperBound(int[] nums, int t) {
//        int left = 0, right = nums.length - 1;
//        while (left < right) {
//            int mid = right - (right - left) / 2;
//            if (nums[mid] <= t) left = mid;
//            else right = mid - 1;
//        }
//        return nums[left] <= t ? left : left - 1;
//    }
}
/**
 * ref: LC719
 * subarray sum -> presum 前缀和数组的差
 * sum[i:j] = presum[j] - presum[i - 1]
 * find kth smallest pair diff
 * 凡是看见第k小，尝试用二分搜值
 * D =>
 * while (left < right) {
 *      if (countSmallerOrEqual(D)) < k) {  // 连D本身加进去都不足k个 => D本身肯定不是第k个 -> 猜小了，猜一个更大的
 *          left = D + 1; // guess > D;
 *      } else {  // countSmallerOrEqual(D) >= k
 *          right = D; // guess <= D
 *      }
 * }
 * left == right
 * x x x x k x x x x x x
 *         ^
 * 假设很多可行解，其实找的是最小的一个可行解
 * {i, j}
 * presum[j] - presum[i] <= D
 * presum[j] <= D + presum[i]  => j是有上界的  upperBound
 */
