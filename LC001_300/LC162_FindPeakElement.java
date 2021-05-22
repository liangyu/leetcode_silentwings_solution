package LC001_300;
import java.util.*;
public class LC162_FindPeakElement {
    /**
     * A peak element is an element that is strictly greater than its neighbors.
     *
     * Given an integer array nums, find a peak element, and return its index.
     * If the array contains multiple peaks, return the index to any of the peaks.
     *
     * You may imagine that nums[-1] = nums[n] = -∞.
     *
     * Input: nums = [1,2,3,1]
     * Output: 2
     *
     * Input: nums = [1,2,1,3,5,6,4]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * -231 <= nums[i] <= 231 - 1
     * nums[i] != nums[i + 1] for all valid i.
     *
     * @param nums
     * @return
     */
    // time = O(logn), space = O(1)
    public int findPeakElement(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) {
            return -1;
        }

        if (nums.length == 1) return 0;

        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < nums[mid + 1]) start = mid;
            else end = mid;
        }
        return nums[start] > nums[end] ? start : end;
        // if (nums[start] > nums[end]) return start;
        // if (nums[start] < nums[end]) return end;
        // return -1;
        // nums[i] ！= nums[i + 1] -> 不可能存在平台 -> return nums[start] > nums[end] ? start : end;
    }
}
