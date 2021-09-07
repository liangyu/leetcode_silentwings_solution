package LC301_600;

public class LC485_MaxConsecutiveOnes {
    /**
     * Given a binary array nums, return the maximum number of consecutive 1's in the array.
     *
     * Input: nums = [1,1,0,1,1,1]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * nums[i] is either 0 or 1.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int findMaxConsecutiveOnes(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, count = 0, res = 0;
        for (int num : nums) {
            if (num == 1) count += 1;
            else {
                res = Math.max(count, res);
                count = 0;
            }
        }
        return Math.max(res, count);
    }
}
