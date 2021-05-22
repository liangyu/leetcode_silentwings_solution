package LC001_300;
import java.util.*;
public class LC41_FirstMissingPositive {
    /**
     * Given an unsorted integer array nums, find the smallest missing positive integer.
     *
     * Input: nums = [1,2,0]
     * Output: 3
     *
     * Constraints:
     *
     * 0 <= nums.length <= 300
     * -231 <= nums[i] <= 231 - 1
     *
     *
     * Follow up: Could you implement an algorithm that runs in O(n) time and uses constant extra space?
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int firstMissingPositive(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 1; // by default, the missing one is the first 1

        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                swap(nums, nums[i] - 1, i);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) return i + 1;
        }
        return nums.length + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
