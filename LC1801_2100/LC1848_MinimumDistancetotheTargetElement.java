package LC1801_2100;
import java.util.*;
public class LC1848_MinimumDistancetotheTargetElement {
    /**
     * Given an integer array nums (0-indexed) and two integers target and start, find an index i such
     * that nums[i] == target and abs(i - start) is minimized. Note that abs(x) is the absolute value of x.
     *
     * Return abs(i - start).
     *
     * It is guaranteed that target exists in nums.
     *
     * Input: nums = [1,2,3,4,5], target = 5, start = 3
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 10^4
     * 0 <= start < nums.length
     * target is in nums.
     * @param nums
     * @param target
     * @param start
     * @return
     */
    // time = O(n), space = O(1)
    public int getMinDistance(int[] nums, int target, int start) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                min = Math.min(min, Math.abs(i - start));
            }
        }
        return min;
    }
}
