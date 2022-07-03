package LC2101_2400;

public class LC2270_NumberofWaystoSplitArray {
    /**
     * You are given a 0-indexed integer array nums of length n.
     *
     * nums contains a valid split at index i if the following are true:
     *
     * The sum of the first i + 1 elements is greater than or equal to the sum of the last n - i - 1 elements.
     * There is at least one element to the right of i. That is, 0 <= i < n - 1.
     * Return the number of valid splits in nums.
     *
     * Input: nums = [10,4,-8,7]
     * Output: 2
     *
     * Constraints:
     *
     * 2 <= nums.length <= 10^5
     * -10^5 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int waysToSplitArray(int[] nums) {
        int n = nums.length, count = 0;
        long sum = 0, rsum = 0;
        for (int x : nums) sum += x;
        rsum = sum;
        sum = 0;
        for (int i = 0; i < n - 1; i++) {
            sum += nums[i];
            if (sum >= rsum - sum) count++;
        }
        return count;
    }
}
