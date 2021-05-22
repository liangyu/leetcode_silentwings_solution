package LC001_300;
import java.util.*;
public class LC75_SortColors {
    /**
     * Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same
     * color are adjacent, with the colors in the order red, white, and blue.
     *
     * We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
     *
     * Input: nums = [2,0,2,1,1,0]
     * Output: [0,0,1,1,2,2]
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 300
     * nums[i] is 0, 1, or 2.
     *
     *
     * Follow up:
     *
     * Could you solve this problem without using the library's sort function?
     * Could you come up with a one-pass algorithm using only O(1) constant space?
     */
    // time = O(n), space = O(1)
    public void sortColors(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return;

        int left = 0, right = nums.length - 1, cur = 0;
        while (cur <= right) {
            if (nums[cur] == 0) swap(nums, left++, cur++);
            else if (nums[cur] == 2) swap(nums, right--, cur);
            else cur++;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
