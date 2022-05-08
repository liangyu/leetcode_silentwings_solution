package LC2101_2400;
import java.util.*;
public class LC2229_CheckifanArrayIsConsecutive {
    /**
     * Given an integer array nums, return true if nums is consecutive, otherwise return false.
     *
     * An array is consecutive if it contains every number in the range [x, x + n - 1] (inclusive), where x is the
     * minimum number in the array and n is the length of the array.
     *
     * Input: nums = [1,3,4,2]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public boolean isConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        int n = nums.length, min = nums[0], max = nums[0];
        for (int x : nums) {
            set.add(x);
            min = Math.min(min, x);
            max = Math.max(max, x);
        }
        if (set.size() != n) return false;
        if (max - min + 1 != n) return false;
        return true;
    }
}
