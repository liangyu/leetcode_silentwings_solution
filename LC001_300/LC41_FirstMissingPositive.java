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
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] != i + 1 && nums[i] > 0 && nums[i] <= n && nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) return i + 1;
        }
        return n + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
/**
 * indexing sort
 * [2,4,3,5,1] -> 把2扔到它该在的地方
 *  4 2 3 5 1
 *  5 2 3 4 1
 *  1 2 3 4 5
 * [1 2 3 4 k ....]
 * 至少能让k之前的这些数都老老实实呆在位子上
 * 一旦发现哪位对不上了，就尽力了
 * [2 4 3 5 2]
 *  4 2 3 5 2
 *  5 2 3 4 2
 *  2 2 3 4 2 -> 死循环  力所能及的地方
 *
 *  2 2 1 4 5
 *  1 2 2 4 5
 *  顺着对每一位做swap，一直到swap不动为止
 */
