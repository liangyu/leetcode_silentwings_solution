package LC001_300;
import java.util.*;
public class LC35_SearchInsertPosition {
    /**
     * Given a sorted array of distinct integers and a target value, return the index if the target is found.
     * If not, return the index where it would be if it were inserted in order.
     *
     * Input: nums = [1,3,5,6], target = 5
     * Output: 2
     *
     * @param nums
     * @param target
     * @return
     */
    // time = O(logn), space = O(1)
    public int searchInsert(int[] nums, int target) {
        // corner case
        if (nums == null || nums.length == 0) return -1;

        int start = 0, end = nums.length - 1;

        // step 1: binary search -> shrink to 2 numbers, and then do the post-processing
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) { // find the 1st position >= target, so move end in equal condition
                end = mid;
            } else if (nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        // step 2: post-processing
        if (nums[start] >= target) return start;
        if (nums[end] >= target) return end;
        return end + 1;
    }
}
