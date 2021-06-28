package LC1801_2100;

public class LC1909_RemoveOneElementtoMaketheArrayStrictlyIncreasing {
    /**
     * Given a 0-indexed integer array nums, return true if it can be made strictly increasing after removing exactly
     * one element, or false otherwise. If the array is already strictly increasing, return true.
     *
     * The array nums is strictly increasing if nums[i - 1] < nums[i] for each index (1 <= i < nums.length).
     *
     * Input: nums = [1,2,10,5,7]
     * Output: true
     *
     * Constraints:
     *
     * 2 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     * @param nums
     * @return
     */
    // S1:
    // time = O(n), space = O(1)
    public boolean canBeIncreasing(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        boolean flag = false;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1]) {
                if (flag) return false;
                flag = true;
                if (i > 1 && nums[i] <= nums[i - 2]) nums[i] = nums[i - 1];
            }
        }
        return true;
    }

    // S2: brute-force
    // time = O(n^2), space = O(1)
    public boolean canBeIncreasing2(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (helper(nums, i)) return true;
        }
        return false;
    }

    private boolean helper(int[] nums, int idx) {
        Integer pre = null;
        for (int i = 0; i < nums.length; i++) {
            if (i != idx) {
                if (pre == null) {
                    pre = nums[i];
                    continue;
                } else {
                    if (nums[i] <= pre) return false;
                    else pre = nums[i];
                }
            }
        }
        return true;
    }
}
