package LC301_600;
import java.util.*;
public class LC581_ShortestUnsortedContinuousSubarray {
    /**
     * Given an integer array nums, you need to find one continuous subarray that if you only sort this subarray in ascending order, then the whole array will be sorted in ascending order.
     *
     * Return the shortest such subarray and output its length.
     *
     * Input: nums = [2,6,4,8,10,9,15]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^4
     * -10^5 <= nums[i] <= 10^5
     *
     *
     * Follow up: Can you solve it in O(n) time complexity?
     * @param nums
     * @return
     */
    // time = O(n), space - O(1)
    public int findUnsortedSubarray(int[] nums) {
        // corner case
        if (nums == null || nums.length <= 1) return 0;

        int len = nums.length;
        int start = 0, end = -1;
        int max = nums[0], min = nums[len - 1];

        for (int i = 0; i < len; i++) {
            if (nums[i] < max) end = i;
            else max = nums[i];
            if (nums[len - 1 - i] > min) start = len - 1 - i;
            else min = nums[len - 1 - i];
        }
        return end - start + 1;
    }
}
