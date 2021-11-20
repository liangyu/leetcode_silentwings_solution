package LC1201_1500;

public class LC1413_MinimumValuetoGetPositiveStepbyStepSum {
    /**
     * Given an array of integers nums, you start with an initial positive value startValue.
     *
     * In each iteration, you calculate the step by step sum of startValue plus elements in nums (from left to right).
     *
     * Return the minimum positive value of startValue such that the step by step sum is never less than 1.
     *
     * Input: nums = [-3,2,-3,4,2]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * -100 <= nums[i] <= 100
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int minStartValue(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int sum = 0, start = 0;
        for (int x : nums) {
            sum += x;
            if (sum < 1) {
                start += 1 - sum;
                sum = 1;
            }
        }
        return start == 0 ? 1 : start;
    }
}
