package LC001_300;
import java.util.*;
public class LC153_FindMinimuminRotatedSortedArray {
    /**
     * Suppose an array of length n sorted in ascending order is rotated between 1 and n times.
     * For example, the array nums = [0,1,2,4,5,6,7] might become:
     *
     * [4,5,6,7,0,1,2] if it was rotated 4 times.
     * [0,1,2,4,5,6,7] if it was rotated 7 times.
     * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array
     * [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
     *
     * Given the sorted rotated array nums, return the minimum element of this array.
     *
     * Input: nums = [3,4,5,1,2]
     * Output: 1
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 5000
     * -5000 <= nums[i] <= 5000
     * All the integers of nums are unique.
     * nums is sorted and rotated between 1 and n times.
     *
     * @param nums
     * @return
     */
    // S1: BS
    // time = o(logn), space = O(1)
    public int findMin(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Invalid input!");
        }

        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[start] < nums[mid]) { // case 1: 如果mid落在左边上升区间
                if (nums[mid] < nums[end]) return nums[start]; // 1.1: 单调递增，则start就是最小
                else start = mid; // 1.2: 2个单调区间，则最小值应该落在第2区间的左端点 -> move start = mid
            } else { // case 2: 如果落在右边上升区间
                end = mid; // 只有一种情况，就是min落在右边上升区间的左端点 -> move end = mid
            }
        }
        if (nums[start] < nums[end]) return nums[start];
        return nums[end];
    }

    // S1.2: BS
    // time = o(logn), space = O(1)
    public int findMin2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0)  return -1;

        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) left = mid + 1;
            else right = mid;
        }
        return nums[left];
    }
}
/**
 * 通过mid与right的比较就可以判断在左还是右区间
 */
