package LC1801_2100;

public class LC2057_SmallestIndexWithEqualValue {
    /**
     * Given a 0-indexed integer array nums, return the smallest index i of nums such that i mod 10 == nums[i], or -1
     * if such index does not exist.
     *
     * x mod y denotes the remainder when x is divided by y.
     *
     * Input: nums = [0,1,2]
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 9
     * @param nums
     * @return
     */
    public int smallestEqual(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return -1;

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (i % 10 == nums[i]) return i;
        }
        return -1;
    }
}
