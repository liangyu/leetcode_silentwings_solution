package LC001_300;
import java.util.*;
public class LC283_MoveZeroes {
    /**
     * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero
     * elements.
     *
     * Note that you must do this in-place without making a copy of the array.
     *
     * Input: nums = [0,1,0,3,12]
     * Output: [1,3,12,0,0]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^4
     * -2^31 <= nums[i] <= 2^31 - 1
     *
     *
     * Follow up: Could you minimize the total number of operations done?
     * @param nums
     */
    // S1: 补0
    // time = O(n), space = O(1)
    public void moveZeroes(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return;

        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[idx++] = nums[i];
            }
        }
        for (int i = idx; i < nums.length; i++) nums[i] = 0;
    }

    // S2: swap
    // time = O(n), space = O(1)
    public void moveZeroes2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return;

        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                swap(nums, slow++, fast);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
