package LC601_900;

public class LC713_SubarrayProductLessThanK {
    /**
     * Given an array of integers nums and an integer k, return the number of contiguous subarrays where the product
     * of all the elements in the subarray is strictly less than k.
     *
     * Input: nums = [10,5,2,6], k = 100
     * Output: 8
     *
     * Constraints:
     *
     * 1 <= nums.length <= 3 * 10^4
     * 1 <= nums[i] <= 1000
     * 0 <= k <= 10^6
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int j = 0, product = 1, res = 0;

        for (int i = 0; i < n; i++) {
            if (j < i) {
                j = i;
                product = 1;
            }
            while (j < n && product * nums[j] < k) {
                product *= nums[j];
                j++;
            }
            res += j - i;
            product /= nums[i];
        }
        return res;
    }
}
/**
 * 当nums[j]>k时，右指针动不了，而左指针依然会顺移，所以可能会出现j<i的情况，此时只需要重置这两个指针即可
 */
