package LC1801_2100;

public class LC1929_ConcatenationofArray {
    /**
     * Given an integer array nums of length n, you want to create an array ans of length 2n where ans[i] == nums[i]
     * and ans[i + n] == nums[i] for 0 <= i < n (0-indexed).
     *
     * Specifically, ans is the concatenation of two nums arrays.
     *
     * Return the array ans.
     *
     * Input: nums = [1,2,1]
     * Output: [1,2,1,1,2,1]
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 1000
     * 1 <= nums[i] <= 1000
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int[] getConcatenation(int[] nums) {
        int n = nums.length;
        int[] ans = new int[2 * n];
        for (int i = 0; i < n; i++) {
            ans[i] = nums[i];
            ans[i + n] = nums[i];
        }
        return ans;
    }
}
