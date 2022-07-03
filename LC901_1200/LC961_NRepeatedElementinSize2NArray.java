package LC901_1200;

import java.util.HashSet;

public class LC961_NRepeatedElementinSize2NArray {
    /**
     * You are given an integer array nums with the following properties:
     *
     * nums.length == 2 * n.
     * nums contains n + 1 unique elements.
     * Exactly one element of nums is repeated n times.
     * Return the element that is repeated n times.
     *
     * Input: nums = [1,2,3,3]
     * Output: 3
     *
     * Constraints:
     *
     * 2 <= n <= 5000
     * nums.length == 2 * n
     * 0 <= nums[i] <= 10^4
     * nums contains n + 1 unique elements and one of them is repeated exactly n times.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int repeatedNTimes(int[] nums) {
        int n = nums.length;
        for (int i = 0; i + 1 < n; i += 2) {
            if (nums[i] == nums[i + 1]) return nums[i];
        }
        if (nums[0] == nums[2] || nums[0] == nums[3]) return nums[0];
        return nums[1];
    }
}
