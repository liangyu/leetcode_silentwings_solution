package LC2101_2400;
import java.util.*;
public class LC2210_CountHillsandValleysinanArray {
    /**
     * You are given a 0-indexed integer array nums. An index i is part of a hill in nums if the closest non-equal
     * neighbors of i are smaller than nums[i]. Similarly, an index i is part of a valley in nums if the closest
     * non-equal neighbors of i are larger than nums[i]. Adjacent indices i and j are part of the same hill or valley
     * if nums[i] == nums[j].
     *
     * Note that for an index to be part of a hill or valley, it must have a non-equal neighbor on both the left and
     * right of the index.
     *
     * Return the number of hills and valleys in nums.
     *
     * Input: nums = [2,4,1,1,6,5]
     * Output: 3
     *
     * Constraints:
     *
     * 3 <= nums.length <= 100
     * 1 <= nums[i] <= 100
     * @param nums
     * @return
     */
    // S1: Eliminate duplicates
    // time = O(n), space = O(n)
    public int countHillValley(int[] nums) {
        int n = nums.length;
        List<Integer> list = new ArrayList<>();
        list.add(nums[0]);
        for (int i = 1; i < n; i++) {
            if (nums[i] == nums[i - 1]) continue;
            list.add(nums[i]);
        }

        n = list.size();
        int count = 0;
        for (int i = 1; i < n - 1; i++) {
            if (list.get(i) > list.get(i - 1) && list.get(i) > list.get(i + 1)) count++;
            else if (list.get(i) < list.get(i - 1) && list.get(i) < list.get(i + 1)) count++;
        }
        return count;
    }

    // S2: pointer (optimal solution!)
    // time = O(n), space = O(1)
    public int countHillValley2(int[] nums) {
        int n = nums.length, count = 0, left = nums[0];
        for (int i = 1; i < n - 1; i++) {
            if (left < nums[i] && nums[i] > nums[i + 1] || left > nums[i] && nums[i] < nums[i + 1]) {
                left = nums[i];
                count++;
            }
        }
        return count;
    }
}
