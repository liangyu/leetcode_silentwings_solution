package LC001_300;
import java.util.*;
public class LC280_WiggleSort {
    /**
     * Given an integer array nums, reorder it such that nums[0] <= nums[1] >= nums[2] <= nums[3]....
     *
     * You may assume the input array always has a valid answer.
     *
     * Input: nums = [3,5,2,1,6,4]
     * Output: [3,5,1,6,2,4]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5 * 10^4
     * 0 <= nums[i] <= 10^4
     * It is guaranteed that there will be an answer for the given input nums.
     *
     *
     * Follow up: Could you do it without sorting the array?
     * @param nums
     */
    // time = O(n), space = O(1)
    public void wiggleSort(int[] nums) {
        // corner case
        if (nums == null || nums.length < 2) return;

        int n = nums.length;
        for (int i = 1; i < n; i++) {
            if (i % 2 == 1 && nums[i] < nums[i - 1] || i % 2 == 0 && nums[i] > nums[i - 1]) {
                int temp = nums[i];
                nums[i] = nums[i - 1];
                nums[i - 1] = temp;
            }
        }
    }
}
