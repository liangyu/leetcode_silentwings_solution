package LC001_300;
import java.util.*;
public class LC31_NextPermutation {
    /**
     * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of
     * numbers.
     *
     * If such an arrangement is not possible, it must rearrange it as the lowest possible order (i.e., sorted in
     * ascending order).
     *
     * The replacement must be in place and use only constant extra memory.
     *
     * Input: nums = [1,2,3]
     * Output: [1,3,2]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 100
     * @param nums
     */
    // time = O(n), space = O(1)
    public void nextPermutation(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return;

        int i = nums.length - 1;
        while (i >= 1 && nums[i] <= nums[i - 1]) i--;
        // case 1: reach the head -> i == 0  递增
        if (i == 0) {
            reverse(nums, 0);
            return;
        }

        // case 2: nums[i] > nums[i - 1]
        i--;
        int j = nums.length - 1;
        while (nums[j] <= nums[i] && j > i) j--;
        swap(nums, i, j);
        reverse(nums, i + 1);
        return;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i++, j--);
        }
    }
}
/**
 * [ ] [4] 5 2 1 -> [5] * * * 从小到大排一下，越小越好 -> 5 1 2 4
 * 从最低位到最高位，找第一个能提升一点的位置，剩下的从小到大排
 * [197] 3  8 6 4 2
 * [197] 4  2 3 6 8  (8 6 3 2 -> 2 3 6 8 -> reverse)
 */
