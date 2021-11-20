package LC301_600;
import java.util.*;
public class LC448_FindAllNumbersDisappearedinanArray {
    /**
     * Given an array nums of n integers where nums[i] is in the range [1, n], return an array of all the integers in
     * the range [1, n] that do not appear in nums.
     *
     * Input: nums = [4,3,2,7,8,2,3,1]
     * Output: [5,6]
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^5
     * 1 <= nums[i] <= n
     *
     * Follow up: Could you do it without extra space and in O(n) runtime? You may assume the returned list does not
     * count as extra space.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (nums == null || nums.length == 0) return res;

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] != i + 1 && nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) res.add(i + 1);
        }
        return res;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
/**
 * ref: LC41, 442
 * indexing sort
 * [4,3,2,7,8,2,3,1]
 * [1,2,3,4,3,2,7,8] -> O(n)
 */