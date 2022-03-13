package LC001_300;
import java.util.*;
public class LC80_RemoveDuplicatesfromSortedArrayII {
    /**
     * Given a sorted array nums, remove the duplicates in-place such that duplicates appeared at most twice and return
     * the new length.
     *
     * Do not allocate extra space for another array; you must do this by modifying the input array in-place with O(1)
     * extra memory.
     *
     * Input: nums = [1,1,1,2,2,3]
     * Output: 5, nums = [1,1,2,2,3]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 3 * 10^4
     * -10^4 <= nums[i] <= 104
     * nums is sorted in ascending order.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int removeDuplicates(int[] nums) {
        // corner case
        if (nums == null) return 0;
        if (nums.length <= 2) return nums.length;

        int n = nums.length, j = 1;
        for (int i = 2; i < n; i++) {
            if (nums[i] == nums[j] && nums[i] == nums[j - 1]) continue;
            nums[++j] = nums[i];
        }
        return j + 1;
    }
}
