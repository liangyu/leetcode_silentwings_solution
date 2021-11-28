package LC1801_2100;
import java.util.*;
public class LC2089_FindTargetIndicesAfterSortingArray {
    /**
     * You are given a 0-indexed integer array nums and a target element target.
     *
     * A target index is an index i such that nums[i] == target.
     *
     * Return a list of the target indices of nums after sorting nums in non-decreasing order. If there are no target
     * indices, return an empty list. The returned list must be sorted in increasing order.
     *
     * Input: nums = [1,2,5,2,3], target = 2
     * Output: [1,2]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 1 <= nums[i], target <= 100
     * @param nums
     * @param target
     * @return
     */
    // time = O(n), space = O(1)
    public List<Integer> targetIndices(int[] nums, int target) {
        List<Integer> res = new ArrayList<>();
        int n = nums.length, smaller = 0, count = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] < target) smaller++;
            if (nums[i] == target) count++;
        }
        int val = smaller;
        while (count-- > 0) res.add(val++);
        return res;
    }
}
