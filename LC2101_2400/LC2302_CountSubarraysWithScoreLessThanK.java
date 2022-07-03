package LC2101_2400;

public class LC2302_CountSubarraysWithScoreLessThanK {
    /**
     * The score of an array is defined as the product of its sum and its length.
     *
     * For example, the score of [1, 2, 3, 4, 5] is (1 + 2 + 3 + 4 + 5) * 5 = 75.
     * Given a positive integer array nums and an integer k, return the number of non-empty subarrays of nums whose
     * score is strictly less than k.
     *
     * A subarray is a contiguous sequence of elements within an array.
     *
     * Input: nums = [2,1,4,3,5], k = 10
     * Output: 6
     *
     * Input: nums = [1,1,1], k = 5
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * 1 <= k <= 10^15
     * @param nums
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(n)
    public long countSubarrays(int[] nums, long k) {
        int n = nums.length;
        long[] presum = new long[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + nums[i - 1];

        long res = 0;
        for (int i = 1; i <= n; i++) {
            if (nums[i - 1] >= k) continue;
            int left = 0, right = i - 1;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if ((presum[i] - presum[mid]) * (i - mid) >= k) left = mid + 1;
                else right = mid;
            }
            res += i - left;
        }
        return res;
    }
}
/**
 * 对于每个元素而言，有多少个subarray跟它关联
 * 只要遍历这些元素，将这些元素关联的subarray关联起来即可
 * x x x [j x x i] x x x j
 *              3
 */
