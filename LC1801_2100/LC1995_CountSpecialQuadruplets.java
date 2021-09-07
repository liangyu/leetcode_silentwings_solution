package LC1801_2100;

public class LC1995_CountSpecialQuadruplets {
    /**
     * Given a 0-indexed integer array nums, return the number of distinct quadruplets (a, b, c, d) such that:
     *
     * nums[a] + nums[b] + nums[c] == nums[d], and
     * a < b < c < d
     *
     * Input: nums = [1,2,3,6]
     * Output: 1
     *
     * Constraints:
     *
     * 4 <= nums.length <= 50
     * 1 <= nums[i] <= 100
     * @param nums
     * @return
     */
    // time = O(n^4), space = O(1)
    public int countQuadruplets(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, count = 0;
        for (int a = 0; a < n - 3; a++) {
            for (int b = a + 1; b < n - 2; b++) {
                for (int c = b + 1; c < n - 1; c++) {
                    for (int d = c + 1; d < n; d++) {
                        if (nums[a] + nums[b] + nums[c] == nums[d]) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }
}
