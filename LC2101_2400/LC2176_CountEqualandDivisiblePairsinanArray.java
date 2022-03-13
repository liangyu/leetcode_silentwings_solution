package LC2101_2400;

public class LC2176_CountEqualandDivisiblePairsinanArray {
    /**
     * Given a 0-indexed integer array nums of length n and an integer k, return the number of pairs (i, j) where
     * 0 <= i < j < n, such that nums[i] == nums[j] and (i * j) is divisible by k.
     *
     * Input: nums = [3,1,2,2,2,1,3], k = 2
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 1 <= nums[i], k <= 100
     * @param nums
     * @param k
     * @return
     */
    // time = O(n^2), space = O(1)
    public int countPairs(int[] nums, int k) {
        int n = nums.length, count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] == nums[j] && i * j % k == 0) count++;
            }
        }
        return count;
    }
}
