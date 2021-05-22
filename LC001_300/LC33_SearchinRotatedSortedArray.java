package LC001_300;
import java.util.*;
public class LC33_SearchinRotatedSortedArray {
    /**
     * You are given an integer array nums sorted in ascending order (with distinct values), and an integer target.
     *
     * Suppose that nums is rotated at some pivot unknown to you beforehand
     * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
     *
     * If target is found in the array return its index, otherwise, return -1.
     *
     * Input: nums = [4,5,6,7,0,1,2], target = 0
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5000
     * -10^4 <= nums[i] <= 10^4
     * All values of nums are unique.
     * nums is guaranteed to be rotated at some pivot.
     * -10^4 <= target <= 10^4
     *
     * @param nums
     * @param target
     * @return
     */
    // time = O(logn), space = O(1)
    public int search(int[] nums, int target) {
        // corner case
        if (nums == null || nums.length == 0) return -1;

        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) return mid;
            if (nums[start] <= nums[mid]) { // 先定出mid的位置，再由此判断target的位置
                if (nums[start] <= target && target <= nums[mid]) end = mid;
                else start = mid;
            } else {
                if (nums[mid] <= target && target <= nums[end]) start = mid;
                else end = mid;
            }
        }
        if (nums[start] == target) return start;
        if (nums[end] == target) return end;
        return -1;
    }
}

/**
 *     4 5 6 7 0 1 2      target = 0
 *     L     M L'  R
 *     nums[mid] v.s. target
 */
