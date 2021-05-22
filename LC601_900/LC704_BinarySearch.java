package LC601_900;
import java.util.*;
public class LC704_BinarySearch {
    /**
     * Given a sorted (in ascending order) integer array nums of n elements and a target value,
     * write a function to search target in nums. If target exists, then return its index, otherwise return -1.
     *
     * Note:
     *
     * You may assume that all elements in nums are unique.
     * n will be in the range [1, 10000].
     * The value of each element in nums will be in the range [-9999, 9999].
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
            if (nums[mid] < target) start = mid;
            else end = mid;
        }
        if (nums[start] == target) return start;
        if (nums[end] == target) return end;
        return -1;
    }
}
