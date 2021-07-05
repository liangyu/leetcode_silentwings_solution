package LC1801_2100;

public class LC1920_BuildArrayfromPermutation {
    /**
     * Given a zero-based permutation nums (0-indexed), build an array ans of the same length where ans[i] = nums[nums[i]]
     * for each 0 <= i < nums.length and return it.
     *
     * A zero-based permutation nums is an array of distinct integers from 0 to nums.length - 1 (inclusive).
     *
     * Input: nums = [0,2,1,5,3,4]
     * Output: [0,1,2,4,5,3]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] < nums.length
     * The elements in nums are distinct.
     *
     * Follow-up: Can you solve it without using an extra space (i.e., O(1) memory)?
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int[] buildArray(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return new int[0];

        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) ans[i] = nums[nums[i]];
        return ans;
    }
}
