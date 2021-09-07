package LC001_300;
import java.util.*;
public class LC154_FindMinimuminRotatedSortedArrayII {
    /**
     * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
     *
     * (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
     *
     * Find the minimum element.
     *
     * The array may contain duplicates.
     *
     * Input: [2,2,2,0,1]
     * Output: 0
     *
     * Note:
     *
     * This is a follow up problem to Find Minimum in Rotated Sorted Array.
     * Would allow duplicates affect the run-time complexity? How and why?
     *
     * @param nums
     * @return
     */
    // S1: BS
    // time = O(logn), space = O(1)
    public int findMin(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Invalid input!");
        }

        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // case 3: what if nums[start] == nums[mid] == nums[end]
            // you can't tell which range mid is falling into
            // move either side will break the balance
            if (nums[start] == nums[mid] && nums[mid] == nums[end]) start++; // or end--;
            // case 1: mid falls in the left range
            else if (nums[start] <= nums[mid]) { // 注意这里与上面试互斥条件，一定要用else if而不是if !!!
                if (nums[mid] <= nums[end]) return nums[start]; // 这里变成<=，而不仅仅是 <
                else start = mid;
            } else { // case 2: mid falls in the right range
                end = mid;
            }
        }
        if (nums[start] < nums[end]) return nums[start];
        return nums[end];
    }

    // S1.2: BS
    // time = O(logn), space = O(1)
    public int findMin2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0)  return -1;

        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) left = mid + 1;
            else if (nums[mid] < nums[right]) right = mid;
            else right--;
        }
        return nums[left];
    }
}
/**
 * == 的时候没有办法判断是在左区间还是右区间
 * 根据LC153，加上相等的时候right--即可，把重复的删掉即可，不影响
 * [2，2，2，2，2，0，1，2]
 * [2,2,3,4,2,2,2,2,2]
 */
