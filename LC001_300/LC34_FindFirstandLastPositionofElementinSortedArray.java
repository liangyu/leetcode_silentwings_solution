package LC001_300;
import java.util.*;
public class LC34_FindFirstandLastPositionofElementinSortedArray {
    /**
     * Given an array of integers nums sorted in ascending order, find the starting and ending position of
     * a given target value.
     *
     * If target is not found in the array, return [-1, -1].
     *
     * Follow up: Could you write an algorithm with O(log n) runtime complexity?
     *
     * Input: nums = [5,7,7,8,8,10], target = 8
     * Output: [3,4]
     *
     * Constraints:
     *
     * 0 <= nums.length <= 105
     * -109 <= nums[i] <= 109
     * nums is a non-decreasing array.
     * -109 <= target <= 109
     *
     * @param nums
     * @param target
     * @return
     */
    // time = O(logn), space = O(1)
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[2];
        // corner case
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1}; // be careful of this corner case!!!
        }

        // step 1: find the 1st position
        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) end = mid; // 1st position, move end
            else if (nums[mid] < target) start = mid;
            else end = mid;
        }
        if (nums[start] == target) res[0] = start; // 1st pos -> check start 1st
        else if (nums[end] == target) res[0] = end;
        else res[0] = -1;

        // step 2：find the last position
        start = 0;
        end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) start = mid; // last position, move start
            else if (nums[mid] < target) start = mid;
            else end = mid;
        }
        if (nums[end] == target) res[1] = end; // last pos -> check end 1st
        else if (nums[start] == target) res[1] = start;
        else res[1] = -1;

        return res;
    }
}
