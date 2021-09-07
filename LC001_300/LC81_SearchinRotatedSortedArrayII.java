package LC001_300;
import java.util.*;
public class LC81_SearchinRotatedSortedArrayII {
    /**
     * You are given an integer array nums sorted in ascending order (not necessarily distinct values),
     * and an integer target.
     *
     * Suppose that nums is rotated at some pivot unknown to you beforehand (i.e., [0,1,2,4,4,4,5,6,6,7] might
     * become [4,5,6,6,7,0,1,2,4,4]).
     *
     * If target is found in the array return its index, otherwise, return -1.
     *
     * Input: nums = [2,5,6,0,0,1,2], target = 0
     * Output: true
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5000
     * -10^4 <= nums[i] <= 10^4
     * nums is guaranteed to be rotated at some pivot.
     * -10^4 <= target <= 10^4
     *
     * Follow up: This problem is the same as Search in Rotated Sorted Array, where nums may contain duplicates.
     * Would this affect the run-time complexity? How and why?
     *
     * @param nums
     * @param target
     * @return
     */
    // S1: BS
    // time = O(logn) (worst : O(n)), space = O(1)
    public boolean search(int[] nums, int target) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) return true;
            if (nums[start] == nums[mid] && nums[mid] == nums[end]) start++; // break the balance
            else if (nums[start] <= nums[mid]) {
                if (nums[start] <= target && target <= nums[mid]) end = mid;
                else start = mid;
            } else {
                if (nums[mid] <= target && target <= nums[end]) start = mid;
                else end = mid;
            }
        }
        if (nums[start] == target || nums[end] == target) return true;
        return false;
    }
    // S1: BS
    // time = O(logn) (worst : O(n)), space = O(1)
    public boolean search2(int[] nums, int target) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        int left = 0, right = nums.length - 1;
        while (right > 1 && nums[right] == nums[0]) right--; // right >= 0 即可！！
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return true;
            if (nums[mid] >= nums[0] && target >= nums[0] || nums[mid] < nums[0] && target < nums[0]) {
                if (nums[mid] < target) left = mid + 1;
                else right = mid;
            } else if (nums[mid] >= nums[0]) left = mid + 1;
            else right = mid - 1;
        }
        return nums[left] == target ? true : false;
    }
}
/**
 * 相较于I,这里的问题出在判断nums[mid]与target是否在同一个区间的时候，你会有
 * 1 1 2 2 3 4 5 6 7 0 0 1 1 1     target = 1
 *   M                   M T
 * 怎么做？只要多加一行！最大问题就是它的头等于它的尾，这里只要找是否存在即可。
 * 如果把右边所有干扰的1一删除，就发觉原来的做法又成立了！
 */
