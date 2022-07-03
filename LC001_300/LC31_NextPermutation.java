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
        int n = nums.length, i = n - 1;
        while (i > 0 && nums[i] <= nums[i - 1]) i--; // pivot: i - 1
        if (i == 0) {
            reverse(nums, 0, n - 1);
            return;
        }
        int j = n - 1;
        while (j >= i && nums[j] <= nums[i - 1]) j--;
        swap(nums, j, i - 1);
        reverse(nums, i, n - 1);
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    private void reverse(int[] nums, int i, int j) {
        while (i < j) swap(nums, i++, j--);
    }
}
/**
 * [ ] [4] 5 2 1 -> [5] * * * 从小到大排一下，越小越好 -> 5 1 2 4
 * 从最低位到最高位，找第一个能提升一点的位置，剩下的从小到大排
 * [197] 3  8 6 4 2
 * [197] 4  2 3 6 8  (8 6 3 2 -> 2 3 6 8 -> reverse)
 * 找到第一个非降序的位置，后面变成升序
 * 2 3 5 4 1
 *   ^   ^
 * 2 4 1 3 5
 */
