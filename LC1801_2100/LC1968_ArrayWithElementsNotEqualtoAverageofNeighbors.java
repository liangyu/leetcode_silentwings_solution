package LC1801_2100;
import java.util.*;
public class LC1968_ArrayWithElementsNotEqualtoAverageofNeighbors {
    /**
     * You are given a 0-indexed array nums of distinct integers. You want to rearrange the elements in the array such
     * that every element in the rearranged array is not equal to the average of its neighbors.
     *
     * More formally, the rearranged array should have the property such that for every i in the range
     * 1 <= i < nums.length - 1, (nums[i-1] + nums[i+1]) / 2 is not equal to nums[i].
     *
     * Return any rearrangement of nums that meets the requirements.
     *
     * Input: nums = [1,2,3,4,5]
     * Output: [1,2,4,5,3]
     *
     * Constraints:
     *
     * 3 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // S1: two pass
    // time = O(nlogn), space = O(sort) = O(logn)
    public int[] rearrangeArray(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int i = 0;
        for (i = 1; i < n - 1; i++) {
            if (2 * nums[i] == nums[i - 1] + nums[i + 1]) {
                swap(nums, i, i + 1);
            }
        }

        for (i = n - 2; i > 0; i--) {
            if (2 * nums[i] == nums[i - 1] + nums[i + 1]) {
                swap(nums, i, i - 1);
            }
        }
        return nums;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // S2: one pass
    // time = O(nlogn), space = O(sort) = O(logn)
    public int[] rearrangeArray2(int[] nums) {
        // corner case
        if (nums == null || nums.length < 3) return nums;

        Arrays.sort(nums); // Sort and swap neighbours.

        int n = nums.length;
        for (int i = 1; i < n; i += 2) {
            int temp = nums[i];
            nums[i] = nums[i - 1];
            nums[i - 1] = temp;
        }
        return nums;
    }
}
