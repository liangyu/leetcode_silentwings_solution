package LC1501_1800;

public class LC1508_RangeSumofSortedSubarraySums {
    /**
     * You are given the array nums consisting of n positive integers. You computed the sum of all non-empty continuous
     * subarrays from the array and then sorted them in non-decreasing order, creating a new array of n * (n + 1) / 2
     * numbers.
     *
     * Return the sum of the numbers from index left to index right (indexed from 1), inclusive, in the new array.
     * Since the answer can be a huge number return it modulo 10^9 + 7.
     *
     * Input: nums = [1,2,3,4], n = 4, left = 1, right = 5
     * Output: 13
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 100
     * 1 <= left <= right <= n * (n + 1) / 2
     * @param nums
     * @param n
     * @param left
     * @param right
     * @return
     */
    // time = O(n^2 * logS), space = O(1)    S: sum of nums
    public int rangeSum(int[] nums, int n, int left, int right) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int sum = 0;
        for (int num : nums) sum += num;

        long M = (long) (1e9 + 7);
        long res = 0;
        for (int k = left; k <= right; k++) { // O(r - l) -> O(n)
            res = (res + kthSubarraySum(nums, k, sum)) % M;
        }
        return (int) res;
    }

    private int kthSubarraySum(int[] nums, int k, int sum) {
        int left = 1, right = sum;
        while (left < right) {  // O(log(10^5))
            int mid = left + (right - left) / 2;
            if (countSmallerOrEqual(nums, mid) < k) left = mid + 1; // 往上猜，答案肯定不是mid
            else right = mid;
        }
        return left; // 一定有解！
    }

    private int countSmallerOrEqual(int[] nums, int s) { // O(n)
        // how many subarrays whose sum is smaller or equal to s
        int n = nums.length, count = 0, j = 0, sum = 0;
        for (int i = 0; i < n; i++) { // O(n)
            while (j < n && sum + nums[j] <= s) sum += nums[j++];
            count += j - i; // 注意j不用reset
            sum -= nums[i];
        }
        return count;
    }
}
/**
 * 一共有 n*(n+1)/2个subarray
 * 10^6 => sort下也能AC
 * 求第k大的subarray sum => bs
 * O(n^2*log(n^2)) ->
 * 怎么判断猜大猜小呢？
 * kth subarray sum = S
 * countSmallerOrEqual(S) < k => 第k个subArray sum肯定不是S => left = S + 1
 * countSmallerOrEqual(S) >= k => right = S
 * TotalSubSum(right) - TotalSubSum(left-1)
 *     x x x ... x x x
 *     l l l     r r r
 */